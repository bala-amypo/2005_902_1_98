package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "JWT";

        return new OpenAPI()
                // 🔹 API INFO
                .info(new Info()
                        .title("Micro-Learning Content Recommendation API")
                        .description("REST API for micro-learning content and personalized recommendations")
                        .version("1.0.0")
                )

                // 🔹 SERVER URL
                .servers(List.of(
                        new Server().url("https://9020.408procr.amypo.ai/")
                ))

                // 🔹 SECURITY REQUIREMENT
                .addSecurityItem(
                        new SecurityRequirement().addList(securitySchemeName)
                )

                // 🔹 SECURITY SCHEME
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
