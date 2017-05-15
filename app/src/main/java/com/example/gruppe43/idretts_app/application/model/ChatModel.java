package com.example.gruppe43.idretts_app.application.model;

/**
 * Created by gebi9 on 13-May-17.
 */

public class ChatModel {
    String profileImage;
    String senderName;
    String message;
    String date;
    String userKey;

    //sestion starter is the person to the left


    public ChatModel() {
    }

    public ChatModel(String profileImage, String senderName, String message, String date, String userKey) {
        this.profileImage = profileImage;
        this.senderName = senderName;
        this.message = message;
        this.date = date;
        this.userKey = userKey;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
