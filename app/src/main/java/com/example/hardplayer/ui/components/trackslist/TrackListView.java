package com.example.hardplayer.ui.components.trackslist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardplayer.R;
import com.example.hardplayer.data.Track;
import com.example.hardplayer.ui.components.tracks.RecyclerViewTracksAdapter;

import java.util.ArrayList;

public class TrackListView extends LinearLayout {
    ArrayList<Track> tracks = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView warningTextView;

    public TrackListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // we're using "true" because it's our component. And the parent tag is merge.
        // see https://developer.alexanderklimov.ru/android/theory/layoutinflater.php
        inflater.inflate(R.layout.view_trackslist, this, true);

        recyclerView = (RecyclerView) getChildAt(0);
        warningTextView = (TextView) getChildAt(1);

        recyclerView.setAdapter(new RecyclerViewTracksAdapter(tracks));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // recyclerview is not visible if items count is 0
        checkCountsTrack();
    }

    private void checkCountsTrack() {
        if(tracks.size() == 0)
            warningTextView.setVisibility(VISIBLE);
        else
            warningTextView.setVisibility(GONE);
    }

    public void setCustomWarningName(String warningName) {
        warningTextView.setText(warningName);
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
        recyclerView.setAdapter(new RecyclerViewTracksAdapter(tracks));
        checkCountsTrack();
    }
}
