package com.example.spotify_api_fetch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


//just to check the env injection
@RestController
@RequestMapping("/debug")
public class DebugController {

    @GetMapping("/env")
    public Map<String, String> checkEnv() {
        Map<String, String> env = new HashMap<>();
        env.put("SPOTIFY_CLIENT_ID", System.getenv("SPOTIFY_CLIENT_ID"));
        env.put("SPOTIFY_CLIENT_SECRET", System.getenv("SPOTIFY_CLIENT_SECRET") != null ? "SET" : "NOT SET");
        return env;
    }
}

