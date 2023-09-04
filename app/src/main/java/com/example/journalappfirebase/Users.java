package com.example.journalappfirebase;

import android.graphics.Bitmap;

public class Users {
    String username;
    String password;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Users() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users(String username, String password,String email) {
        this.username = username;
        this.password = password;
    }






}

