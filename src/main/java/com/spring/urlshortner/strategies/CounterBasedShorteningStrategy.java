package com.spring.urlshortner.strategies;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Qualifier("counterBasedShorteningStrategy")
public class CounterBasedShorteningStrategy implements UrlShorteningStrategy{
    private static final AtomicLong counter = new AtomicLong(100000); // Start from large number

    @Override
    public String generateShortKey(String longUrl) {
        long value = counter.getAndIncrement();
        return encodeBase62(value);
    }

    private String encodeBase62(long value) {
        final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(BASE62.charAt((int) (value % 62)));
            value /= 62;
        }
        return sb.reverse().toString();
    }
}
