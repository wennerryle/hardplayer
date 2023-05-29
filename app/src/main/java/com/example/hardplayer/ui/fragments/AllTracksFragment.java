package com.example.hardplayer.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardplayer.MainViewModel;
import com.example.hardplayer.R;
import com.example.hardplayer.models.Track;
import com.example.hardplayer.ui.components.tracks.OnItemClickListener;
import com.example.hardplayer.ui.components.trackslistview.TrackListView;

import java.util.ArrayList;

public class AllTracksFragment extends Fragment {
    private MainViewModel vm;
    private TrackListView trackListView;
    public AllTracksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        View view = inflater.inflate(R.layout.fragment_favorite_tracks, container, false);
        trackListView = view.findViewById(R.id.favorite_tracks_view);

        ArrayList<Track> tracksData = vm.tracks.getValue();

        trackListView.setTracks(tracksData);

        trackListView.setOnClickListener((view1, position) -> {
            Track selectedTrack = trackListView.getTracks().get(position);

            vm.setCurrentTrack(getContext(), selectedTrack);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm.tracks.observe(getViewLifecycleOwner(), (newTracks) -> {
            trackListView.setTracks(newTracks);
        });
    }
}