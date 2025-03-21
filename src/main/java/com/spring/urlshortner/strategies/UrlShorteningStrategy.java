package com.spring.urlshortner.strategies;

public interface UrlShorteningStrategy {
    String generateShortKey(String longUrl);
}