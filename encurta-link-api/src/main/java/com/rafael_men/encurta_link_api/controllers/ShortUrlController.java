package com.rafael_men.encurta_link_api.controllers;

import com.rafael_men.encurta_link_api.dto.ShortUrlDto;
import com.rafael_men.encurta_link_api.dto.ShortUrlResponseDto;
import com.rafael_men.encurta_link_api.services.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/short-urls")
public class ShortUrlController {

    private final ShortUrlService service;

    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public ResponseEntity<ShortUrlResponseDto> createShortUrl(@RequestBody @Valid ShortUrlDto dto) {
        ShortUrlResponseDto response = service.createShortUrl(dto);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ShortUrlResponseDto> getByCode (@PathVariable String code) {
        ShortUrlResponseDto response = service.getByShortCode(code);
        return ResponseEntity.ok(response);
    }
}
