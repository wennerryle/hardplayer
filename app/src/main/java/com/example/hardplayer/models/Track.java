package com.example.hardplayer.models;

public class Track {
    long id;
    Playlist playlist;
    String name;
    String author;
    Boolean isFavorite;

    public Track(long id, Playlist playlist, String name, String author, Boolean isFavorite) {
        this.id = id;
        this.playlist = playlist;
        this.name = name;
        this.author = author;
        this.isFavorite = isFavorite;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public long getId() {
        return id;
    }
}
