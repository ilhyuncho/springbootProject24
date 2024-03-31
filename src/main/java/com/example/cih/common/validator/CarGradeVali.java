package com.example.cih.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME) // 언제까지 효력을 유지할자
@Constraint(validatedBy = CarGradeValidator.class)  // 빈 밸리데이션 제약 사항을 포함하는 애너테이션임을 의미
public @interface CarGradeVali {
    String message() default "Password do not adhere to the specified rule";
    Class<?>[] groups() default {}; //그룹을 지정하면 밸리데이션을 그룹별로 구분해서 적용할 수 있다/
    Class<? extends Payload>[] payload() default {};    // 밸리데이션 클라이언트가 사용하는 메타데이터를 전달하기 위해 사용 ( 예제는
                                                        // 아무런 페이로드(payload)도 지정하지 않는다.
    Class<? extends java.lang.Enum<?>> enumClass() ;
}
