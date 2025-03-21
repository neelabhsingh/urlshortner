package com.spring.urlshortner.controllers;

import com.spring.urlshortner.dtos.ShortenUrlRequest;
import com.spring.urlshortner.dtos.ShortenUrlResponse;
import com.spring.urlshortner.services.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ShortenUrlResponse shortenUrl(@RequestBody ShortenUrlRequest shortenUrlRequest) {
        String shortUrl =  urlShortenerService.shortenUrl(shortenUrlRequest.getOriginalUrl(), shortenUrlRequest.getCustomAlias());
        return new ShortenUrlResponse(shortUrl);
    }

    @GetMapping("/{shortKey}")
    public ResponseEntity<?> redirect(@PathVariable String shortKey, HttpServletResponse response) throws IOException {
        return urlShortenerService.getLongUrl(shortKey)
                .map(url -> {
                    try {
                        response.sendRedirect(url);
                        return ResponseEntity.status(HttpStatus.FOUND).build();
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Redirection failed due to an error.");
                    }

                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shortened URL not found"));
    }
}
