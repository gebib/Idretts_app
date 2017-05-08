package com.example.gruppe43.idretts_app.application.controll;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.view.fragments.TrainerActivityRegistration;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.firebase.database.DatabaseException;

import java.util.ArrayList;

/**
 * Created by gebi9 on 08-May-17.
 */

public class DataBaseHelperB extends DataBaseHelperA {


    public DataBaseHelperB(MainActivity mainActivity) {
        super(mainActivity);
    }

    //show general loading
    public void showGeneralProgressDialog(Boolean state){
        if(state){
            progressDialog = new ProgressDialog(mainActivity);
            progressDialog.setTitle(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTitle));
            progressDialog.setMessage(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTextInfo));
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }

    }

    //show general db failure alert
    public void showGeneralDbExceptionAlert(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainActivity);
        builder1.setTitle(mainActivity.getString(R.string.activityRegistrationFailureTitle));
        builder1.setMessage(mainActivity.getString(R.string.activityRegistrationFailureTextIinfo));
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //ingen action.
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /*a user can have more tan ONE register//////////////////////////////////////////////////////////////////////////////////////////////////////////*/
    //register player data related to camp matches.
    public void registerPlayerCampDataRecords(int numMinutPlayed, int numRedCard, int numYellowCard, int numGreenCard, int numPerfectPasses, int numScores,String playerId) {
            showGeneralProgressDialog(true);
        try {
            camp_records = fbCapRecordsDbRef.push();

            camp_records.child("userId").setValue(playerId);
            camp_records.child("activityId").setValue(TrainerActivityRegistration.getSelectedActivityPostKey());
            camp_records.child("minutPlayed").setValue(numMinutPlayed);
            camp_records.child("redCard").setValue(numRedCard);
            camp_records.child("yellowCard").setValue(numYellowCard);
            camp_records.child("greenCard").setValue(numGreenCard);
            camp_records.child("numPerfectPass").setValue(numPerfectPasses);
            camp_records.child("scored").setValue(numScores);

            showGeneralProgressDialog(false);
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.toastPostRegSuccess), Toast.LENGTH_SHORT).show();
            mainActivity.showFragmentOfGivenCondition();
            mainActivity.clearBackStack();
        } catch (DatabaseException dbe) {
            showGeneralProgressDialog(false);
            mainActivity.setIsOnNewActivityRegisterPage(true);
            showGeneralDbExceptionAlert();
        }
    }
    //when trainer has checked all absence players for an activity register those.
    public void registerAbsence(ArrayList<String> absentPlayersIds, String activityKey) {
        showGeneralProgressDialog(true);
        try {
            for (int i = 0; i < absentPlayersIds.size(); i++) {
                absent_records = fbAbsenceDbRef.push();
                absent_records.child("absentPlayersId").setValue(absentPlayersIds.get(i));
                absent_records.child("activityId").setValue(activityKey);
            }
            showGeneralProgressDialog(false);
        }catch (DatabaseException dbe){
            showGeneralProgressDialog(false);
            showGeneralDbExceptionAlert();
        }
    }

    //when a user is clicked in team, get the data to be displayed about this user.
    public void getProfileViewDataForUser(String userId) {
        //TODO if this user == key show edit else turn off editing
    }
}
