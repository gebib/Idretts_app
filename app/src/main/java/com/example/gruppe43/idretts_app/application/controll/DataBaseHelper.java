package com.example.gruppe43.idretts_app.application.controll;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.view.fragments.FullActivityInfo;
import com.example.gruppe43.idretts_app.application.view.fragments.Tabs;
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

    private ProgressDialog progressDialog;
    private String postOwnerUserFirstName;
    private String postOwnerUserLastName;
    private static String[] activityDataCache;
    private DatabaseReference trainer_posts;


    public String[] getActivityDataCache() {
        return activityDataCache;
    }

    public DataBaseHelper(MainActivity mainActivity) {
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
    public void postTrainerActivity(String title,
                                    String activityDate,
                                    String startTime,
                                    String endTime,
                                    String place,
                                    String intensity,
                                    String activityTextInfo,
                                    String icon,
                                    String editedPostKey) {
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTitle));
        progressDialog.setMessage(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTextInfo));
        progressDialog.show();

        try {
            String get_current_user_id = fbAuth.getCurrentUser().getUid();
            if(editedPostKey.equals("")) {
                trainer_posts = fbTrainerPostsDbRef.push();//creates new ID.
            }else{
                trainer_posts = fbTrainerPostsDbRef.child(editedPostKey);
            }
            getCurrentDate();
            String datePosted = nowDate + "." + nowMonth + "." + nowYear;
            String timePosted = nowHour + ":" + nowMinute;
            trainer_posts.child("title").setValue(title);
            trainer_posts.child("activityDate").setValue(activityDate);
            trainer_posts.child("startTime").setValue(startTime);
            trainer_posts.child("endTime").setValue(endTime);
            trainer_posts.child("place").setValue(place);
            trainer_posts.child("intensity").setValue(intensity);
            if(editedPostKey.equals("")){
                trainer_posts.child("postedDate").setValue(datePosted);
            }
            trainer_posts.child("infoText").setValue(activityTextInfo);
            trainer_posts.child("timePosted").setValue(timePosted);
            trainer_posts.child("icon").setValue(icon);
            if(editedPostKey.equals("")) {
                trainer_posts.child("postedUserId").setValue(get_current_user_id);
            }
            progressDialog.dismiss();
                Toast.makeText(mainActivity,mainActivity.getResources().getString(R.string.toastPostRegSuccess), Toast.LENGTH_SHORT).show();
                mainActivity.showFragmentOfGivenCondition();
                mainActivity.clearBackStack();
                mainActivity.hideKeyboard();
        } catch (DatabaseException dbe) {//db failure
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
            mainActivity.setIsOnNewActivityRegisterPage(true);
        }
    }

    /*//Post player activity
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
    }*/

    //retrieve dataes of the selected activity for displaying
    public void getSelectedActivityInfo(String postKey) {
        activityDataCache = new String[12];
        fbTrainerPostsDbRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
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

                activityDataCache[0] = title;
                activityDataCache[1] = activityDate;
                activityDataCache[2] = startTime;
                activityDataCache[3] = endTime;
                activityDataCache[4] = place;
                activityDataCache[5] = intensity;
                activityDataCache[6] = postedDate;
                activityDataCache[7] = infoText;
                activityDataCache[8] = timePosted;
                activityDataCache[9] = icon;

                String postOwnersId = (String) dataSnapshot.child("postedUserId").getValue();
                getPostOwnerName(postOwnersId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPostOwnerName(String postOwnersId) {
        fbUsersDbRef.child(postOwnersId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstName = (String) dataSnapshot.child("firstName").getValue();
                String lastName = (String) dataSnapshot.child("lastName").getValue();
                postOwnerUserFirstName = firstName;
                postOwnerUserLastName = lastName;
                activityDataCache[10] = postOwnerUserFirstName;
                activityDataCache[11] = postOwnerUserLastName;
                Bundle bundle = new Bundle();
                bundle.putStringArray("postData", activityDataCache);
                FullActivityInfo fullActivityInfoFragment = new FullActivityInfo();
                fullActivityInfoFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = mainActivity.getmFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.replace(R.id.containerView, fullActivityInfoFragment).commit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // showDatabaseDataFetchConnectionError();
            }
        });
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

    //svas changes to database for edited posts.
    public void setEditTrainerPost(String[] editedValues, String postKey) {

    }
}
