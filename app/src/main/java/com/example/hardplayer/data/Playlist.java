package com.example.hardplayer.data;

import android.widget.ImageView;

import java.util.ArrayList;

public class Playlist {
    public String name;
    public String authors;
    public ArrayList<Track> tracks;
    public ImageView image;

    public Playlist(String name, ArrayList<Track> tracks, String authors) {
        this.name = name;
        this.tracks = tracks;
        this.authors = authors;
    }
}
