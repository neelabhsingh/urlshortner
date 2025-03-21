package com.spring.urlshortner.services;

import com.spring.urlshortner.exceptions.ShortKeyAlreadyExistsException;
import com.spring.urlshortner.models.ShortenedUrl;
import com.spring.urlshortner.repositories.UrlRepository;
import com.spring.urlshortner.strategies.HashingShorteningStrategy;
import com.spring.urlshortner.strategies.UrlShorteningContext;
import com.spring.urlshortner.strategies.UrlShorteningStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {
    private final UrlRepository urlRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final UrlShorteningContext urlShorteningContext;
    private final UrlShorteningStrategyFactory strategyFactory;
    @Autowired
    public UrlShortenerService(UrlRepository urlRepository, RedisTemplate<String, String> redisTemplate, UrlShorteningContext urlShorteningContext, UrlShorteningStrategyFactory strategyFactory) {
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
        this.urlShorteningContext = urlShorteningContext;
        this.strategyFactory = strategyFactory;
    }
    public String shortenUrl(String longUrl, String customAlias){
        String shortKey = (customAlias != null && !customAlias.isEmpty())
                ? customAlias
//                :  urlShorteningContext.generateShortKey(longUrl);
                 : strategyFactory.getStrategy().generateShortKey(longUrl);

        if (urlRepository.existsByShortKey(shortKey)) {
            throw new ShortKeyAlreadyExistsException("Short key already exists. Try again!");
        }
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(longUrl);
        shortenedUrl.setShortKey(shortKey);
        shortenedUrl.setClickCount(0L);

        urlRepository.save(shortenedUrl);
        //redisTemplate.opsForValue().set(shortKey, longUrl); // Cache in Redis
        return "https://short.ly/" + shortKey;
    }

    public Optional<String> getLongUrl(String shortKey){
        String cachedUrl = redisTemplate.opsForValue().get(shortKey);
        if(cachedUrl != null){
            return Optional.of(cachedUrl);
        }
        return urlRepository.findByShortKey(shortKey)
                .map(url ->  {
                    redisTemplate.opsForValue().set(shortKey, url.getOriginalUrl(), Duration.ofHours(24));
                    return url.getOriginalUrl();
                });
    }

    private String generateShortKey() {
        String shortKey;
        do {
            shortKey = UUID.randomUUID().toString().substring(0, 8);
        } while (urlRepository.existsByShortKey(shortKey));
        return shortKey;
    }
}