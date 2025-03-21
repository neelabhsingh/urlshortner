package com.spring.urlshortner.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "shortened_urls",
        indexes = {
                @Index(name = "idx_short_key", columnList = "short_key")// Creating index
        }
)
public class ShortenedUrl extends BaseModel{

    @Column(name = "original_url", nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(name = "short_key", nullable = false, unique = true, length = 10)
    private String shortKey;

    @Column(name = "click_count", nullable = false)
    private Long clickCount = 0L;

    @Column(name = "expires_at")
    private Long expiresAt; // Optional expiry time

}
