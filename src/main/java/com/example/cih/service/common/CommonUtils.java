package com.example.cih.service.common;

import com.example.cih.domain.carConsumable.CarConsumable;
import com.example.cih.domain.carConsumable.ReplaceAlarm;
import com.example.cih.domain.notification.EventNotification;
import com.example.cih.domain.reference.RefCarConsumable;
import com.example.cih.domain.shop.ShopItem;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserGradeType;
import com.example.cih.dto.shop.ItemOptionResDTO;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Log4j2
public class CommonUtils {

    private CommonUtils(){
        // 인스턴스화를 막으려고 private 생성자를 사용
        throw new AssertionError();
    };

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

    // 상품 옵션 설명 String 반환
    public static String getItemOptionDesc(List<ItemOptionResDTO> listItemOption) {
        ItemOptionResDTO itemOption1 = listItemOption.get(0);
        String desc = itemOption1.getOptionType() + ": " + itemOption1.getOptionName();

        if(listItemOption.size() > 1){
            ItemOptionResDTO itemOption2 = listItemOption.get(1);
            desc += ", " + itemOption2.getOptionType() + ": " + itemOption2.getOptionName();
        }
        return desc;
    }

    public static String getItemOptionIds(List<ItemOptionResDTO> listItemOption) {
        ItemOptionResDTO itemOption1 = listItemOption.get(0);
        String ids = itemOption1.getItemOptionId() + ",";

        if(listItemOption.size() > 1){
            ItemOptionResDTO itemOption2 = listItemOption.get(1);
            ids += itemOption2.getItemOptionId();
        }
        else{
            ids += "0";
        }
        return ids;
    }

    public static ReplaceAlarm checkNextReplaceDay(RefCarConsumable refCarConsumable, CarConsumable carConsumable){

        int cycleKm = refCarConsumable.getReplaceCycleKm();
        int cycleMonth = refCarConsumable.getReplaceCycleMonth();

        // 1. 개월 마다 체크
        if(cycleMonth > 0){
            LocalDate lastReplaceDate = carConsumable.getReplaceDate();
            LocalDate nextReplaceDay = lastReplaceDate.plusMonths(cycleMonth);  // 계산된 다음 점검 날짜

            log.error("lastReplaceDate : " + lastReplaceDate + ",  nextReplaceDay : " + nextReplaceDay);

            // 두 날짜 차이 계산
            long diffDays = Duration.between(LocalDate.now().atStartOfDay(), nextReplaceDay.atStartOfDay()).toDays();
            log.error(diffDays);

            if(diffDays <= 0 || (diffDays - 30) < 0){
                log.error("nextReplaceDay.READY_CYCLE(): " + nextReplaceDay + ", Diff: " + diffDays);
                return ReplaceAlarm.READY_CYCLE;
            }
            log.error("nextReplaceDay.NOT_CYCLE(): " + nextReplaceDay + ", Diff: " + diffDays);
        }
        else if( cycleKm > 0){   // 2. 주행 km 마다 체크
            int accumKm = carConsumable.getAccumKm();
            int nextReplaceKm = cycleKm + accumKm;

            // 주행 km를 비교하여 체크
        }

        return ReplaceAlarm.NOT_CYCLE;
    }

    public static Boolean checkTimeOver(LocalTime checkTime){
        LocalTime now = LocalDateTime.now().toLocalTime();

        Duration diff = Duration.between(now, checkTime);
        long diffSeconds = diff.toSeconds();

        return diffSeconds < 0;
    }

    public static String phoneFormat(String str) {
        if (str == null) {
            return "";
        }
        if (str.length() == 8) {
            return str.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
        } else if (str.length() == 12) {
            return str.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
        }
        return str.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    }
}
