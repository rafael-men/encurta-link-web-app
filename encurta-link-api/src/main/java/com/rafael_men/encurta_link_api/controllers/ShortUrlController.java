package com.rafael_men.encurta_link_api.controllers;

import com.rafael_men.encurta_link_api.dto.ShortUrlDto;
import com.rafael_men.encurta_link_api.dto.ShortUrlResponseDto;
import com.rafael_men.encurta_link_api.models.ShortUrl;
import com.rafael_men.encurta_link_api.repositories.ShortUrlRepository;
import com.rafael_men.encurta_link_api.services.ShortUrlService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encurta/short-urls")
public class ShortUrlController {

    private final ShortUrlService service;


    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Buscar todas as URLS anteriores")
    public List<ShortUrl> getAll() {
        return service.findAll();
    }

    @PostMapping("/generate")
    @Operation(summary = "Gerar Link Curto")
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
