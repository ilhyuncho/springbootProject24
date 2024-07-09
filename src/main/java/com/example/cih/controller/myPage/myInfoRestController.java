package com.example.cih.controller.myPage;

import com.example.cih.domain.user.User;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.user.*;
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


    @ApiOperation(value = "배송 주소 정보 get", notes = "")
    @GetMapping("/addressInfo")
    //@PreAuthorize("principal.username != #userName")
    public UserAddressBookResDTO addressInfo(Long userAddressBookId){

        //User user = userService.findUser(userName);

        UserAddressBookResDTO userAddressBookResDTO = userAddressBookService.getUserAddressBookInfo(userAddressBookId);

        log.error(userAddressBookResDTO);

        return userAddressBookResDTO;
    }

    @ApiOperation(value = "배송지 추가 등록", notes = "")
    @PostMapping("/registerAddress")
    public Map<String,String> postRegisterAddress(@Valid @RequestBody UserAddressBookReqDTO userAddressBookReqDTO,
                                                     BindingResult bindingResult,
                                                     Principal principal ) throws BindException {
        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }
        User user = userService.findUser(principal.getName());

        userAddressBookService.registerAddressBook(user, userAddressBookReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @ApiOperation(value = "배송지 수정", notes = "")
    @PostMapping("/modifyAddress")
    public Map<String,String> postModifyAddress(@Valid @RequestBody UserAddressBookReqDTO userAddressBookReqDTO,
                                                  BindingResult bindingResult,
                                                  Principal principal ) throws BindException {
        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }
        User user = userService.findUser(principal.getName());

        userAddressBookService.modifyAddressBook(user, userAddressBookReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }



}
