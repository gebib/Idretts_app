package com.example.gruppe43.idretts_app.application.model;

/**
 * Created by gebi9 on 08-May-17.
 */

public class AbsencesModel {
    String absentPlayerId;
    String activityId;
    public AbsencesModel() {
    }

    public AbsencesModel(String absentPlayerId, String activityId) {
        this.absentPlayerId = absentPlayerId;
        this.activityId = activityId;
    }

    public String getAbsentPlayerId() {
        return absentPlayerId;
    }

    public void setAbsentPlayerId(String absentPlayerId) {
        this.absentPlayerId = absentPlayerId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
