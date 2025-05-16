package com.tatkal;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi group1Api() {
        return GroupedOpenApi.builder()
                .group("InternalUseOnly")
                .packagesToScan("com.tatkal.controller")
                .pathsToMatch("/InternalUseOnly/**")
                .build();
    }

    @Bean
    public GroupedOpenApi group2Api() {
        return GroupedOpenApi.builder()
                .group("External")
                .packagesToScan("com.tatkal.controller")
                .pathsToMatch("/External/**")
                .build();
    }
}
