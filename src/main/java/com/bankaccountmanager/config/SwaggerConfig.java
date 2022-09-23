package com.bankaccountmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact(
            "Tanushree Khanduri", "http://localhost:8080/", "tanushree.khanduri24@gmail.com");
    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Bank Account Management App", "Assignment created for managing bank accounts", "1.0",
            "urn:tos", DEFAULT_CONTACT,
            "License 1.0", "http://localhost:8080/licenses/LICENSE-1.0", Arrays.asList());
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.bankaccountmanager.controller"))
                .paths(PathSelectors.any())
                .build();
    }

}
