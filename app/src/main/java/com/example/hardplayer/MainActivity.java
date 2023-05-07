package com.example.hardplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.hardplayer.data.Playlist;
import com.example.hardplayer.ui.components.playlistcarousel.RecycleViewPlaylistAdapter;
import com.google.android.material.color.DynamicColors;

import java.util.ArrayList;

import og.android.lib.toggleiconview.ToggleIconView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView playlists = findViewById(R.id.playlists);

        playlists.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        ArrayList<Playlist> playlists1 = new ArrayList<Playlist>();

        // TODO: TEST DATA! REMOVE IT.
        for(int i = 0; i < 20; i++) {
            playlists1.add(new Playlist("Ромашки", null, "DVRST, Mamba"));
        }
        playlists.setAdapter(new RecycleViewPlaylistAdapter(playlists1));

        findViewById(R.id.player_play_button).setOnClickListener(button -> {
            ((ToggleIconView) button.findViewById(R.id.avd_play_and_stop)).toggle();
        });

        // animations for the collapsed / expanded player
        ((MotionLayout) findViewById(R.id.player_background)).setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {}
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {}
            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {}
            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                {
                    CheckBox favoriteCheckbox = findViewById(R.id.player_favorite_checkbox);

                    // hide this id when player is collapsed
                    favoriteCheckbox.setAlpha(progress * 2f);

                    // for disable checkbox when player is collapsed
                    if (progress < 0.1f)
                        favoriteCheckbox.setVisibility(View.GONE);
                    else
                        favoriteCheckbox.setVisibility(View.VISIBLE);
                }

                // hide this id when player is expanded
                findViewById(R.id.player_back_timer_collapsed).setAlpha(1 - progress * 6f);
            }
        });
    }
}

