package com.example.spotify_api_fetch.logging;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EnvLogger {
    @PostConstruct
    public void check() {
        String id = System.getenv("SPOTIFY_CLIENT_ID");
        System.out.println("SPOTIFY_CLIENT_ID present? " + (id != null ? id.substring(0,6) + "…" : "NO"));
    }
}

