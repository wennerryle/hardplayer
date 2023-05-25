package com.example.hardplayer;

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
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardplayer.models.Playlist;
import com.example.hardplayer.ui.components.playlistcarousel.RecycleViewPlaylistAdapter;
import com.example.hardplayer.ui.components.viewpager.ViewPagerAdapter;
import com.example.hardplayer.utils.SharedThreads;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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
    private CheckBox favoriteCheckbox;
    private ViewPager2 viewPager;
    private TextView backTimerCollapsed;
    private float playerAnimationProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vm = new ViewModelProvider(this).get(MainViewModel.class);

        // init components
        favoriteCheckbox = findViewById(R.id.player_favorite_checkbox);
        viewPager = findViewById(R.id.main_screen_viewpager);
        backTimerCollapsed = findViewById(R.id.player_back_timer_collapsed);

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
                getLifecycle())
        );
        new TabLayoutMediator(findViewById(R.id.main_screen_tab_layout),
                viewPager,
                false,
                true,
                ((tab, position) -> tab.setText(position == 0 ? "Избранные" : "Все" ))
        ).attach();

        findViewById(R.id.player_play_button).setOnClickListener(button -> {
            ((ToggleIconView) button.findViewById(R.id.avd_play_and_stop)).toggle();
            MainActivityPermissionsDispatcher.getSongsWithPermissionCheck(this);
        });

        ((MotionLayout) findViewById(R.id.player_background)).setTransitionListener(new TransitionAdapter() {
            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                playerAnimationProgress = progress;
                backTimerCollapsed.post(() -> {
                    favoriteCheckbox.setAlpha(playerAnimationProgress * 2);
                    backTimerCollapsed.setAlpha(1 - playerAnimationProgress * 6);

                    if (playerAnimationProgress < 0.1f)
                        favoriteCheckbox.setVisibility(View.GONE);
                    else
                        favoriteCheckbox.setVisibility(View.VISIBLE);
                });
            }
        });
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void getSongs() {
        ContentResolver contentResolver = this.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(songUri,
                null,
                null,
                null,
                null);

        if (cursor == null) return;

        cursor.moveToFirst();

        do {
            System.out.println("id " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
            System.out.println("title " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
            System.out.println("artist " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
            System.out.println("data " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
            System.out.println("date added " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
            System.out.println("album id " + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
        } while (cursor.moveToNext());

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
