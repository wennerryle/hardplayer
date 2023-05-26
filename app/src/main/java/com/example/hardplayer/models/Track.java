package com.example.hardplayer.models;

import android.net.Uri;

public class Track {
    long id;
    long albumID;
    String title;
    String trackLocation;
    String artists;
    Boolean isFavorite;
    Uri albumImage;

    public Track(long id, long albumID, String title, String artists, String trackLocation, Uri albumImage) {
        this.id = id;
        this.albumID = albumID;
        this.title = title;
        this.artists = artists;
        this.trackLocation = trackLocation;
        this.isFavorite = false;
        this.albumImage = albumImage;
    }

    public Uri getAlbumImage() {
        return albumImage;
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
