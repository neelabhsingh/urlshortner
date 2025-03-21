package com.spring.urlshortner.strategies;

import com.spring.urlshortner.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDShorteningStrategy implements UrlShorteningStrategy {

    @Override
    public String generateShortKey(String longUrl) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }
}
