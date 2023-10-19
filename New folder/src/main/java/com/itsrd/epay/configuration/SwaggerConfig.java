package com.itsrd.epay.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String AUTH_HEADER = "Authorization";

    private ApiInfo getInfo() {
        return new ApiInfo("Epay application", "Send money easily", "1.0", "Terms Of Service",
                new Contact("Rudresh", "https://github.com/iTs-rd", "rudreshgpt7@gmail.com"),
                "License of APIS", "API license URL", Collections.emptyList());
    }

    private List<SecurityContext> getSecurityContexts() {
        return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEvetything");
        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[]{authorizationScope}));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTH_HEADER, "header");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(getSecurityContexts())
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();

    }


}