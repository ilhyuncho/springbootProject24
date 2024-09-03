package com.example.cih.controller.admin.validator;

import com.example.cih.common.util.Util;
import com.example.cih.dto.notification.NotificationRegDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NotiControllerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NotificationRegDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        NotificationRegDTO notificationRegDTO = (NotificationRegDTO)target;

        // 테스트용
        if(notificationRegDTO.getName().isEmpty()){
            notificationRegDTO.setName(Util.createRandomName(notificationRegDTO.getTarget()));
        }
    }
}
