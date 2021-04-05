package com.example.nowple;

import android.graphics.Bitmap;

public class PlayingMetadata {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }



    public String getAlbum_artist() {
        return album_artist;
    }

    public void setAlbum_artist(String album_artist) {
        this.album_artist = album_artist;
    }

    public Bitmap getArt() {
        return art;
    }

    public void setArt(Bitmap art) {
        this.art = art;
    }

    String title;
    String artist;
    String album;
    String author;
    String album_artist;
    String composer;
    Bitmap art;

    public Long getYear() {
        return Year;
    }

    public void setYear(Long year) {
        Year = year;
    }

    Long Year;

}
