package com.example.spotify_api_fetch.config;

// src/main/java/com/example/config/ProxyHeaderConfig.java


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class ProxyHeaderConfig {

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();   // <- makes Spring respect HTTPS coming through the proxy
    }
}

