package com.spring.urlshortner.strategies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UrlShorteningStrategyFactory {
    private final Map<String, UrlShorteningStrategy> strategies;
    private final String selectedStrategy;

    public UrlShorteningStrategyFactory(
            UUIDShorteningStrategy uuidStrategy,
            HashingShorteningStrategy hashingStrategy,
            CounterBasedShorteningStrategy counterStrategy,
            @Value("${shortening.strategy}") String selectedStrategy) {

        this.strategies = Map.of(
                "uuid", uuidStrategy,
                "hashing", hashingStrategy,
                "counter", counterStrategy
        );
        this.selectedStrategy = selectedStrategy;
    }

    public UrlShorteningStrategy getStrategy() {
        return strategies.getOrDefault(selectedStrategy, new UUIDShorteningStrategy());
    }
}
