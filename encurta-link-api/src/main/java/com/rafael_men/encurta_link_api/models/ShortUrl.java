package com.rafael_men.encurta_link_api.models;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "short_url")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(unique = true,nullable = false,length = 10)
    private String shortCode;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Long visitCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    public ShortUrl() {}

    public ShortUrl(UUID id, String shortCode, String originalUrl, LocalDateTime createdAt, LocalDateTime expiresAt, Long visitCount) {
        this.id = id;
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.visitCount = visitCount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ShortUrl shortUrl = (ShortUrl) object;
        return Objects.equals(id, shortUrl.id) && Objects.equals(originalUrl, shortUrl.originalUrl) && Objects.equals(shortCode, shortUrl.shortCode) && Objects.equals(createdAt, shortUrl.createdAt) && Objects.equals(expiresAt, shortUrl.expiresAt) && Objects.equals(visitCount, shortUrl.visitCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalUrl, shortCode, createdAt, expiresAt, visitCount);
    }
}
