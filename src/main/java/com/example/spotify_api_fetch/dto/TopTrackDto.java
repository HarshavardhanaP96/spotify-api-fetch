package com.example.spotify_api_fetch.dto;

public class TopTrackDto {
    private String id;
    private String name;
    private String artist;
    private String album;
    private String imageUrl;

    public TopTrackDto() {}

    public TopTrackDto(String id, String name, String artist, String album, String imageUrl) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public String getImageUrl() { return imageUrl; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setAlbum(String album) { this.album = album; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
