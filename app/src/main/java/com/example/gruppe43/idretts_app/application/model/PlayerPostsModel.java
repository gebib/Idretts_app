package com.example.gruppe43.idretts_app.application.model;


public class PlayerPostsModel {
    private String DatePosted;
    private int Intensity;
    private String Place;
    private String PlayerId;
    private String PlayerImage;
    private String TimePosted;
    private String Title;
    private String FirstName;
    private String LastName;

    public PlayerPostsModel() {
    }

    public PlayerPostsModel(String datePosted, int intensity, String place, String playerId,
                            String playerImage, String timePosted, String title, String firstNAme, String lastName) {
        this.DatePosted = datePosted;
        this.Intensity = intensity;
        this.Place = place;
        this.PlayerId = playerId;
        this.PlayerImage = playerImage;
        this.TimePosted = timePosted;
        this.Title = title;
        this.FirstName = firstNAme;
        this.LastName = lastName;
    }

    public String getDatePosted() {
        return DatePosted;
    }

    public void setDatePosted(String datePosted) {
        DatePosted = datePosted;
    }

    public int getIntensity() {
        return Intensity;
    }

    public void setIntensity(int intensity) {
        Intensity = intensity;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(String playerId) {
        PlayerId = playerId;
    }

    public String getPlayerImage() {
        return PlayerImage;
    }

    public void setPlayerImage(String playerImage) {
        PlayerImage = playerImage;
    }

    public String getTimePosted() {
        return TimePosted;
    }

    public void setTimePosted(String timePosted) {
        TimePosted = timePosted;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getFirstNAme() {
        return FirstName;
    }

    public void setFirstNAme(String firstNAme) {
        FirstName = firstNAme;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
