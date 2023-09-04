package com.example.journalappfirebase;

import android.graphics.Bitmap;

public class FeedModel{
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String title;
    String thoughts;
    String imageview;
String username;
    public FeedModel(String title, String thoughts, String imageview) {
        this.title = title;
        this.thoughts = thoughts;
        this.imageview = imageview;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getImageview() {
        return imageview;
    }

    public void setImageview(String imageview) {
        this.imageview = imageview;
    }
}
