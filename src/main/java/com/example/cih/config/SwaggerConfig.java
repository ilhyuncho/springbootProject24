package com.example.cih.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    http://localhost:8090/swagger-ui.html

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())    // api가 작성되어 있는 패키지 지정 / basepackage로 지정하여 해당 패키지에 존재하는 api 문서화
                .paths(PathSelectors.any())             // URL 경로를 지정하여 해당 URL에 해당하는 요청만 Swagger API 문서로 만듬
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Sample REST API",
                "This is sample API.",
                "V1",
                "Terms of service",
                new Contact("administrator", "www.example.com", "administrator@email.com"),
                "License of API", "www.example.com", Collections.emptyList());
    }


}
