package com.example.hardplayer.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardplayer.R;
import com.example.hardplayer.data.Track;
import com.example.hardplayer.ui.components.trackslist.TrackListView;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class AllTracksFragment extends Fragment {
    public AllTracksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tracks, container, false);

        TrackListView trackListView = view.findViewById(R.id.favorite_tracks_view);

        ArrayList<Track> tracksData = new ArrayList<>();

        trackListView.setTracks(tracksData);

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            for(int i = 0; i < 100; i++) {
                tracksData.add(new Track(null, "I Hate You I Love You", "DVRST", true));
            }
            return null;
        });

        future.thenAccept(__ -> {
            trackListView.setTracks(tracksData);
        });

        return view;
    }
}