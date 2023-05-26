package com.example.hardplayer.ui.components.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hardplayer.ui.fragments.AllTracksFragment;
import com.example.hardplayer.ui.fragments.FavoriteTracksFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager,
                            @NonNull Lifecycle lifecycle,
                            List<Fragment> fragmentList) {
        super(fragmentManager, lifecycle);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int tabIndex) {
        return fragmentList.get(tabIndex);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
