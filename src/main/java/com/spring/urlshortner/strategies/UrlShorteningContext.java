package com.spring.urlshortner.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UrlShorteningContext {
    private final UrlShorteningStrategy strategy;

    @Autowired
    public UrlShorteningContext(@Qualifier("hashingShorteningStrategy") UrlShorteningStrategy strategy) {
        this.strategy = strategy;
    }

    public String generateShortKey(String longUrl) {
        return strategy.generateShortKey(longUrl);
    }
}