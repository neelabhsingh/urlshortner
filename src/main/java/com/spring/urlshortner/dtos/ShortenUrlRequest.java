package com.spring.urlshortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlRequest {
    private String originalUrl;
    private String customAlias; // Optional custom short URL alias
}
