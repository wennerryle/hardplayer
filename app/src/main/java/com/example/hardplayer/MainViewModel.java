package com.example.hardplayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hardplayer.models.Playlist;
import com.example.hardplayer.models.Track;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    public ArrayList<Playlist> playlists = new ArrayList<>();

    private MutableLiveData<ArrayList<Track>> mutableTracks = new MutableLiveData<>();
    public LiveData<ArrayList<Track>> tracks = mutableTracks;
}
