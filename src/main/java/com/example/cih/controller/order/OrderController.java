package com.example.cih.controller.order;

import com.example.cih.domain.user.User;
import com.example.cih.dto.order.OrderItemResDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.dto.order.OrderListResDTO;
import com.example.cih.dto.order.OrderTemporaryResDTO;
import com.example.cih.dto.user.UserAddressBookResDTO;
import com.example.cih.service.shop.OrderService;
import com.example.cih.service.user.UserAddressBookService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final UserAddressBookService userAddressBookService;

    @ApiOperation(value = "주문내역 조회", notes = "결제 완료된 내역 조회")
    @GetMapping("/list")
    public String list(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                       Model model,
                       Principal principal ){

        User user = userService.findUser(principal.getName());

        PageResponseDTO<OrderListResDTO> orderAll = orderService.getOrderAll(user, pageRequestDTO);

        model.addAttribute("responseDTO", orderAll);

        log.error(orderAll);

        return "/order/orderList";
    }

    @ApiOperation(value = "주문서 페이지 이동", notes = "아이템 즉시 구매시")
    @GetMapping("/orderPage/{orderTemporaryId}")
    public String orderPage(@PathVariable(name="orderTemporaryId") Long orderTemporaryId,
                       Model model, RedirectAttributes redirectAttributes,
                       Principal principal){

        User user = userService.findUser(principal.getName());

        OrderTemporaryResDTO orderTemporaryResDTO = orderService.getOrderTemporary(orderTemporaryId);
        if(orderTemporaryResDTO == null){
            // 유효 기간 만료
            return "redirect:/shop/main/";
        }

        UserAddressBookResDTO mainAddressInfo = userAddressBookService.getMainAddressInfo(user);

        model.addAttribute("responseDTO", orderTemporaryResDTO);
        model.addAttribute("mainAddressInfo", mainAddressInfo);
        model.addAttribute("mPoint", user.getMPoint());

        return "/order/orderPage";
    }

    @ApiOperation(value = "주문 내역 상세 조회", notes = "주문 내역을 자세히")
    @GetMapping("/orderDetail/{orderId}")
    public String orderDetail(@PathVariable(name="orderId") Long orderId,
                              Model model, Principal principal){

        User user = userService.findUser(principal.getName());

        List<OrderItemResDTO> listOrderItemResDTO = orderService.getOrderDetail(user, orderId);

        model.addAttribute("responseDTO", listOrderItemResDTO);
        return "/order/orderDetail";
    }

}
