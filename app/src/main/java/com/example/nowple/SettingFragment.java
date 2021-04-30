package com.example.nowple;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Objects;

public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.activity_setting,null);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = Objects.requireNonNull(this.getView()).findViewById(R.id.toolbar);
        ViewGroup.LayoutParams layoutParams =  toolbar.getLayoutParams();
        Rect rect = new Rect();
        Objects.requireNonNull(getActivity()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        layoutParams.height = rect.top;


    }


}
