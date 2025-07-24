package com.rafael_men.encurta_link_api.dto;

import jakarta.validation.constraints.NotBlank;


public class ShortUrlDto {

    @NotBlank(message = "A URL original é obrigatória")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
