package com.example.hardplayer;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hardplayer.mediaplayer.SharedMediaPlayer;
import com.example.hardplayer.models.Playlist;
import com.example.hardplayer.models.Track;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private static MediaPlayer mediaPlayer = SharedMediaPlayer.getInstance();

    public ArrayList<Playlist> playlists = new ArrayList<>();

    private MutableLiveData<ArrayList<Track>> mutableTracks = new MutableLiveData<>();
    public LiveData<ArrayList<Track>> tracks = mutableTracks;

    private MutableLiveData<Track> mutableCurrentTrack = new MutableLiveData<>();
    public LiveData<Track> currentTrack = mutableCurrentTrack;

    public void setTracks(ArrayList<Track> tracks) {
        mutableTracks.postValue(tracks);
    }

    public void setCurrentTrack(@NonNull Context context, Track currentTrack) {
        mutableCurrentTrack.postValue(currentTrack);
    }
}
