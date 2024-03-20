package com.example.cih.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
//출처: https://jeongkyun-it.tistory.com/199 [나의 과거일지:티스토리]
public class JpaAuditingConfiguration {
    // @WebMvcTest 어노테이션을 지정해서 테스트를 수행하는 코드를 작성하면 애플리케이션
    // 클래스를 호출하는 과정에서 예외가 발생 하여 따로 분리 함
}
