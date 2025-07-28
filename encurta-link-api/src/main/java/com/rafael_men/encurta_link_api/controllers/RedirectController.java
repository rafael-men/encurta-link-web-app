package com.rafael_men.encurta_link_api.controllers;

import com.rafael_men.encurta_link_api.models.ShortUrl;
import com.rafael_men.encurta_link_api.repositories.ShortUrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class RedirectController {

    private final ShortUrlRepository repository;

    public RedirectController(ShortUrlRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/r/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        ShortUrl shortUrl = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL não encontrada"));

        if (shortUrl.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "URL expirou");
        }

        shortUrl.setVisitCount(shortUrl.getVisitCount() + 1);
        repository.save(shortUrl);

        response.sendRedirect(shortUrl.getOriginalUrl());
    }
}
