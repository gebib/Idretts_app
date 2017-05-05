package com.example.gruppe43.idretts_app.application.model;

/**
 * Created by gebi9 on 05-May-17.
 */

public class UsersModel {
    private boolean confirmedByCoach;
    private String firstName;
    private String image;
    private String isAdmin;
    private String lastName;
    private String playerAge;
    private String registeredDate;

    public UsersModel() {
    }

    public UsersModel(boolean confirmedByCoach, String firstName, String image, String isAdmin, String lastName, String playerAge, String registeredDate) {
        this.confirmedByCoach = confirmedByCoach;
        this.firstName = firstName;
        this.image = image;
        this.isAdmin = isAdmin;
        this.lastName = lastName;
        this.playerAge = playerAge;
        this.registeredDate = registeredDate;
    }

    public boolean isConfirmedByCoach() {
        return confirmedByCoach;
    }

    public void setConfirmedByCoach(boolean confirmedByCoach) {
        this.confirmedByCoach = confirmedByCoach;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPlayerAge() {
        return playerAge;
    }

    public void setPlayerAge(String playerAge) {
        this.playerAge = playerAge;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
