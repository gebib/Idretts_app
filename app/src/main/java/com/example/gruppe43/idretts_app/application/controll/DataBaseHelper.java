package com.example.gruppe43.idretts_app.application.controll;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by gebi9 on 26-Apr-17.
 */

public class DataBaseHelper extends Authentication {

    private boolean isRegistered;
    private String[] activityData;
    private ProgressDialog progressDialog;
    private String postOwnerUserFirstAndLastName;

    public DataBaseHelper(MainActivity mainActivity) {
        isRegistered = false;
        fbTrainerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
        fbPlayerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbAbsenceDbRef = FirebaseDatabase.getInstance().getReference().child("Abcences");
        fbCapsDbRef = FirebaseDatabase.getInstance().getReference().child("Camps");
        fbAuth = FirebaseAuth.getInstance();
        this.mainActivity = mainActivity;
    }


    //if on registration first time admin is confirmed!
    public void setIsAdmin() {
        fbUsersDbRef.child(fbAuth.getCurrentUser().getUid()).child("isAdmin").setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mainActivity);
                    builder1.setTitle(mainActivity.getString(R.string.alert));
                    builder1.setMessage(mainActivity.getString(R.string.setAdminFailured));
                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //ingen action.
                        }
                    });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });
    }

    //Post trainer activities posts
    public boolean postTrainerActivity(String title,
                                       String activityDate,
                                       String startTime,
                                       String endTime,
                                       String place,
                                       String intensity,
                                       String activityTextInfo,
                                       String icon) {
        final String dateOfActivity, startingTime, endingTime, gatherPlace, trainingIntensity, activityTitle, textInfo, actIcon;
        dateOfActivity = activityDate;
        startingTime = startTime;
        endingTime = endTime;
        gatherPlace = place;
        trainingIntensity = intensity;
        activityTitle = title;
        textInfo = activityTextInfo;
        actIcon = icon;

        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTitle));
        progressDialog.setMessage(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTextInfo));
        progressDialog.show();

        try {
            String get_current_user_id = fbAuth.getCurrentUser().getUid();
            DatabaseReference trainer_posts = fbTrainerPostsDbRef.push();//creates new ID for every post.////////////////////////////////////////////
            getCurrentDate();
            String datePosted = nowDate + "." + nowMonth + "." + nowYear;
            String timePosted = nowHour + ":" + nowMinute;
            trainer_posts.child("title").setValue(activityTitle);
            trainer_posts.child("activityDate").setValue(dateOfActivity);
            trainer_posts.child("startTime").setValue(startingTime);
            trainer_posts.child("endTime").setValue(endingTime);
            trainer_posts.child("place").setValue(gatherPlace);
            trainer_posts.child("intensity").setValue(trainingIntensity);
            trainer_posts.child("postedDate").setValue(datePosted);
            trainer_posts.child("infoText").setValue(textInfo);
            trainer_posts.child("timePosted").setValue(timePosted);
            trainer_posts.child("icon").setValue(actIcon);
            trainer_posts.child("postedUserId").setValue(get_current_user_id);
            progressDialog.dismiss();
            isRegistered = true;
        } catch (DatabaseException dbe) {//db failure
            progressDialog.dismiss();
           mainActivity.setIsRegisterSuccesfull(false);
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
            isRegistered = false;
        }
        return isRegistered;
    }

    //Post player activity
    public void postPlayerActivity(String title,
                                   String date,
                                   String intencity,
                                   String place) {
        final String activityTitle, activityDate, activityIntensity, activityPlace;
        activityTitle = title;
        activityDate = date;
        activityIntensity = intencity;
        activityPlace = place;


        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTitle));
        progressDialog.setMessage(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTextInfo));
        progressDialog.show();
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

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
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
        });
    }

    public String[] getTrainerPostData(String childKey) {
        activityData = new String[12];
        fbTrainerPostsDbRef.child(childKey).addValueEventListener(new ValueEventListener() {
            @Override//hvis denne ikke kalles, sjekk model klassen og DB om det noe ikke stemmer! skal sammen stemme di to!
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title, activityDate, startTime, endTime, place, intensity, postedDate,
                        infoText, timePosted, icon;

                title = (String) dataSnapshot.child("title").getValue();
                activityDate = (String) dataSnapshot.child("activityDate").getValue();
                startTime = (String) dataSnapshot.child("startTime").getValue();
                endTime = (String) dataSnapshot.child("endTime").getValue();
                place = (String) dataSnapshot.child("place").getValue();
                intensity = (String) dataSnapshot.child("intensity").getValue();
                postedDate = (String) dataSnapshot.child("postedDate").getValue();
                infoText = (String) dataSnapshot.child("infoText").getValue();
                timePosted = (String) dataSnapshot.child("timePosted").getValue();
                icon = (String) dataSnapshot.child("icon").getValue();

                activityData[0] = title;
                activityData[1] = activityDate;
                activityData[2] = startTime;
                activityData[3] = endTime;
                activityData[4] = place;
                activityData[5] = intensity;
                activityData[6] = postedDate;
                activityData[7] = infoText;
                activityData[8] = timePosted;
                activityData[9] = icon;

                String postOwnersId = (String) dataSnapshot.child("postedUserId").getValue();
                String postOwnersName = getPostOwnerName(postOwnersId);
                activityData[10] = postOwnersName;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showDatabaseDataFetchConnectionError();
            }
        });
        return activityData;
    }

    public String getPostOwnerName(String postOwnersId){
        fbUsersDbRef.child(postOwnersId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstName = (String) dataSnapshot.child("firstName").getValue();
                String lastName = (String) dataSnapshot.child("lastName").getValue();
                postOwnerUserFirstAndLastName = firstName + " "+ lastName;

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                showDatabaseDataFetchConnectionError();
            }
        });
    return  postOwnerUserFirstAndLastName;
    }

    public void showDatabaseDataFetchConnectionError() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainActivity);
        builder1.setTitle(mainActivity.getString(R.string.databaseConnectionErrorFetchTitle));
        builder1.setMessage(mainActivity.getString(R.string.databaseConnectionErrorFetchTextInfo));
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //ingen action.
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
