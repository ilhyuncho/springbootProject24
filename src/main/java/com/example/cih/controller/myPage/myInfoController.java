package com.example.cih.controller.myPage;


import com.example.cih.domain.user.User;
import com.example.cih.dto.user.UserAddressBookResDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.user.UserAddressBookService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/myPage")
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasRole('USER')")
public class myInfoController {

    private final UserService userService;
    private final UserAddressBookService userAddressBookService;

    @ApiOperation(value = "[나의 정보] 조회", notes = "")
    @GetMapping("/myInfo")
    //@PreAuthorize("principal.username != #userName")
    public String getMyInfo(String userName,
                             Model model){

        UserDTO userDTO = userService.findUserDTO(userName);

        model.addAttribute("userDTO", userDTO);

        return "/myPage/myInfo";
    }

    @ApiOperation(value = "[나의 포인트정보] 페이지 이동", notes = "")
    @GetMapping("/myPoint")
    //@PreAuthorize("principal.username != #userName")
    public String getMyPoint(String userName, Model model){

        User user = userService.findUser(userName);

        return "/myPage/myPoint";
    }

    @ApiOperation(value = "[배송 주소록 관리] 페이지 이동", notes = "")
    @GetMapping("/deliveryAddress")
    //@PreAuthorize("principal.username != #userName")
    public String getMyPointInfo(String userName, Model model){

        User user = userService.findUser(userName);

        List<UserAddressBookResDTO> listUserAddressBook = userAddressBookService.getAllUserAddressBookInfo(user);

        model.addAttribute("responseDTO", listUserAddressBook);

        return "/myPage/deliveryAddress";
    }


}
