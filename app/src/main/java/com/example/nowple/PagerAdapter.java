package com.example.nowple;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;

public class PagerAdapter extends FragmentStateAdapter {


    public PagerAdapter(@NonNull MainActivity fragment) {
        super(fragment);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {

            return new SettingFragment();
        }

        return new MainFragment();

    }


    @Override
    public int getItemCount() {
        return 2;
    }


}
