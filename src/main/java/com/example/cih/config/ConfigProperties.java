package com.example.cih.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 사용자 정의 속성
 */
@Configuration
@ConfigurationProperties(prefix = "mail")   // 설정 데이터를 나타내는 클래스임을 표시
@Setter
@Getter
//@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class ConfigProperties {

    private String hostName;
    private int port;
    private String sendName;

}
