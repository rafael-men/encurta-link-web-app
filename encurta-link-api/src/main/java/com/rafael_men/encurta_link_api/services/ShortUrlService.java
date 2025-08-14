package com.rafael_men.encurta_link_api.services;

import com.rafael_men.encurta_link_api.dto.ShortUrlDto;
import com.rafael_men.encurta_link_api.dto.ShortUrlResponseDto;
import com.rafael_men.encurta_link_api.models.ShortUrl;
import com.rafael_men.encurta_link_api.repositories.ShortUrlRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShortUrlService {

    private final ShortUrlRepository repository;
    private final UrlValidator urlValidator;
    private final String baseShortUrl;

    public ShortUrlService(ShortUrlRepository repository, UrlValidator urlValidator, @Value("${app.base-short-url}") String baseShortUrl) {
        this.repository = repository;
        this.urlValidator = urlValidator;
        this.baseShortUrl = baseShortUrl;
    }

    public List<ShortUrl> findAll() {
        return repository.findAll();
    }

    public ShortUrlResponseDto createShortUrl(ShortUrlDto dto) {
        if (!urlValidator.isValid(dto.getOriginalUrl())) {
            throw new IllegalArgumentException("A URL fornecida é inválida.");
        }

        String generatedCode;
        do {
            generatedCode = generateRandomCode();
        } while (repository.existsByShortCode(generatedCode));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusHours(3);

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(dto.getOriginalUrl());
        shortUrl.setShortCode(generatedCode);
        shortUrl.setCreatedAt(now);
        shortUrl.setExpiresAt(expiration);
        shortUrl.setVisitCount(0L);

        repository.saveAndFlush(shortUrl);


        String completeShortUrl = baseShortUrl + generatedCode;

        return new ShortUrlResponseDto(
                shortUrl.getOriginalUrl(),
                shortUrl.getShortCode(),
                completeShortUrl,
                shortUrl.getCreatedAt(),
                shortUrl.getExpiresAt(),
                shortUrl.getVisitCount()
        );
    }

    private String generateRandomCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public ShortUrlResponseDto getByShortCode(String code) {
        ShortUrl url = repository.findByShortCode(code)
                .orElseThrow(() -> new RuntimeException("URL não encontrada"));

        if (url.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Esta URL expirou.");
        }

        url.setVisitCount(url.getVisitCount() + 1);
        repository.save(url);

        String completeShortUrl = baseShortUrl + url.getShortCode();

        return new ShortUrlResponseDto(
                url.getOriginalUrl(),
                url.getShortCode(),
                completeShortUrl,
                url.getCreatedAt(),
                url.getExpiresAt(),
                url.getVisitCount()
        );
    }
}
