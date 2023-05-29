package com.example.hardplayer;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.TransitionAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hardplayer.databinding.ActivityMainBinding;
import com.example.hardplayer.mediaplayer.SharedMediaPlayer;
import com.example.hardplayer.models.Track;
import com.example.hardplayer.models.TrackBuilder;
import com.example.hardplayer.ui.components.playlistcarousel.RecycleViewPlaylistAdapter;
import com.example.hardplayer.ui.components.viewpager.ViewPagerAdapter;
import com.example.hardplayer.ui.fragments.AllTracksFragment;
import com.example.hardplayer.ui.fragments.FavoriteTracksFragment;
import com.example.hardplayer.utils.TimeFormatter;
import com.google.android.material.slider.Slider;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import og.android.lib.toggleiconview.ToggleIconView;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private MainViewModel vm;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // init components
        ViewPager2 viewPager = findViewById(R.id.main_screen_viewpager);

        vm = new ViewModelProvider(this).get(MainViewModel.class);

        Handler tracksDataHandler = new Handler(Looper.getMainLooper());
        Runnable onSecondHandler = new Runnable() {
            @Override
            public void run() {
                binding.playerSlider.setValue(SharedMediaPlayer.getInstance().getCurrentPosition());
                binding.playerBackTimerCollapsed.setText(TimeFormatter.formatMilliseconds(
                        vm.currentTrack.getValue().getDuration() - SharedMediaPlayer.getInstance().getCurrentPosition()
                ));
                tracksDataHandler.postDelayed(this, 1000);
            }
        };

        vm.currentTrack.observe(this, track -> {
            Glide
                    .with(this)
                    .load(track.getAlbumImage())
                    .override(Resources.getSystem().getDisplayMetrics().widthPixels,
                                Resources.getSystem().getDisplayMetrics().heightPixels)
                    .transition(withCrossFade())
                    .placeholder(R.drawable.placeholder)
                    .into(binding.playerBackBackground);

            binding.playerTrackName.setText(track.getTitle());
            binding.playerTrackAuthor.setText(track.getArtists());
            binding.playerFavoriteCheckbox.setActivated(track.getIsFavorite());

            //ура мешаем бизнес логику и отображение, оторвать бы себе руки

            SharedMediaPlayer.getInstance().stop();
            SharedMediaPlayer.getInstance().release(); // free memory
            SharedMediaPlayer.setMediaPlayer(
                    MediaPlayer.create(this, track.getTrackLocation())
            ); // prepare track
            SharedMediaPlayer.getInstance().start();

            binding.playerTrackTime.setText(TimeFormatter.formatMilliseconds(
                    track.getDuration()
            ));

            binding.playerSlider.setValueTo(track.getDuration());
            binding.playerSlider.setValue(0);
            tracksDataHandler.post(onSecondHandler);
        });

        vm.currentTrackTimeInMs.observe(this, trackInMS -> {
            String formattedTime = TimeFormatter.formatMilliseconds(trackInMS);

            binding.playerBackTimerExpanded.setText(formattedTime);
            binding.playerBackTimerCollapsed.setText(formattedTime);
            binding.playerSeekbarTimer.setText(formattedTime);
        });

        binding.playerSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                binding.playerSeekbarTimerFramelayout.animate().alpha(1f);
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                binding.playerSeekbarTimerFramelayout.animate().alpha(0f);
            }
        });

        binding.playerSlider.addOnChangeListener((slider, value, fromUser) -> {
            vm.setCurrentTrackTimeInMs((int) value);
            if (fromUser)
                SharedMediaPlayer.getInstance().seekTo((int) value);
        });

        RecyclerView playlistsRV = findViewById(R.id.main_screen_recycler_view_playlists);
        playlistsRV.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        RecycleViewPlaylistAdapter adapterPlaylists = new RecycleViewPlaylistAdapter();
        adapterPlaylists.setPlaylists(vm.playlists);
        playlistsRV.setAdapter(adapterPlaylists);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(
                new ViewPagerAdapter(getSupportFragmentManager(),
                getLifecycle(), List.of(new AllTracksFragment(), new FavoriteTracksFragment()))
        );

        String[] tabTexts = new String[] {"Все", "Избранные"};
        new TabLayoutMediator(findViewById(R.id.main_screen_tab_layout),
                viewPager,
                false,
                true,
                ((tab, position) -> tab.setText(tabTexts[position]))
        ).attach();

        findViewById(R.id.player_play_button).setOnClickListener(button -> {
            ((ToggleIconView) button.findViewById(R.id.avd_play_and_stop)).toggle();

            System.out.println("hello world!");
        });

        ((MotionLayout) findViewById(R.id.player_background)).setTransitionListener(new TransitionAdapter() {
            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                binding.playerBackTimerCollapsed.post(() -> {
                    binding.playerFavoriteCheckbox.setAlpha(progress * 2);
                    binding.playerBackTimerCollapsed.setAlpha(1 - progress * 6);

                    if (progress < 0.1f)
                        binding.playerFavoriteCheckbox.setVisibility(View.GONE);
                    else
                        binding.playerFavoriteCheckbox.setVisibility(View.VISIBLE);
                });
            }
        });

        MainActivityPermissionsDispatcher.getSongsWithPermissionCheck(this);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void getSongs() {
        ArrayList<Track> tracks = new ArrayList<>();

        ContentResolver contentResolver = this.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(songUri,
                null,
                null,
                null,
                null);

        if (cursor == null) return;
        if(!cursor.moveToFirst()) return;

        do {
            long trackID = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            long albumID = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            String trackTitle = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String trackArtists = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

            Uri trackLocation = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, trackID
            );

            Uri album_uri = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    albumID
            );

            tracks.add(TrackBuilder
                    .aTrack()
                    .withId(trackID)
                    .withAlbumID(albumID)
                    .withTitle(trackTitle)
                    .withArtists(trackArtists)
                    .withTrackLocation(trackLocation)
                    .withAlbumImage(album_uri)
                    .withSize(size)
                    .withDuration(duration)
                    .build()
            );
        } while (cursor.moveToNext());

        vm.setTracks(tracks);
        cursor.close();
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForStorage(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("Отсутствует разрешение!")
                .setMessage("Этот аудиоплеер использует память устройства, для поиска в нём аудио.")
                .setPositiveButton("Хорошо", (dialog, button) -> request.proceed())
                .setNegativeButton("Неа", (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showDeniedForStorage() {
        Toast.makeText(this, "Разрешите приложение доступ к файлам!", Toast.LENGTH_SHORT).show();
        showSettingsOfApp();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showNeverAskForStorage() {
        Toast.makeText(this, "Разрешите приложение доступ к файлам!", Toast.LENGTH_SHORT).show();
        showSettingsOfApp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void showSettingsOfApp() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
