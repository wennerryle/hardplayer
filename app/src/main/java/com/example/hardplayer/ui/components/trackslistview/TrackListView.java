package com.example.hardplayer.ui.components.trackslistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardplayer.R;
import com.example.hardplayer.models.Track;
import com.example.hardplayer.ui.components.tracks.OnItemClickListener;
import com.example.hardplayer.ui.components.tracks.RecyclerViewTracksAdapter;

import java.util.ArrayList;

public class TrackListView extends LinearLayout {
    private ArrayList<Track> tracks;
    private TextView warningTextView;
    public RecyclerViewTracksAdapter recyclerViewTracksAdapter;

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public TrackListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        tracks = new ArrayList<>();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // we're using "true" because it's our component. And the parent tag is merge.
        // see https://developer.alexanderklimov.ru/android/theory/layoutinflater.php
        inflater.inflate(R.layout.view_trackslist, this, true);

        RecyclerView recyclerView = (RecyclerView) getChildAt(0);
        warningTextView = (TextView) getChildAt(1);

        recyclerViewTracksAdapter = new RecyclerViewTracksAdapter();

        recyclerView.setAdapter(recyclerViewTracksAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // recyclerview is not visible if items count is 0
        checkCountsTrack();
    }

    private void checkCountsTrack() {
        if(tracks == null || tracks.isEmpty())
            warningTextView.setVisibility(VISIBLE);
        else
            warningTextView.setVisibility(GONE);
    }

    public void setCustomWarningName(String warningName) {
        warningTextView.setText(warningName);
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
        recyclerViewTracksAdapter.setTracks(this.tracks);
        checkCountsTrack();
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        recyclerViewTracksAdapter.setOnClickListener(onItemClickListener);
    }
}
