package com.example.hardplayer.mediaplayer;

import android.media.MediaPlayer;

public class SharedMediaPlayer {
    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getInstance() {
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        return mediaPlayer;
    };

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        SharedMediaPlayer.mediaPlayer = mediaPlayer;
    }
}
