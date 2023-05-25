package com.example.hardplayer.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardplayer.MainViewModel;
import com.example.hardplayer.R;
import com.example.hardplayer.models.Playlist;
import com.example.hardplayer.models.Track;
import com.example.hardplayer.ui.components.trackslist.TrackListView;
import com.example.hardplayer.utils.SharedThreads;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class AllTracksFragment extends Fragment {
    private MainViewModel vm;

    public AllTracksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(MainViewModel.class);

        View view = inflater.inflate(R.layout.fragment_favorite_tracks, container, false);

        TrackListView trackListView = view.findViewById(R.id.favorite_tracks_view);

        ArrayList<Track> tracksData = vm.tracks.getValue();

        vm.tracks.observe(getViewLifecycleOwner(), (newTracks) -> {
            trackListView.setTracks(newTracks);
        });

        trackListView.setTracks(tracksData);
        return view;
    }
}