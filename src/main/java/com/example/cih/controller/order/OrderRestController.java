package com.example.cih.controller.order;

import com.example.cih.dto.order.OrderReqDTO;
import com.example.cih.service.buyingCar.BuyingCarService;
import com.example.cih.service.shop.OrderService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class OrderRestController {
    private final OrderService orderService;
    private final BuyingCarService buyingCarService;
    private final UserService userService;

    @ApiOperation(value = "order 데이터 넣기", notes = "테스트 용")
    @PostMapping("/add")
    public String add(@RequestBody OrderReqDTO orderReqDTO,
                      Principal principal) throws ParseException {

        log.error(orderReqDTO.getDeliveryFee());
        orderReqDTO.getListOrderDetail().forEach(log::error);

        //Long order = orderService.order(principal.getName(), orderDTO.getShopItemId(), orderDTO.getItemCount());
        return "/shop/main";
    }

}
