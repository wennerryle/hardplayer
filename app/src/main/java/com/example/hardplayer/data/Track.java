package com.example.hardplayer.data;

public class Track {
    Playlist playlist;
    String name;
    String author;

    public Track(Playlist playlist, String name, String author) {
        this.playlist = playlist;
        this.name = name;
        this.author = author;
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
}
