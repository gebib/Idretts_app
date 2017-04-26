package com.example.gruppe43.idretts_app.application.controll;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.view.fragments.Tabs;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by gebi9 on 22-Apr-17.
 */

public class Authentication extends MainActivity {
    private FirebaseAuth fbAuth;
    private ProgressDialog progressDialog;
    private FragmentActivityInterface mCallback;
    private String nowDate, nowMonth, nowYear, nowHour, nowMinute;

    private DatabaseReference fbTrainerPostsDbRef;
    private DatabaseReference fbPlayerPostsDbRef;
    private DatabaseReference fbUsersDbRef;
    private DatabaseReference fbAbsenceDbRef;
    private DatabaseReference fbCapsDbRef;
    private boolean autHasChild;

    public Authentication() {
        fbAuth = FirebaseAuth.getInstance();
        mCallback = (FragmentActivityInterface) mainContext;

        fbTrainerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
        fbPlayerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbAbsenceDbRef = FirebaseDatabase.getInstance().getReference().child("Abcences");
        fbCapsDbRef = FirebaseDatabase.getInstance().getReference().child("Camps");
    }

    public void signIn(String email, String pass) {
        progressDialog(true, "Sign in", "Signing in ...");
        fbAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mCallback.replaceFragmentWith(new Tabs());
                    mCallback.initAfterLogin();
                    progressDialog(false, "..", "..");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alert("Sign in", "Failed to sign in.. please check your internet connection and correct user email and password, and try again");
                progressDialog(false, "..", "..");
            }
        });
    }

    //register using email and password and add user info to database
    public void FBregisterUserforAuthentication(String email, String pass, String fname, String lname, String age) {
        final String firstName = fname;
        final String lastName = lname;
        final String playerAge = age;
        progressDialog(true, "Registration", "Processing please wait...");
        fbAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String user_id = fbAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = fbUsersDbRef.child(user_id);
                    getCurrentDate();
                    String registeredDate = nowDate + "." + nowMonth + "." + nowYear + " " + nowHour + ":" + nowMinute;
                    boolean confirmation = false;

                    current_user_db.child("firstName").setValue(firstName);
                    current_user_db.child("lastName").setValue(lastName);
                    current_user_db.child("playerAge").setValue(playerAge);
                    current_user_db.child("image").setValue("default");//TODO registering image
                    current_user_db.child("registeredDate").setValue(registeredDate);
                    current_user_db.child("confirmedByCoach").setValue(confirmation);//TODO coach need to confirm and user is restricted until then, remove auto or manually if not confirmed
                    current_user_db.child("isAdmin").setValue("false");

                    progressDialog(false, "..", "..");


                    checkIfThitIsFirstUser();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alert(mainContext.getString(R.string.dbConnectionErrror), mainContext.getString(R.string.dbConnectionErrorTextnfo));
                progressDialog(false, "..", "..");
            }
        });
    }

    //Post trainer activities posts
    public void postTrainerActivity(String title,
                                    String activityDate,
                                    String startTime,
                                    String endTime,
                                    String place,
                                    String intensity) {
        final String dateOfActivity, startingTime, endingTime, gatherPlace, trainingIntensity, postedTime, activityTitle;
        dateOfActivity = activityDate;
        startingTime = startTime;
        endingTime = endTime;
        gatherPlace = place;
        trainingIntensity = intensity;
        postedTime = nowHour;
        activityTitle = title;


        progressDialog(true, "Posting", "Processing please wait...");
        fbTrainerPostsDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user_id = fbAuth.getCurrentUser().getUid();
                DatabaseReference trainer_post_DB = fbTrainerPostsDbRef.child(user_id);
                getCurrentDate();
                String datePosted = nowDate + "." + nowMonth + "." + nowYear + " " + nowHour + ":" + nowMinute;

                trainer_post_DB.child("title").setValue(activityTitle);
                trainer_post_DB.child("activityDate").setValue(dateOfActivity);
                trainer_post_DB.child("startTime").setValue(startingTime);
                trainer_post_DB.child("endTime").setValue(endingTime);
                trainer_post_DB.child("place").setValue(gatherPlace);
                trainer_post_DB.child("intensity").setValue(trainingIntensity);
                trainer_post_DB.child("postTime").setValue(postedTime);
                trainer_post_DB.child("postedDate").setValue(datePosted);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Post player activity
    public void postPlayerActivity(String title,
                                   String date,
                                   String intencity,
                                   String place){
        final String activityTitle,activityDate,activityIntensity,activityPlace;
        activityTitle = title;
        activityDate = date;
        activityIntensity = intencity;
        activityPlace = place;

        progressDialog(true, "Posting", "Processing please wait...");
        fbPlayerPostsDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user_id = fbAuth.getCurrentUser().getUid();
                DatabaseReference player_post_DB = fbPlayerPostsDbRef.child(user_id);
                getCurrentDate();
                String registeredDate = nowDate + "." + nowMonth + "." + nowYear + " " + nowHour + ":" + nowMinute;

                player_post_DB.child("title").setValue(activityTitle);
                player_post_DB.child("activityDate").setValue(activityDate);
                player_post_DB.child("datePosted").setValue(registeredDate);
                player_post_DB.child("intensity").setValue(activityIntensity);
                player_post_DB.child("place").setValue(activityPlace);
                player_post_DB.child("userId").setValue(user_id);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    //depending on if its the first one to register check and init correctly.
    private void checkIfThitIsFirstUser() {
        fbUsersDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //so that the first user to register is identified to be ADMIN!
                //becouse only admin own those default codes
                if (dataSnapshot.getChildrenCount() == 1) {//only visible after reset/ first start!
                    mCallback.requIreAdminPass();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //delete the first user if not validated as admin!
    public void deleteFirstUserNotValid() {
        FirebaseUser currentUser = fbAuth.getCurrentUser();
        currentUser.delete();
        fbUsersDbRef.removeValue();
    }


    //show progress dialog while loading something
    public void progressDialog(Boolean showProgressDialog, String title, String message) {
        if (showProgressDialog) {
            progressDialog = new ProgressDialog(mainContext);
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    public void alert(String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainContext);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //ingen action.
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    //get the current timeDate
    public void getCurrentDate() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        nowDate = today.monthDay + "";
        nowMonth = today.month + 1 + "";
        nowYear = today.year + "";
        nowHour = today.hour + "";
        nowMinute = today.minute + "";
    }


}




































