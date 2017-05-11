package com.example.gruppe43.idretts_app.application.model;

/**
 * Created by gebi9 on 10-May-17.
 */

public class AbcenceControllModel {
    private String absentPlayersId;
    private String activityId;

    public AbcenceControllModel() {
    }

    public AbcenceControllModel(String absentPlayersId, String activityId) {
        this.absentPlayersId = absentPlayersId;
        this.activityId = activityId;
    }

    public String getAbsentPlayersId() {
        return absentPlayersId;
    }

    public void setAbsentPlayersId(String absentPlayersId) {
        this.absentPlayersId = absentPlayersId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
