package com.rafael_men.encurta_link_api.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("URL Shortener API")
                        .description("API para encurtamento de URLs com suporte a expiração, personalização e autenticação")
                        .version("v1.0")

                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação externa")
                        .url("https://github.com/rafael-men/encurta-link-web-app"));
    }
}
