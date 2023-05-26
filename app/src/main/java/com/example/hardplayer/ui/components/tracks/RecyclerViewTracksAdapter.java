package com.example.hardplayer.ui.components.tracks;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hardplayer.R;
import com.example.hardplayer.models.Track;

import java.util.ArrayList;

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

        holder.trackName.setText(currentTrack.getTitle());
        holder.trackAuthors.setText(currentTrack.getArtists());
        holder.trackFavorite.setChecked(currentTrack.getIsFavorite());

        Glide
                .with(holder.trackImage)
                .load(currentTrack.getAlbumImage())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.trackImage);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }
}
