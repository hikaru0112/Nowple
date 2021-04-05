package com.example.nowple;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Build;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.List;


import static android.media.MediaMetadata.METADATA_KEY_ALBUM;
import static android.media.MediaMetadata.METADATA_KEY_ALBUM_ART;
import static android.media.MediaMetadata.METADATA_KEY_ALBUM_ARTIST;
import static android.media.MediaMetadata.METADATA_KEY_ALBUM_ART_URI;
import static android.media.MediaMetadata.METADATA_KEY_ART;
import static android.media.MediaMetadata.METADATA_KEY_ARTIST;
import static android.media.MediaMetadata.METADATA_KEY_AUTHOR;
import static android.media.MediaMetadata.METADATA_KEY_COMPOSER;
import static android.media.MediaMetadata.METADATA_KEY_DISPLAY_ICON;
import static android.media.MediaMetadata.METADATA_KEY_DISPLAY_TITLE;
import static android.media.MediaMetadata.METADATA_KEY_TITLE;
import static android.media.MediaMetadata.METADATA_KEY_YEAR;
import static java.lang.Long.getLong;

public class NotificationLisner extends NotificationListenerService {

    static PlayingMetadata playingMetadata;
    static  Boolean isUpdateFlg = false;
    static  Boolean isNextSong = false;
    static   Main_page_view view;

    Main_page_view mv;
    public NotificationLisner() {
        super();
        playingMetadata = new PlayingMetadata();
    }
    String nextSongCheck;






    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if(sbn == null) return;
        Log.d("test","PostedNotification");
      //  StatusBarNotification[] notifications_list = getActiveNotifications();

        if(!sbn.getPackageName().equals(getPackageName())){
            Notification notification = sbn.getNotification();
            MediaMetadata metadata = null;
            List<MediaController> aaa = getSystemService(MediaSessionManager.class).getActiveSessions(new ComponentName(getApplicationContext(), NotificationLisner.class));

            if(aaa.isEmpty()){
                return;
            }
            MediaController a =  aaa.get(0);

                if(null != sbn.getPackageName()) {

                    if (a.getPackageName().equals(sbn.getPackageName())) {

                        metadata = a.getMetadata();
                        if(metadata!=null) {

                            if (metadata.containsKey(METADATA_KEY_TITLE)) {

//                                if(metadata.getString(METADATA_KEY_TITLE).equals(nextSongCheck)){
//                                    Log.d("test",playingMetadata.getTitle());
//                                    return;
//                                }
                                nextSongCheck = metadata.getString(METADATA_KEY_TITLE);
                                playingMetadata.setTitle(metadata.getString(METADATA_KEY_TITLE));
                                isNextSong = true;

                                Log.d("test111",playingMetadata.getTitle());
                                Log.d("test",playingMetadata.getTitle());
                            } else {
                                playingMetadata.setTitle("noneTitle");
                            }
                            if (metadata.containsKey(METADATA_KEY_ARTIST)) {

                                playingMetadata.setArtist(metadata.getString(METADATA_KEY_ARTIST));
                            } else {
                                playingMetadata.setArtist("noneArtist");
                            }

                            if (metadata.containsKey(METADATA_KEY_ALBUM)) {

                                playingMetadata.setAlbum(metadata.getString(METADATA_KEY_ALBUM));

                            } else {
                                playingMetadata.setAlbum("noneALBUM");
                            }
                            if(metadata.containsKey(METADATA_KEY_AUTHOR)){
                                playingMetadata.setAuthor(metadata.getString(METADATA_KEY_AUTHOR));
                            }else {
                                playingMetadata.setAuthor("noneAutor");
                            }
                            if(metadata.containsKey(METADATA_KEY_ALBUM_ARTIST)){
                                playingMetadata.setAlbum_artist(metadata.getString(METADATA_KEY_ALBUM_ARTIST));
                            }else{
                                playingMetadata.setAlbum_artist("noneAlbum_Artist");
                            }
                            if(metadata.containsKey(METADATA_KEY_YEAR)){
                                playingMetadata.setYear(metadata.getLong(METADATA_KEY_YEAR));
                            }else{
                                playingMetadata.setYear(0L);
                            }
                            if(metadata.containsKey(METADATA_KEY_COMPOSER)){
                                playingMetadata.setComposer(metadata.getString(METADATA_KEY_COMPOSER));
                            }else{
                                playingMetadata.setAlbum_artist("noneComposer");
                            }

                            Bitmap bitmap = metadata.getBitmap(METADATA_KEY_ALBUM_ART);

                            if(null != bitmap ){
                                playingMetadata.setArt(bitmap);

                                isUpdateFlg = true;

                            }else{
                                try {
                                    playingMetadata.setArt(((BitmapDrawable) notification.getLargeIcon().loadDrawable(getBaseContext())).getBitmap());

                                    isUpdateFlg = true;
                                }catch (NullPointerException e){
                                    Log.d("null","あああ");
                                    e.printStackTrace();

                                }


                                Log.d("test","nooooo");

                            }
                            if(!(view == null)){
                                view.setWillNotDraw(false);
                                view.invalidate();
                            }


                        }else {
                            Log.d("test", "metadataisnon");
                        }

                    }

                    }

            }

//        for(int i=0;i<100;i++) {
//
//            Log.d("test",notifications_list[i].getPackageName());
//        }
    }


}
