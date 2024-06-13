package com.example.cih.controller.order;

import com.example.cih.domain.user.User;
import com.example.cih.dto.buyingCar.BuyingCarViewDTO;
import com.example.cih.dto.order.OrderDTO;
import com.example.cih.dto.order.OrderDetailDTO;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import com.example.cih.service.buyingCar.BuyingCarService;
import com.example.cih.service.shop.OrderService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class OrderController {
    private final OrderService orderService;
    private final BuyingCarService buyingCarService;
    private final UserService userService;

    @ApiOperation(value = "order 데이터 넣기", notes = "테스트 용")
    @PostMapping("/add")
    public String add(OrderDTO orderDTO, String userName){

        Long order = orderService.order(userName, orderDTO.getShopItemId(), orderDTO.getOrderCount());
        return "/shop/main";
    }

    @ApiOperation(value = "주문내역 조회", notes = "결제 완료된 내역 조회")
    @GetMapping("/list")
    public String list(@ModelAttribute("pageRequestDto") PageRequestDTO pageRequestDTO,
                       Model model,
                       Principal principal ){

        User user = userService.findUser(principal.getName());

        PageResponseDTO<OrderDTO> cartAll = orderService.getOrderAll(pageRequestDTO, principal.getName());

        List<BuyingCarViewDTO> listBuyingCarViewDTO = buyingCarService.getBuyingCarInfo(user);

        model.addAttribute("responseDTO", cartAll);
        model.addAttribute("listBuyingCarDTO", listBuyingCarViewDTO);

        return "/order/orderList";
    }

    @ApiOperation(value = "주문내역 상세 조회", notes = "주문 내역을 자세히")
    @GetMapping("/orderDetail")
    public String orderDetail(Long orderId,
                              Model model){

        OrderDetailDTO orderDetailDto = orderService.getOrderDetail(orderId);

        model.addAttribute("responseDTO", orderDetailDto);
        return "/order/orderDetail";
    }

    @ApiOperation(value = "주문 취소", notes = "")
    @PostMapping("/cancel")
    public String cancel(Long orderId,
                              Model model){

        log.error("orderCancel()~~~ ");

        orderService.cancelOrder(orderId);

        // 임시로
        return "redirect:/order/orderDetail";
    }

}
