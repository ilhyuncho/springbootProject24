package com.example.cih.common.aspect.customArgumentLogging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElapseLoggable {
    // 커스텀 애너테이션 선언

}
