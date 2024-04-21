package com.example.cih.common;

import com.example.cih.domain.car.CarSize;

import javax.persistence.AttributeConverter;
import java.util.Objects;


// 모든 CarSize 타입에 컨버터를 적용하려면 (autoplay = true) 옵션을 적용, @Convert를 삭제 해도 됨
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
