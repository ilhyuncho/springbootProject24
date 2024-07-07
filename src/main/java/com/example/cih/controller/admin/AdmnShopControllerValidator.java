package com.example.cih.controller.admin;

import com.example.cih.common.util.Util;
import com.example.cih.dto.shop.ShopItemReqDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AdmnShopControllerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ShopItemReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ShopItemReqDTO shopItemReqDTO =(ShopItemReqDTO)target;

        // 테스트용
        if(shopItemReqDTO.getItemName().isEmpty()){
            shopItemReqDTO.setItemName(Util.createRandomName("Item"));
        }
    }
}
