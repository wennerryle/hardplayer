package com.example.hardplayer.ui.components.tracks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardplayer.R;
import com.example.hardplayer.data.Track;
import com.example.hardplayer.ui.components.playlistcarousel.RecycleViewPlaylistAdapter;

import java.util.ArrayList;

public class RecyclerViewTracksAdapter extends RecyclerView.Adapter<TracksHolder> {
    ArrayList<Track> tracks = new ArrayList<>();

    public RecyclerViewTracksAdapter(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public TracksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        return new TracksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TracksHolder holder, int position) {
        Track currentTrack = tracks.get(position);

        holder.trackName.setText(currentTrack.getName());
        holder.trackAuthors.setText(currentTrack.getAuthor());
        holder.trackFavorite.setChecked(currentTrack.getIsFavorite());

        // TODO: Change this background to a real
        holder.trackImage.setImageResource(R.drawable.basic_background);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }
}
