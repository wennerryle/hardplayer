package com.example.hardplayer.ui.components.tracks;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardplayer.R;

public class TracksHolder extends RecyclerView.ViewHolder {
    TextView trackName;
    TextView trackAuthors;
    ImageView trackImage;
    CheckBox trackFavorite;
    
    public TracksHolder(@NonNull View itemView) {
        super(itemView);
        
        trackName = itemView.findViewById(R.id.track_item_name);
        trackAuthors = itemView.findViewById(R.id.track_item_authors);
        trackImage = itemView.findViewById(R.id.track_item_album_image);
        trackFavorite = itemView.findViewById(R.id.track_item_favorite_checkbox);
    }
}
