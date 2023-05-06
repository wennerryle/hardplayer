package com.example.hardplayer.ui.components.playlistcarousel;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardplayer.R;

public class PlaylistHolder extends RecyclerView.ViewHolder {
    TextView playlistName;
    TextView playlistAuthors;
    ImageView playlistImage;

    public PlaylistHolder(@NonNull View itemView) {
        super(itemView);

        playlistName = itemView.findViewById(R.id.playlist_item_title);
        playlistAuthors = itemView.findViewById(R.id.playlist_item_authors);
        playlistImage = itemView.findViewById(R.id.playlist_item_album_image);
    }
}
