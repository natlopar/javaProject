package com.BlogAuJ.AuJ.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BlogAuJ",
                version = "1.0",
                description = "Blog to adopt a junior "
        )
)
public class OpenApiConfig {
    
}
