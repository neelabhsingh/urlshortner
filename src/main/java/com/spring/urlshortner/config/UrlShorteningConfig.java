package com.spring.urlshortner.config;

import com.spring.urlshortner.strategies.HashingShorteningStrategy;
import com.spring.urlshortner.strategies.UrlShorteningContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlShorteningConfig {
    @Bean
    @Qualifier("hashingShorteningContext")
    public UrlShorteningContext hashingShorteningContext() {
        return new UrlShorteningContext(new HashingShorteningStrategy());
    }
}
