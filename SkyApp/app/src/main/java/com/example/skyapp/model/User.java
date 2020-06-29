package com.example.skyapp.model;

public class User {

    public String user_email;
   public String user_id;
    public String user_url;
    public String user_phone;
    public String user_username;

    public User() {
    }


public User(String user_email, String user_id, String user_url, String user_phone, String user_username) {
        this.user_email = user_email;
        this.user_id = user_id;
        this.user_url = user_url;
        this.user_phone = user_phone;
        this.user_username = user_username;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }
}
