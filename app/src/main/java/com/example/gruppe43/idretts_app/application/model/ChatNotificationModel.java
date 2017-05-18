package com.example.gruppe43.idretts_app.application.model;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir

public class ChatNotificationModel {
    String fromUserKey;
    String toUserKey;
    String sessionKey;
    String senderName;

    public ChatNotificationModel() {
    }

    public ChatNotificationModel(String fromUserKey, String toUserKey, String sessionKey, String senderName) {
        this.fromUserKey = fromUserKey;
        this.toUserKey = toUserKey;
        this.sessionKey = sessionKey;
        this.senderName = senderName;
    }

    public String getFromUserKey() {
        return fromUserKey;
    }

    public void setFromUserKey(String fromUserKey) {
        this.fromUserKey = fromUserKey;
    }

    public String getToUserKey() {
        return toUserKey;
    }

    public void setToUserKey(String toUserKey) {
        this.toUserKey = toUserKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}

