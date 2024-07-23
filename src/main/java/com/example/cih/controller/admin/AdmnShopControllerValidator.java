package com.example.cih.controller.admin;

import com.example.cih.dto.ImageDTO;
import com.example.cih.dto.ImageOrderReqDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashSet;

@Component
@Log4j2
public class AdmnShopControllerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ImageOrderReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ImageOrderReqDTO imageOrderReqDTO =(ImageOrderReqDTO)target;

        // ImageOrder 값이 중복 되는지 체크
        boolean result = imageOrderReqDTO.getImageOrderList().stream().
                map(ImageDTO::getImageOrder).allMatch(new HashSet<>()::add);

        if(!result) {
            log.error("validate : " + result);
            errors.rejectValue("objectId","Duplicated Order", "순서값이 중복 되었습니다.");
            //errors.reject("message", "순서값이 중복 되었습니다.");
        }
    }

}
