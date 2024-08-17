package com.example.cih.controller.review;


import com.example.cih.domain.user.User;
import com.example.cih.dto.review.ReviewWriteReqDTO;
import com.example.cih.service.review.ReviewService;
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
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewRestController {
    private final ReviewService reviewService;
    private final UserService userService;


    @ApiOperation(value = "리뷰 등록", notes = "구매자가 요청")
    @PostMapping("/write")
    public Map<String,String> postWriteReview(@Valid @RequestBody ReviewWriteReqDTO reviewWriteReqDTO,
                                                     BindingResult bindingResult,
                                                     Principal principal ) throws BindException {

        if(bindingResult.hasErrors()){
            log.error("has errors.....");
            throw new BindException(bindingResult);
        }

        User user = userService.findUser(principal.getName());

        reviewService.writeReview(user, reviewWriteReqDTO);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }


}
