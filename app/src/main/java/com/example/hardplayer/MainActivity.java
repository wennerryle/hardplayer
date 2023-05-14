package com.example.hardplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.TransitionAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardplayer.data.Playlist;
import com.example.hardplayer.ui.components.playlistcarousel.RecycleViewPlaylistAdapter;
import com.example.hardplayer.ui.components.viewpager.ViewPagerAdapter;
import com.example.hardplayer.utils.Debouncer;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import og.android.lib.toggleiconview.ToggleIconView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    float playerAnimationProgress = 0;
    CheckBox favoriteCheckbox;
    ViewPager2 viewPager;
    TextView backTimerCollapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init components
        favoriteCheckbox = findViewById(R.id.player_favorite_checkbox);
        viewPager = findViewById(R.id.main_screen_viewpager);
        backTimerCollapsed = findViewById(R.id.player_back_timer_collapsed);

        RecyclerView playlists = findViewById(R.id.main_screen_recycler_view_playlists);

        playlists.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        ArrayList<Playlist> playlistsData = new ArrayList<>();

        // TODO: TEST DATA! REMOVE IT.
        for(int i = 0; i < 20; i++) {
            playlistsData.add(new Playlist("Ромашки", null, "DVRST, Mamba"));
        }

        playlists.setAdapter(new RecycleViewPlaylistAdapter(playlistsData));

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
        });

        Debouncer backTimerAnimation = new Debouncer(() -> {
            favoriteCheckbox.setAlpha(playerAnimationProgress * 2);
            backTimerCollapsed.setAlpha(1 - playerAnimationProgress * 6);

            if (playerAnimationProgress < 0.1f)
                favoriteCheckbox.setVisibility(View.GONE);
            else
                favoriteCheckbox.setVisibility(View.VISIBLE);
        }, 100);

        ((MotionLayout) findViewById(R.id.player_background)).setTransitionListener(new TransitionAdapter() {
            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                playerAnimationProgress = progress;
                backTimerCollapsed.post(backTimerAnimation::run);
            }
        });

        MainActivityPermissionsDispatcher.requestStorageWithPermissionCheck(this);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void requestStorage() {
        System.out.println("It's work!");
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void showSettingsOfApp() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
