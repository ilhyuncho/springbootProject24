package com.example.cih.config;

import com.querydsl.core.annotations.Config;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

//@Configuration
//public class PropertyConfig {
//
//    @Bean
//    public PropertiesFactoryBean member() throws Exception{
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        ClassPathResource classPathResource = new ClassPathResource("application.properties");
//
//        propertiesFactoryBean.setLocation(classPathResource);
//        propertiesFactoryBean.setFileEncoding(StandardCharsets.UTF_8.toString());
//        return propertiesFactoryBean;
//    }
//}
