package com.example.cih.domain.car;

import org.springframework.beans.factory.annotation.Value;

public class Projection {
    public interface CarSummary{
        String getCarNumber();
        @Value("#{target.carNumber} #{target.carModel}")    // 초기 환경 객체를 호출하는 방법
        String getCarInfo();
    }

    public static class CarSummary2{
        private String carNumber;
        private String carModel;

        public CarSummary2(String carNumber,String carModel){
            this.carNumber = carNumber;
            this.carModel = carModel;
        }

        public String getCarNumber(){
            return carNumber;
        }
        public String getCarModel(){
            return carModel;
        }
    }


}
