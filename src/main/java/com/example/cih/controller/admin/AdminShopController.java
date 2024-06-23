package com.example.cih.controller.admin;


import com.example.cih.common.handler.FileHandler;
import com.example.cih.common.util.Util;
import com.example.cih.domain.shop.ItemOptionType;
import com.example.cih.dto.shop.ShopItemReqDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import com.example.cih.service.shop.ShopItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminShopController {

    private final ShopItemService shopItemService;
    private final FileHandler fileHandler;

    @GetMapping("/shopItem")
    public String getShopItem(Model model){

        List<ShopItemViewDTO> allItems = shopItemService.getAllItems();

        model.addAttribute("itemList", allItems);
        model.addAttribute("ItemOptionTypeList", ItemOptionType.getAllTypes());

        return "/admin/shopItem";
    }

    @ApiOperation(value = "상품 데이터 넣기", notes = "관리자용")
    @PostMapping("/shopItem")
    public String postShopItem(@Valid ShopItemReqDTO shopItemReqDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/shopItem";
        }

        // 테스트용
        if( shopItemReqDTO.getItemName().isEmpty()){
            shopItemReqDTO.setItemName(Util.createRandomName("Item"));
        }

        Long ItemId = shopItemService.registerItem(shopItemReqDTO);

        return "redirect:/admin/shopItem";
    }

    @GetMapping({"/shopItemDetail/{shopItemId}",
                 "/shopItemModify/{shopItemId}"})
    public String shopItemDetailOrModify(HttpServletRequest request,
                                 @PathVariable("shopItemId") Long shopItemId,
                                 Model model){

        ShopItemViewDTO shopItem = shopItemService.findItem(shopItemId);

        model.addAttribute("responseDTO", shopItem);

        String requestURI = request.getRequestURI();
        return request.getRequestURI().substring(0, requestURI.lastIndexOf("/"));
    }

    @PostMapping("/shopItemModify")
    public String shopItemModify(@Valid ShopItemReqDTO shopItemReqDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal ){

        if(bindingResult.hasErrors()){
            log.error("has errors.....");

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("shopItemId", shopItemReqDTO.getShopItemId());
            return "redirect:/admin/shopItemDetail/" + shopItemReqDTO.getShopItemId();
        }

        shopItemService.modifyItem(shopItemReqDTO);

        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("shopItemId", shopItemReqDTO.getShopItemId());
        redirectAttributes.addAttribute("userName", principal.getName());

        return "redirect:/admin/shopItemDetail/" + shopItemReqDTO.getShopItemId();
    }

    @PostMapping("/shopItemDelete")
    public String shopItemDelete(ShopItemReqDTO shopItemReqDTO,
                                 RedirectAttributes redirectAttributes){

        shopItemService.deleteItem(shopItemReqDTO.getShopItemId());

        // Item이 db에서 삭제되었다면 첨부파일 삭제
        List<String> fileNames = shopItemReqDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0){
            fileHandler.removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/admin/shopItem";
    }

}
