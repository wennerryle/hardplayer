package com.example.hardplayer.models;

import android.net.Uri;

public final class TrackBuilder {
    private long id;
    private long albumID;
    private String title;
    private Uri trackLocation;
    private String artists;
    private Uri albumImage;
    private int duration;
    private int size;
    private Boolean isFavorite = false;

    private TrackBuilder() {
    }

    public static TrackBuilder aTrack() {
        return new TrackBuilder();
    }

    public TrackBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public TrackBuilder withAlbumID(long albumID) {
        this.albumID = albumID;
        return this;
    }

    public TrackBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TrackBuilder withTrackLocation(Uri trackLocation) {
        this.trackLocation = trackLocation;
        return this;
    }

    public TrackBuilder withArtists(String artists) {
        this.artists = artists;
        return this;
    }

    public TrackBuilder withIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
        return this;
    }

    public TrackBuilder withAlbumImage(Uri albumImage) {
        this.albumImage = albumImage;
        return this;
    }

    public TrackBuilder withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public TrackBuilder withSize(int size) {
        this.size = size;
        return this;
    }

    public Track build() {
        return new Track(id, albumID, title, trackLocation, artists, isFavorite, albumImage, duration, size);
    }
}
