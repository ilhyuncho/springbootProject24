package com.example.cih.admin.shop;


import com.example.cih.domain.shop.ShopItem;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.car.CarInfoDTO;
import com.example.cih.dto.car.CarViewDTO;
import com.example.cih.dto.order.OrderDTO;
import com.example.cih.dto.shop.ShopItemDTO;
import com.example.cih.dto.shop.ShopItemViewDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.shop.ShopItemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminShopController {

    @Value("${com.cih.upload.path}")
    private String uploadPath;

    private final ShopItemService shopItemService;

    @GetMapping("/shopItem")
    public String getShopItem(Model model){

        List<ShopItemViewDTO> allItems = shopItemService.getAllItems();

        allItems.forEach(log::error);

        model.addAttribute("itemList", allItems);

        return "/admin/shopItem";
    }

    @ApiOperation(value = "상품 데이터 넣기", notes = "테스트 용")
    @PostMapping("/shopItem")
    public String postShopItem(ShopItemDTO shopItemDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(log::error);

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/admin/shopItem";
        }

        Long ItemId = shopItemService.registerItem(shopItemDTO);

        return "redirect:/admin/shopItem";
    }

    @GetMapping("/shopItem/{shopItemId}")
    public String shopItemDetail(PageRequestDTO pageRequestDTO,
                                 @PathVariable("shopItemId") Long shopItemId,
                                 Model model){

        ShopItemViewDTO shopItem = shopItemService.findOne(shopItemId);

        model.addAttribute("responseDTO", shopItem);

        return "/admin/shopItemDetail";
    }

    @GetMapping("/shopItemModify/{shopItemId}")
    public String shopItemModify(PageRequestDTO pageRequestDTO,
                                 @PathVariable("shopItemId") Long shopItemId,
                                 Model model){

        log.error("gdfgdfgdfghfghertyrtyrt");
        ShopItemViewDTO shopItem = shopItemService.findOne(shopItemId);

        model.addAttribute("responseDTO", shopItem);

        return "/admin/shopItemModify";
    }

    @PostMapping("/shopItemModify")
    public String shopItemModify(PageRequestDTO pageRequestDTO,
                                @Valid ShopItemDTO shopItemDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Principal principal ){
        log.error("shopItem modify post...." + shopItemDTO);

        String link = pageRequestDTO.getLink();

        if(bindingResult.hasErrors()){
            log.error("has errors.....");

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("shopItemId", shopItemDTO.getShopItemId());
            return "redirect:/admin/shopItem/" + shopItemDTO.getShopItemId();
        }

        shopItemService.modifyItem(shopItemDTO);

        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("shopItemId", shopItemDTO.getShopItemId());
        redirectAttributes.addAttribute("userName", principal.getName());

        return "redirect:/admin/shopItem/" + shopItemDTO.getShopItemId();
    }

    @PostMapping("/shopItemDelete")
    public String shopItemDelete(ShopItemDTO shopItemDTO,
                                RedirectAttributes redirectAttributes){
        log.error("remove......post: " + shopItemDTO);

        shopItemService.deleteItem(shopItemDTO.getShopItemId());

        // Item이 db에서 삭제되었다면 첨부파일 삭제
        List<String> fileNames = shopItemDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0){
            removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");
        redirectAttributes.addAttribute("userName","user1");

        return "redirect:/admin/shopItem";
    }

    public void removeFiles(List<String> files){

        log.error("removeFiles");

        files.forEach(log::error);

        for (String fileName : files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try{
                String contentType = Files.probeContentType(resource.getFile().toPath());

                resource.getFile().delete();

                // 섬네일이 존재한다면
                if(contentType.startsWith("image")){
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);

                    thumbnailFile.delete();
                }
            }catch(Exception e){
                log.error(e.getMessage());
            }
        }
    }

}
