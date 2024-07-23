package com.example.cih.controller.sellingCar;

import com.example.cih.domain.user.User;
import com.example.cih.dto.sellingCar.SellingCarRegDTO;
import com.example.cih.dto.sellingCar.SellingCarResDTO;
import com.example.cih.service.sellingCar.SellingCarService;
import com.example.cih.service.user.UserSearchCarHistoryService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/sellingCar")
@RequiredArgsConstructor
@Log4j2
public class SellingCarRestController {

    private final SellingCarService sellingCarService;
    private final UserService userService;
    private final UserSearchCarHistoryService userSearchCarHistoryService;

    @ApiOperation(value = "판매 차량 등록", notes = "차 소유주가 차량 등록")
    @PostMapping("/register")
    public Map<String,String> postRegisterSellingCar(@Valid @RequestBody SellingCarRegDTO sellingCarRegDTO,
                                BindingResult bindingResult,
                                Principal principal ) throws BindException {

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        sellingCarService.registerSellingCar(principal.getName(), sellingCarRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @ApiOperation(value = "판매 현황", notes = "차 소유주가 [판매 현황] 버튼 클릭")
    @GetMapping("/get")
    public SellingCarResDTO getSellingCar(Long sellingCarId, Principal principal){

        User user = userService.findUser(principal.getName());

        SellingCarResDTO sellingCarResDTO = sellingCarService.getSellingCarInfo(sellingCarId, user);

        return sellingCarResDTO;
    }

    @ApiOperation(value = "판매 취소", notes = "판매 중이던 차량 판매 취소")
    @PostMapping("/cancel")
    public Map<String,String> postCancelSellingCar(@Valid @RequestBody SellingCarRegDTO sellingCarRegDTO,
                                                 BindingResult bindingResult,
                                                 Principal principal ) throws BindException {
        log.error("cancelSellingCar post...." + sellingCarRegDTO.getCarId());

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        sellingCarService.cancelSellingCar(principal.getName(), sellingCarRegDTO.getCarId());

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }

    @ApiOperation(value = "추천 차량 정보 전달", notes = "메인 화면")
    @GetMapping("/recommend")
    public List<SellingCarResDTO> getRecommendSellingCar(){

        List<SellingCarResDTO> recommendList = sellingCarService.getRecommendList();

        log.error(recommendList);
        return recommendList;
    }

    @ApiOperation(value = "최근 본 차량 정보 전달", notes = "메인 화면")
    @GetMapping("/recentlySeenCar")
    public List<SellingCarResDTO> getRecentlySeenCar(Principal principal){

        User user = userService.findUser(principal.getName());

        return userSearchCarHistoryService.getSearchCarHistory(user);
    }

    @ApiOperation(value = "판매 차량 좋아요", notes = "")
    @PostMapping("/like")
    public Map<String,String> postlike(@Valid @RequestBody SellingCarRegDTO sellingCarRegDTO,
                                                   BindingResult bindingResult,
                                                   Principal principal) throws BindException {
        log.error("postlike post...." + sellingCarRegDTO);

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        User user = userService.findUser(principal.getName());

        sellingCarService.likeSellingCar(user, sellingCarRegDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }
}
