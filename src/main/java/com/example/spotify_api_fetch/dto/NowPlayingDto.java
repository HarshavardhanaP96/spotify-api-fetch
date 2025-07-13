package com.example.spotify_api_fetch.dto;

public class NowPlayingDto {
    private String name;
    private String artist;
    private String album;
    private boolean isPlaying;
    private String imageUrl;

    public NowPlayingDto() {}

    public NowPlayingDto(String name, String artist, String album, boolean isPlaying, String imageUrl) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.isPlaying = isPlaying;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public boolean isPlaying() { return isPlaying; }
    public String getImageUrl() { return imageUrl; }

    public void setName(String name) { this.name = name; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setAlbum(String album) { this.album = album; }
    public void setPlaying(boolean playing) { isPlaying = playing; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

