package com.example.cih.domain.car;

import org.springframework.beans.factory.annotation.Value;

public class Projection {
    public interface CarSummary{
        String getCarNumber();

        @Value("#{target.carNumber} #{target.carModel}")
        String getCarInfo();
    }
}
