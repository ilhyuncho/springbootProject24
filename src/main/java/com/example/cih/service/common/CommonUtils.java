package com.example.cih.service.common;

import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserGradeType;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommonUtils {

    // 할인 가격 계산
    public static int calcDiscountPrice(User user, ShopItem shopItem, EventNotification event){

        int discountPrice = 0;
        Integer originalPrice = shopItem.getItemPrice().getOriginalPrice();

        // 1. 회원 등급 별 할인율 적용
        if(user != null && (user.getMGrade() == UserGradeType.GRADE_E
                || user.getMGrade() == UserGradeType.GRADE_S)){

            if(shopItem.getItemPrice().getMembershipPercent() > 0){
                discountPrice += (int)(originalPrice * ((double)shopItem.getItemPrice().getMembershipPercent() / 100));
                log.error("등급 할인 : " + discountPrice);
            }
        }
        // 2. 이벤트 할인율 적용
        if(shopItem.getItemPrice().getIsEventTarget()
                && event != null
                && event.getEventValue() > 0){
//            log.error(originalPrice + "," + (double)eventDiscountRate / 100);

            discountPrice += (int)(originalPrice * ((double)event.getEventValue() / 100));
            log.error("이벤트 할인 : " + discountPrice);
        }

        return discountPrice;
    }
}
