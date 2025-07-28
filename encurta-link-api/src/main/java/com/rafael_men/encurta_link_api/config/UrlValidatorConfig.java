package com.rafael_men.encurta_link_api.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlValidatorConfig {
    @Bean
    public UrlValidator urlValidator() {
        return new UrlValidator(new String[]{"http","https"});
    }
}
