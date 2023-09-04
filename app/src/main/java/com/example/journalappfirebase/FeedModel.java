package com.example.journalappfirebase;

import android.graphics.Bitmap;

public class FeedModel{
    String title;
    String thoughts;
    Bitmap imageview;

    public FeedModel(String title, String thoughts, Bitmap imageview) {
        this.title = title;
        this.thoughts = thoughts;
        this.imageview = imageview;
    }

    public FeedModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public Bitmap getImageview() {
        return imageview;
    }

    public void setImageview(Bitmap imageview) {
        this.imageview = imageview;
    }
}
