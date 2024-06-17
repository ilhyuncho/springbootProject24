package com.example.cih.controller.order;

import com.example.cih.dto.order.OrderCancelDTO;
import com.example.cih.dto.order.OrderReqDTO;
import com.example.cih.service.buyingCar.BuyingCarService;
import com.example.cih.service.shop.OrderService;
import com.example.cih.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Log4j2
//@PreAuthorize("hasRole('USER')")
public class OrderRestController {
    private final OrderService orderService;
    private final BuyingCarService buyingCarService;
    private final UserService userService;

    @ApiOperation(value = "상품 결제 처리", notes = "테스트 용")
    @PostMapping("/add")
    public String add(@RequestBody OrderReqDTO orderReqDTO,
                      Principal principal) throws ParseException {

        log.error(orderReqDTO.toString());
        orderReqDTO.getListOrderDetail().forEach(log::error);

        Long order = orderService.order(principal.getName(), orderReqDTO);
        return "/shop/main";
    }

    @ApiOperation(value = "상품 구매 취소", notes = "")
    @PostMapping("/cancel")
    public Map<String, String> PostCancel(@RequestBody OrderCancelDTO orderCancelDTO,
                         Model model){

        log.error("PostCancel()~~~ orderId : " + orderCancelDTO.getOrderId() + "]" );

       orderService.cancelOrder(orderCancelDTO.getOrderId());

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");

        return resultMap;
    }
    
}
