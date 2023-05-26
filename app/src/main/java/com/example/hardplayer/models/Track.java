package com.example.hardplayer.models;

public class Track {
    long id;
    long albumID;
    String title;
    String trackLocation;
    String artists;
    Boolean isFavorite;

    public Track(long id, long albumID, String title, String artists, String trackLocation, Boolean isFavorite) {
        this.id = id;
        this.albumID = albumID;
        this.title = title;
        this.artists = artists;
        this.isFavorite = isFavorite;
    }

    public Track(long id, long albumID, String title, String artists, String trackLocation) {
        this.id = id;
        this.albumID = albumID;
        this.title = title;
        this.artists = artists;
        this.trackLocation = trackLocation;
        this.isFavorite = false;
    }

    public long getAlbumID() {
        return albumID;
    }

    public String getTitle() {
        return title;
    }

    public String getArtists() {
        return artists;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public long getId() {
        return id;
    }
}
