package com.example.cih.common.validator;

import com.example.cih.domain.car.CarSize;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
public class CarGradeValidator implements ConstraintValidator<CarGradeVali, CarSize > {

    private CarGradeVali annotation;
    @Override
    public void initialize(CarGradeVali constraintAnnotation) {
        //ConstraintValidator.super.initialize(constraintAnnotation);
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(CarSize value, ConstraintValidatorContext context) {

        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value == enumValue) {
                    //log.info("CarGradeValidator isValid : value=" + value + "enumValue=" + enumValue );
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
