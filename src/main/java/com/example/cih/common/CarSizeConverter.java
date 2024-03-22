package com.example.cih.common;

import com.example.cih.domain.car.CarSize;

import javax.persistence.AttributeConverter;
import java.util.Objects;


public class CarSizeConverter implements AttributeConverter<CarSize, String> {
    @Override
    public String convertToDatabaseColumn(CarSize attribute) {
        if(Objects.isNull(attribute))
            return null;

        return attribute.getValue();
    }

    @Override
    public CarSize convertToEntityAttribute(String dbData) {
        if(Objects.isNull(dbData)){
            return null;
        }
        return CarSize.fromValue(dbData);
    }
}
