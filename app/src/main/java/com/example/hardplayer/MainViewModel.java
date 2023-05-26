package com.example.hardplayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hardplayer.models.Playlist;
import com.example.hardplayer.models.Track;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    public ArrayList<Playlist> playlists = new ArrayList<>();

    private MutableLiveData<Integer> mutableCurrentTrackTime = new MutableLiveData<Integer>();
    public LiveData<Integer> currentTrackTime = mutableCurrentTrackTime;

    private MutableLiveData<Boolean> mutableIsPlaying = new MutableLiveData<>();
    public LiveData<Boolean> isPlaying = new MutableLiveData<>();

    private MutableLiveData<ArrayList<Track>> mutableTracks = new MutableLiveData<>();
    public LiveData<ArrayList<Track>> tracks = mutableTracks;

    private MutableLiveData<Track> mutableCurrentTrack = new MutableLiveData<>();
    public LiveData<Track> currentTrack = mutableCurrentTrack;

    public void setTracks(ArrayList<Track> tracks) {
        mutableTracks.postValue(tracks);
    }

    public void setCurrentTrack(Track currentTrack) {
        mutableCurrentTrack.postValue(currentTrack);
    }

    public void setCurrentTrackByID(long ID) {
        tracks.getValue().forEach(track -> {
            if(track.getId() == ID)
                mutableCurrentTrack.postValue(track);
        });
    }
}
