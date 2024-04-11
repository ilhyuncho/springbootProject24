package com.example.cih.domain.car;

import org.springframework.beans.factory.annotation.Value;

public class Projection {
    public interface CarSummary{
        String getCarNumber();
        @Value("#{target.carNumber} #{target.carModel}")
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
