package com.example.nowple;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.palette.graphics.Palette;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Main_page_view extends View {

    public Main_page_view(Context context) {
        super(context);
    }

    public Main_page_view(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    public Main_page_view(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }


    float width;
    float height;
    Boolean isDraw =false;
    Bitmap al_art =null;
    String title ="",artist="";
    TextView  artistTextView,titleTextView;
    int color = Color.DKGRAY;;
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        super.onDraw(canvas);
        Paint paint = new Paint();
        Paint p2 = new Paint();

        isDraw =true;
        if(null == canvas){
            return;
        }

        if(null != NotificationLisner.playingMetadata) {


            if (null != NotificationLisner.playingMetadata.getArt() && NotificationLisner.isUpdateFlg) {
                al_art = NotificationLisner.playingMetadata.getArt();

            }

            if(null  != NotificationLisner.playingMetadata.getTitle()&& NotificationLisner.isUpdateFlg) {
                Log.d("log", "draw=====");

                title = NotificationLisner.playingMetadata.getTitle();


            }

            if(null  != NotificationLisner.playingMetadata.getArtist() && NotificationLisner.isUpdateFlg) {
                Log.d("log", "draw=====");
                artist = NotificationLisner.playingMetadata.getArtist();

            }

            if( !(titleTextView == null) && !(artistTextView == null) && NotificationLisner.isUpdateFlg) {
                titleTextView.setText(title);
                artistTextView.setText(artist);

            }

            NotificationLisner.isUpdateFlg = false;
        }
        paint.setAntiAlias(true);
        canvas.drawColor(Color.WHITE);

        if( al_art != null) {
            int size = (int) (height / 1.42);
            canvas.drawBitmap(Bitmap.createScaledBitmap(al_art, size, size, false), width / 2 - size / 2, 0, new Paint());
            Palette palette = Palette.from(al_art).generate();
            color = Palette.from(al_art).maximumColorCount(25).generate().getDominantColor(Color.DKGRAY);
            titleTextView.setTextColor(-color+0xFF000000);
            artistTextView.setTextColor(-color+0xFF000000);
        }


        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);

        canvas.drawRect(0,(int)(height*0.67),(int)width,(int)height,paint);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LinearGradient gradient = new LinearGradient(0,height*0.67f, 0,height*0.30f,new int[]{color,Color.TRANSPARENT},null, Shader.TileMode.CLAMP);
            paint.setDither(true);
            paint.setShader(gradient);
        }else{
            paint.setColor(color);
        }

        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,(int)(height*0.30),(int)width,(int)(height*0.67),paint);


    }
    float oldx = 0;
    float oldy =0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldx = event.getX();
                oldy = event.getY();
                // something to do
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() - oldx < 100 && event.getX() - oldx > -100) {

                    if(NotificationLisner.playingMetadata == null){
                        return false;
                    }

                    if(NotificationLisner.playingMetadata.getTitle() == null){
                        return false;
                    }
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, NotificationLisner.playingMetadata.title + "\n#NowPlaying");
                    if(!(NotificationLisner.playingMetadata.getArt() == null)) {
                        try {
                            intent.putExtra(Intent.EXTRA_STREAM, getImageUri(getContext(), NotificationLisner.playingMetadata.art));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    intent.setType("image/png");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                    try {
                        this.getRootView().getContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
                    }

                }
                // something to do
                break;
            case MotionEvent.ACTION_MOVE:
                // something to do
                break;
            case MotionEvent.ACTION_CANCEL:
                // something to do
                break;
        }

        return true;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) throws IOException {
        try(FileOutputStream fos = getRootView().getContext().openFileOutput("art.png",Context.MODE_PRIVATE)){
            inImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        }

        File file = new File(getRootView().getContext().getFilesDir(),"art.png");

        return FileProvider.getUriForFile(getRootView().getContext(),BuildConfig.APPLICATION_ID + ".provider",file);


    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        width = getWidth();
        height = getHeight();
        artistTextView = getRootView().findViewById(R.id.artist);
        titleTextView = getRootView().findViewById(R.id.title);
        artistTextView.setSelected(true);
        titleTextView.setSelected(true);
        NotificationLisner.view = this;
//        artistTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        titleTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }


}
