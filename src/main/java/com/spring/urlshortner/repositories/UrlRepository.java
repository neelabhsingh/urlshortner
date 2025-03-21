package com.spring.urlshortner.repositories;

import com.spring.urlshortner.models.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<ShortenedUrl, Long> {

    Optional<ShortenedUrl> findByShortKey(String shortCode);

    boolean existsByShortKey(String shortKey);
}
