package com.example.gruppe43.idretts_app.application.model;

/**
 * Created by gebi9 on 31-Mar-17.
 */

public class TrainerModel {
    String activityDate;//OPSI OPSI!!
    String endTime;
    String infoText;
    String intensity;
    String place;
    String postedDate;
    String startTime;
    String title;
    String timePosted;
    String icon;

    public TrainerModel() {
    }

    public TrainerModel(String activityDate, String endTime, String infoText, String intensity,
                        String place, String postedDate, String startTime, String title,String timePosted,String icon) {
        this.activityDate = activityDate;
        this.endTime = endTime;
        this.infoText = infoText;
        this.intensity = intensity;
        this.place = place;
        this.postedDate = postedDate;
        this.startTime = startTime;
        this.title = title;
        this.timePosted = timePosted;
        this.icon = icon;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }
}
