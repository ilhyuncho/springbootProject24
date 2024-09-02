package com.example.cih.controller.admin;


import com.example.cih.dto.ImageOrderReqDTO;
import com.example.cih.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminNotiRestController {

    private final NotificationService notificationService;

    @PostMapping("/modifyEventImageOrder")
    public ResponseEntity<Map<String, String>> postEventImageOrderModify(@Valid @RequestBody ImageOrderReqDTO imageOrderReqDTO,
                                                                    BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()) {
            log.error("bindingResult.hasErrors()~~~");
            throw new BindException(bindingResult);
        }

        notificationService.modifyImageOrder(imageOrderReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }


}
