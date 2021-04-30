package com.example.nowple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaController2;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaSession2;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import static android.Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE;
import static android.Manifest.permission.MEDIA_CONTENT_CONTROL;
import static android.Manifest.permission.READ_CONTACTS;
import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pager);
        if (Build.VERSION.SDK_INT >= 23) {
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY)
                    != PackageManager.PERMISSION_GRANTED
            ) {
                String[] p = new String[]{ Manifest.permission.ACCESS_NOTIFICATION_POLICY, Manifest.permission.READ_EXTERNAL_STORAGE,BIND_NOTIFICATION_LISTENER_SERVICE};
                ActivityCompat.requestPermissions(this,p, 1);
            }
        }


        FragmentStateAdapter adapter;
        ViewPager2 pager = (ViewPager2) findViewById(R.id.pager);
        adapter =  new PagerAdapter(this);
        pager.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator((TabLayout)findViewById(R.id.indicator),(ViewPager2)findViewById(R.id.pager),(tab, position) -> {
           // tab.setText("OBJECT " + (position + 1));

        }
                );
        tabLayoutMediator.attach();

       pager.setCurrentItem(2);


        if (!NotificationManagerCompat.getEnabledListenerPackages(getApplicationContext())
                .contains(getApplicationContext().getPackageName())) {
            //We dont have access
            Intent intent= new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS);
            //For API level 22+ you can directly use Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent,100);
        } else {
            //Your own logic
            Log.d("aaa", "You have Notification Access");

        }


    }



}


