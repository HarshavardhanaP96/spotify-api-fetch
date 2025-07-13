package com.example.spotify_api_fetch.controller;


import com.example.spotify_api_fetch.dto.NowPlayingDto;
import com.example.spotify_api_fetch.dto.TopTrackDto;
import com.example.spotify_api_fetch.service.SpotifyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping
    public Mono<Map<String, Object>> summary() {
        return Mono.zip(spotifyService.nowPlaying(), spotifyService.topTracks())
                .map(tuple -> Map.of(
                        "now_playing", tuple.getT1(),
                        "top_tracks", tuple.getT2(),
                        "actions", Map.of(
                                "pause", "/spotify/pause",
                                "play", "/spotify/play?track_id={id}"
                        )
                ));
    }

    @PutMapping("/pause")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> pause() {
        return spotifyService.pause();
    }

    @PutMapping("/play")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> play(@RequestParam("track_id") String trackId) {
        return spotifyService.play(trackId);
    }
}

