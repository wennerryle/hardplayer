package com.example.hardplayer.ui.components.playlistcarousel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardplayer.R;
import com.example.hardplayer.models.Playlist;
import java.util.ArrayList;

public class RecycleViewPlaylistAdapter extends RecyclerView.Adapter<PlaylistHolder> {
    private ArrayList<Playlist> playlists;

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
        this.notifyDataSetChanged();
    }

    public RecycleViewPlaylistAdapter(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        return new PlaylistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistHolder holder, int position) {
        Playlist currentPlaylist = playlists.get(position);

        holder.playlistAuthors.setText(currentPlaylist.authors);
        holder.playlistName.setText(currentPlaylist.name);

        // TODO: Change this background to a real
        holder.playlistImage.setImageResource(R.drawable.basic_background);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }
}
