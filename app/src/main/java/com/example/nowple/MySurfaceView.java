package com.example.nowple;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.palette.graphics.Palette;

//現在使われておりません

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    SurfaceHolder holder;
    Thread thread;
    float width;
    float height;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context,attrs);

        holder = getHolder();
        holder.addCallback(this);
        holder.setFixedSize(getWidth(),getHeight());
        Log.d("test","test");


    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new Thread(this);

        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

        this.width = width;
        this.height = height;
        titleXPos = width/96f;
        cnt =  0;
        wait = 0;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    thread = null;
    }

    Bitmap al_art;
    Paint paint = new Paint();
    Paint p2 = new Paint();
    int color = Color.DKGRAY;;
    String artist = "",title = "";
    float titleXPos = 0;
    float cnt;
    int wait;
    float titleWidth = 0;
    @Override
    public void  run(){
        //Canvas canvas = null;

        while ((thread != null)){

            if(null != NotificationLisner.playingMetadata) {


                if (null != NotificationLisner.playingMetadata.getArt() && NotificationLisner.isUpdateFlg) {
                    al_art = NotificationLisner.playingMetadata.getArt();

                }

                if(null  != NotificationLisner.playingMetadata.getTitle()&& NotificationLisner.isUpdateFlg) {
                    Log.d("log", "draw=====");

                    title = NotificationLisner.playingMetadata.getTitle();
                    titleWidth = (getHan1Zen2(title)*(width/9.8f))/2;
                    wait = 0;
                    cnt = 0;

                }

                if(null  != NotificationLisner.playingMetadata.getArtist() && NotificationLisner.isUpdateFlg) {
                    Log.d("log", "draw=====");
                    artist = NotificationLisner.playingMetadata.getArtist();

                }

                NotificationLisner.isUpdateFlg = false;
            }

            Canvas canvas = holder.lockCanvas();
            if(null == canvas){
                return;
            }
            paint.setAntiAlias(true);
            canvas.drawColor(Color.BLACK);

            if( al_art != null) {
                int size = (int) (height/1.2);
                canvas.drawBitmap(Bitmap.createScaledBitmap(al_art,size,size,false), width/2-size/2, 0, new Paint());
                Palette palette = Palette.from(al_art).generate();
                color = Palette.from(al_art).maximumColorCount(20).generate().getDominantColor(Color.DKGRAY);

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


            p2.setColor(-color+0xFF000000);



            p2.setStyle(Paint.Style.FILL_AND_STROKE);
            p2.setTextSize(width/20);
            p2.setAntiAlias(true);


            canvas.drawText(artist, width/93, (float) (height*0.72), p2);

            p2.setStyle(Paint.Style.FILL_AND_STROKE);
            p2.setTextSize(width/9.8f);




            if(titleWidth > width - titleXPos ){
                if(wait > 150) {


                    cnt += 2f;
                    canvas.drawText(title, titleXPos - cnt, (float) (height * 0.785), p2);
                    if (titleWidth - cnt < 0) {
                        cnt = 0;
                        wait = 0;
                    }
                }else{
                    wait++;
                    canvas.drawText(title, titleXPos, (float) (height * 0.785), p2);
                }
            }else {
                canvas.drawText(title, titleXPos, (float) (height * 0.785), p2);

            }

            holder.unlockCanvasAndPost(canvas);
            try {
               // Thread.sleep(100);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        width = getWidth();
        height = getHeight();
    }

    public static int getHan1Zen2(String str) {


        int ret = 0;
        char[] c = str.toCharArray();
        for (char value : c) {
            if (String.valueOf(value).getBytes().length <= 1) {
                ret += 1;
            } else {
                ret += 2;
            }
        }

        return ret;
    }


}
