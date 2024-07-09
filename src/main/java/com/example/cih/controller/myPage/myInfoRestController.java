package com.example.cih.controller.myPage;

import com.example.cih.common.exception.OwnerCarNotFoundException;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserAddressBook;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.car.CarConsumableRegDTO;
import com.example.cih.dto.user.UserAddressBookReqDTO;
import com.example.cih.dto.user.UserMissionListResDTO;
import com.example.cih.dto.user.UserMissionReqDTO;
import com.example.cih.dto.user.UserMissionResDTO;
import com.example.cih.service.user.UserAddressBookService;
import com.example.cih.service.user.UserMissionService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myInfo")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class myInfoRestController {

    private final UserMissionService userMissionService;
    private final UserAddressBookService userAddressBookService;
    private final UserService userService;

    @ApiOperation(value = "나의 포인트 정보 조회", notes = "")
    @GetMapping("/myPoint")
    public UserMissionListResDTO<UserMissionResDTO> getMyPoint(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                                                               UserMissionReqDTO userMissionReqDTO,
                                                               Principal principal){

        log.error("나의 포인트 정보 조회 : " + userMissionReqDTO);

        User user = userService.findUser(principal.getName());

        UserMissionListResDTO<UserMissionResDTO> listUserMission = userMissionService.getListUserMission(pageRequestDTO, user, userMissionReqDTO);

        return listUserMission;
    }

    @ApiOperation(value = "배송지 추가 등록", notes = "")
    @PostMapping("/deliveryAddress")
    public Map<String,String> postDeliveryAddress(@Valid @RequestBody UserAddressBookReqDTO userAddressBookReqDTO,
                                                     BindingResult bindingResult,
                                                     Principal principal ) throws BindException {
        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }
        User user = userService.findUser(principal.getName());

        Map<String, String> resultMap = new HashMap<>();

        List<UserAddressBook> userAddressBookInfo = userAddressBookService.getUserAddressBookInfo(user);
        if(userAddressBookInfo.size() > 7){
            resultMap.put("result", "fail");
            resultMap.put("message", "배송 주소록을 더 이상 만들수 없습니다");
            return resultMap;
        }
        else{

            boolean isSameDeliveryName = userAddressBookInfo.stream()
                    .anyMatch(userAddressBook -> userAddressBook.getDeliveryName().equals(userAddressBookReqDTO.getDeliveryName()));
            if(isSameDeliveryName)
            {
                resultMap.put("result", "fail");
                resultMap.put("message", "이미 같은 배송지명이 존재 합니다");
                return resultMap;
            }
        }

        userAddressBookService.registerAddressBook(user, userAddressBookReqDTO);

        resultMap.put("result", "success");
        return resultMap;
    }



}
