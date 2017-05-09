package com.example.gruppe43.idretts_app.application.model;

/**
 * Created by gebi9 on 05-May-17.
 */

public class UsersModel {
    //cmmon
    private String firstName;
    private String image;
    private String isAdmin;
    private String lastName;
    private String playerAge;
    private String playerNr;
    private String playerType;
    private String registeredDate;
    private String status;

    //pl_____________________________
    private String confirmedByCoach;
    private String absFb;
    private String absGym;  //absents
    private String absMeet;
    private String absCmp;

    private String rCard;
    private String yCard; //cmp record
    private String gCard;
    private String nMinutesPlayed;
    private String nAccidents;
    private String nGoalGivingPasses;
    private String nScores;

    private String nPersonalTraining;


    //tr_______________________________
    private String nFbAct;
    private String nGymAct; //activity record
    private String nMeetAct;
    private String nCmpAct;


    public UsersModel() {
    }

    public UsersModel(String firstName, String image, String isAdmin, String lastName, String playerAge,
                      String playerNr, String playerType, String registeredDate, String status,
                      String confirmedByCoach, String absFb, String absGym, String absMeet, String absCmp,
                      String rCard, String yCard, String gCard, String nMinutesPlayed, String nAccidents,
                      String nGoalGivingPasses, String nScores, String nPersonalTraining, String nFbAct,
                      String nGymAct, String nMeetAct, String nCmpAct) {
        this.firstName = firstName;
        this.image = image;
        this.isAdmin = isAdmin;
        this.lastName = lastName;
        this.playerAge = playerAge;
        this.playerNr = playerNr;
        this.playerType = playerType;
        this.registeredDate = registeredDate;
        this.status = status;
        this.confirmedByCoach = confirmedByCoach;
        this.absFb = absFb;
        this.absGym = absGym;
        this.absMeet = absMeet;
        this.absCmp = absCmp;
        this.rCard = rCard;
        this.yCard = yCard;
        this.gCard = gCard;
        this.nMinutesPlayed = nMinutesPlayed;
        this.nAccidents = nAccidents;
        this.nGoalGivingPasses = nGoalGivingPasses;
        this.nScores = nScores;
        this.nPersonalTraining = nPersonalTraining;
        this.nFbAct = nFbAct;
        this.nGymAct = nGymAct;
        this.nMeetAct = nMeetAct;
        this.nCmpAct = nCmpAct;
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

    public String getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(String playerNr) {
        this.playerNr = playerNr;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirmedByCoach() {
        return confirmedByCoach;
    }

    public void setConfirmedByCoach(String confirmedByCoach) {
        this.confirmedByCoach = confirmedByCoach;
    }

    public String getAbsFb() {
        return absFb;
    }

    public void setAbsFb(String absFb) {
        this.absFb = absFb;
    }

    public String getAbsGym() {
        return absGym;
    }

    public void setAbsGym(String absGym) {
        this.absGym = absGym;
    }

    public String getAbsMeet() {
        return absMeet;
    }

    public void setAbsMeet(String absMeet) {
        this.absMeet = absMeet;
    }

    public String getAbsCmp() {
        return absCmp;
    }

    public void setAbsCmp(String absCmp) {
        this.absCmp = absCmp;
    }

    public String getrCard() {
        return rCard;
    }

    public void setrCard(String rCard) {
        this.rCard = rCard;
    }

    public String getyCard() {
        return yCard;
    }

    public void setyCard(String yCard) {
        this.yCard = yCard;
    }

    public String getgCard() {
        return gCard;
    }

    public void setgCard(String gCard) {
        this.gCard = gCard;
    }

    public String getnMinutesPlayed() {
        return nMinutesPlayed;
    }

    public void setnMinutesPlayed(String nMinutesPlayed) {
        this.nMinutesPlayed = nMinutesPlayed;
    }

    public String getnAccidents() {
        return nAccidents;
    }

    public void setnAccidents(String nAccidents) {
        this.nAccidents = nAccidents;
    }

    public String getnGoalGivingPasses() {
        return nGoalGivingPasses;
    }

    public void setnGoalGivingPasses(String nGoalGivingPasses) {
        this.nGoalGivingPasses = nGoalGivingPasses;
    }

    public String getnScores() {
        return nScores;
    }

    public void setnScores(String nScores) {
        this.nScores = nScores;
    }

    public String getnPersonalTraining() {
        return nPersonalTraining;
    }

    public void setnPersonalTraining(String nPersonalTraining) {
        this.nPersonalTraining = nPersonalTraining;
    }

    public String getnFbAct() {
        return nFbAct;
    }

    public void setnFbAct(String nFbAct) {
        this.nFbAct = nFbAct;
    }

    public String getnGymAct() {
        return nGymAct;
    }

    public void setnGymAct(String nGymAct) {
        this.nGymAct = nGymAct;
    }

    public String getnMeetAct() {
        return nMeetAct;
    }

    public void setnMeetAct(String nMeetAct) {
        this.nMeetAct = nMeetAct;
    }

    public String getnCmpAct() {
        return nCmpAct;
    }

    public void setnCmpAct(String nCmpAct) {
        this.nCmpAct = nCmpAct;
    }
}