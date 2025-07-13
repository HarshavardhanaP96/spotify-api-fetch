package com.example.spotify_api_fetch.service;



import com.example.spotify_api_fetch.dto.NowPlayingDto;
import com.example.spotify_api_fetch.dto.TopTrackDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpotifyService {

    private final OAuth2AuthorizedClientService clientService;
    private final WebClient.Builder webClientBuilder;

    public SpotifyService(OAuth2AuthorizedClientService clientService, WebClient.Builder webClientBuilder) {
        this.clientService = clientService;
        this.webClientBuilder = webClientBuilder;
    }

    private WebClient authorizedClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient("spotify", auth.getName());
        return webClientBuilder
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue())
                .baseUrl("https://api.spotify.com/v1")
                .build();
    }


    @SuppressWarnings("unchecked")
    public Mono<NowPlayingDto> nowPlaying() {
        return authorizedClient()
                .get()
                .uri("/me/player/currently-playing")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r -> Mono.empty())
                .bodyToMono(Map.class)
                .map(data -> {
                    Map<String, Object> item = (Map<String, Object>) data.get("item");
                    String name = (String) item.get("name");

                    List<Map<String, Object>> artists = (List<Map<String, Object>>) item.get("artists");
                    String artist = (String) artists.get(0).get("name");

                    Map<String, Object> album = (Map<String, Object>) item.get("album");
                    String albumName = (String) album.get("name");

                    List<Map<String, Object>> images = (List<Map<String, Object>>) album.get("images");
                    String imageUrl = (String) images.get(0).get("url");

                    boolean isPlaying = (Boolean) data.get("is_playing");

                    return new NowPlayingDto(name, artist, albumName, isPlaying, imageUrl);
                });
    }

    @SuppressWarnings("unchecked")
    public Mono<List<TopTrackDto>> topTracks() {
        return authorizedClient()
                .get()
                .uri("/me/top/tracks?limit=10")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
                    return items.stream().map(item -> {
                        String id = (String) item.get("id");
                        String name = (String) item.get("name");
                        List<Map<String, Object>> artists = (List<Map<String, Object>>) item.get("artists");
                        String artist = (String) artists.get(0).get("name");
                        Map<String, Object> album = (Map<String, Object>) item.get("album");
                        String albumName = (String) album.get("name");
                        List<Map<String, Object>> images = (List<Map<String, Object>>) album.get("images");
                        String imageUrl = (String) images.get(0).get("url");

                        return new TopTrackDto(id, name, artist, albumName, imageUrl);
                    }).collect(Collectors.toList());
                });
    }

    public Mono<Void> pause() {
        return authorizedClient()
                .put()
                .uri("/me/player/pause")
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    public Mono<Void> play(String trackId) {
        return authorizedClient()
                .put()
                .uri("/me/player/play")
                .bodyValue(Map.of("uris", List.of("spotify:track:" + trackId)))
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}

