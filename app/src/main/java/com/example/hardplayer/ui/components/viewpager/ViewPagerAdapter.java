package com.example.hardplayer.ui.components.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hardplayer.ui.fragments.AllTracksFragment;
import com.example.hardplayer.ui.fragments.FavoriteTracksFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int tabIndex) {
        return switch (tabIndex) {
            case 0 -> new AllTracksFragment();
            default -> new FavoriteTracksFragment();
        };
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
