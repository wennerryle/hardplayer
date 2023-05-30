package com.example.hardplayer.models;

import android.net.Uri;

public class Track {
    long id;
    long albumID;
    String title;
    Uri trackLocation;
    String artists;
    Uri albumImage;
    int duration;
    int size;
    boolean isFavorite;

    public int getDuration() {
        return duration;
    }

    public int getSize() {
        return size;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public Track(long id, long albumID, String title, Uri trackLocation, String artists, Boolean isFavorite, Uri albumImage, int duration, int size) {
        this.id = id;
        this.albumID = albumID;
        this.title = title;
        this.trackLocation = trackLocation;
        this.artists = artists;
        this.isFavorite = isFavorite;
        this.albumImage = albumImage;
        this.duration = duration;
        this.size = size;
    }

    public Uri getTrackLocation() {
        return trackLocation;
    }

    public Boolean getFavorite() {
        return isFavorite;
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
