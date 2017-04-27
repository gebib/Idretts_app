package com.example.gruppe43.idretts_app.application.model;

/**
 * Created by gebi9 on 31-Mar-17.
 */

public class TrainerModel {
    String trainerPostTitleDate;
    String trainerPostStartStopTime;
    String trainerPostPlace;
    String trainerPostTime;
    String trainerPostIntensity;
    String trainerPostIcon;

    public TrainerModel() {
    }

    public TrainerModel(String trainerPostTitleDate, String trainerPostStartStopTime, String trainerPostPlace, String trainerPostTime, String trainerPostIntensity, String trainerPostIcon) {
        this.trainerPostTitleDate = trainerPostTitleDate;
        this.trainerPostStartStopTime = trainerPostStartStopTime;
        this.trainerPostPlace = trainerPostPlace;
        this.trainerPostTime = trainerPostTime;
        this.trainerPostIntensity = trainerPostIntensity;
        this.trainerPostIcon = trainerPostIcon;
    }

    public String getTrainerPostTitleDate() {
        return trainerPostTitleDate;
    }

    public void setTrainerPostTitleDate(String trainerPostTitleDate) {
        this.trainerPostTitleDate = trainerPostTitleDate;
    }

    public String getTrainerPostStartStopTime() {
        return trainerPostStartStopTime;
    }

    public void setTrainerPostStartStopTime(String trainerPostStartStopTime) {
        this.trainerPostStartStopTime = trainerPostStartStopTime;
    }

    public String getTrainerPostPlace() {
        return trainerPostPlace;
    }

    public void setTrainerPostPlace(String trainerPostPlace) {
        this.trainerPostPlace = trainerPostPlace;
    }

    public String getTrainerPostTime() {
        return trainerPostTime;
    }

    public void setTrainerPostTime(String trainerPostTime) {
        this.trainerPostTime = trainerPostTime;
    }

    public String getTrainerPostIntensity() {
        return trainerPostIntensity;
    }

    public void setTrainerPostIntensity(String trainerPostIntensity) {
        this.trainerPostIntensity = trainerPostIntensity;
    }

    public String getTrainerPostIcon() {
        return trainerPostIcon;
    }

    public void setTrainerPostIcon(String trainerPostIcon) {
        this.trainerPostIcon = trainerPostIcon;
    }
}
