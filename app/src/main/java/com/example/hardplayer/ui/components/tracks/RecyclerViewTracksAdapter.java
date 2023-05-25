package com.example.hardplayer.ui.components.tracks;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardplayer.R;
import com.example.hardplayer.models.Track;

import java.util.ArrayList;
import java.util.Objects;

public class RecyclerViewTracksAdapter extends RecyclerView.Adapter<TracksHolder> {

    private final AsyncListDiffer<Track> differ = new AsyncListDiffer<>(this, new DiffUtil.ItemCallback<Track>() {
        @Override
        public boolean areItemsTheSame(@NonNull Track oldItem, @NonNull Track newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Track oldItem, @NonNull Track newItem) {
            return oldItem.equals(newItem);
        }
    });

    public void setTracks(ArrayList<Track> tracks) {
        differ.submitList(tracks);
    }

    @NonNull
    @Override
    public TracksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        return new TracksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TracksHolder holder, int position) {
        Track currentTrack = differ.getCurrentList().get(position);

        holder.trackName.setText(currentTrack.getName());
        holder.trackAuthors.setText(currentTrack.getAuthor());
        holder.trackFavorite.setChecked(currentTrack.getIsFavorite());

        // TODO: Change this background to a real
        holder.trackImage.setImageResource(R.drawable.basic_background);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }
}
