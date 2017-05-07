package com.example.gruppe43.idretts_app.application.model;

/**
 * Created by gebi9 on 06-May-17.
 */

public class CampRecordsModel {
    private String userId;
    private String activityId;
    private int minutPlayed;
    private int redCard;
    private int yellowCard;
    private int greenCard;
    private int numPerfectPass;
    private int scored;

    public CampRecordsModel() {
    }

    public CampRecordsModel(String userId, String activityId, int minutPlayed, int redCard, int yellowCard, int greenCard, int numPerfectPass, int scored) {
        this.userId = userId;
        this.activityId = activityId;
        this.minutPlayed = minutPlayed;
        this.redCard = redCard;
        this.yellowCard = yellowCard;
        this.greenCard = greenCard;
        this.numPerfectPass = numPerfectPass;
        this.scored = scored;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getMinutPlayed() {
        return minutPlayed;
    }

    public void setMinutPlayed(int minutPlayed) {
        this.minutPlayed = minutPlayed;
    }

    public int getRedCard() {
        return redCard;
    }

    public void setRedCard(int redCard) {
        this.redCard = redCard;
    }

    public int getYellowCard() {
        return yellowCard;
    }

    public void setYellowCard(int yellowCard) {
        this.yellowCard = yellowCard;
    }

    public int getGreenCard() {
        return greenCard;
    }

    public void setGreenCard(int greenCard) {
        this.greenCard = greenCard;
    }

    public int getNumPerfectPass() {
        return numPerfectPass;
    }

    public void setNumPerfectPass(int numPerfectPass) {
        this.numPerfectPass = numPerfectPass;
    }

    public int getScored() {
        return scored;
    }

    public void setScored(int scored) {
        this.scored = scored;
    }
}
