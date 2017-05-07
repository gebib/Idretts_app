package com.example.gruppe43.idretts_app.application.controll;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helper_classes.EditCampRecordsDialog;
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.example.gruppe43.idretts_app.application.view.fragments.FullActivityInfo;
import com.example.gruppe43.idretts_app.application.view.fragments.TrainerActivityRegistration;
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

import java.util.ArrayList;


/**
 * Created by gebi9 on 26-Apr-17.
 */

public class DataBaseHelper extends Authentication {

    private ProgressDialog progressDialog;
    private String postOwnerUserFirstName;
    private String postOwnerUserLastName;
    private static String[] activityDataCache;
    private DatabaseReference trainer_posts;
    private DatabaseReference player_posts;
    private DatabaseReference camp_records;
    private String firstANdLastNameOfPlayerPostOwner;

    public String[] getActivityDataCache() {
        return activityDataCache;
    }

    public DataBaseHelper(MainActivity mainActivity) {
        fbTrainerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
        fbPlayerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbAbsenceDbRef = FirebaseDatabase.getInstance().getReference().child("Abcences");
        fbCapRecordsDbRef = FirebaseDatabase.getInstance().getReference().child("CampsRecords");
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
            if (editedPostKey.equals("")) {
                trainer_posts = fbTrainerPostsDbRef.push();//creates new ID.
            } else {
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
            if (editedPostKey.equals("")) {
                trainer_posts.child("postedDate").setValue(datePosted);
            }
            trainer_posts.child("infoText").setValue(activityTextInfo);
            trainer_posts.child("timePosted").setValue(timePosted);
            trainer_posts.child("icon").setValue(icon);
            if (editedPostKey.equals("")) {
                trainer_posts.child("postedUserId").setValue(get_current_user_id);
            }
            progressDialog.dismiss();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.toastPostRegSuccess), Toast.LENGTH_SHORT).show();
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

    //Post player activity
    public void postPlayerActivity(String title, String place, int intensity, String fname, String lastName) {
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTitle));
        progressDialog.setMessage(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTextInfo));
        progressDialog.show();

        try {
            getCurrentDate();
            String user_id = fbAuth.getCurrentUser().getUid();
            String datePosted = nowDate + "." + nowMonth + "." + nowYear;
            String timePosted = nowHour + ":" + nowMinute;


            player_posts = fbPlayerPostsDbRef.push();

            player_posts.child("Title").setValue(title);
            player_posts.child("Place").setValue(place);
            player_posts.child("Intensity").setValue(intensity);
            player_posts.child("FirstName").setValue(fname);
            player_posts.child("TimePosted").setValue(timePosted);
            player_posts.child("DatePosted").setValue(datePosted);
            player_posts.child("LastName").setValue(lastName);
            player_posts.child("PlayerId").setValue(user_id);
            player_posts.child("PlayerImage").setValue("TODO"); //TODO picasso..

            progressDialog.dismiss();

            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.toastPostRegSuccess), Toast.LENGTH_SHORT).show();
            mainActivity.showFragmentOfGivenCondition();
            mainActivity.clearBackStack();
        } catch (DatabaseException dbe) {
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

    //retrieve dataes of the selected activity for displaying
    public void getSelectedActivityInfo(String postKey) {
        activityDataCache = new String[12];
        fbTrainerPostsDbRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String title, activityDate, startTime, endTime, place, intensity, postedDate,
                        infoText, timePosted, icon;
                if (dataSnapshot.exists()) {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //get the name of owner of this post key
    private void getPostOwnerName(String postOwnersId) {
        fbUsersDbRef.child(postOwnersId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // showDatabaseDataFetchConnectionError();
            }
        });
    }

    //delete the selected plost if the user chooses to do so.
    public void deleteSelectedPost(String primaryKey, boolean isTrainerPost) {
        DatabaseReference dbRef;
        try {
            if (isTrainerPost) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
            } else {
                dbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
            }
            dbRef.child(primaryKey).removeValue();
        } catch (DatabaseException dbe) {
            dbe.printStackTrace();
        }
        return;
    }

    public void checkDoesPlayerOwnThePost(final String postKey) {
        fbPlayerPostsDbRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String postOwnerId = (String) dataSnapshot.child("PlayerId").getValue();
                    String currentUserId = fbAuth.getCurrentUser().getUid();
                    if (postOwnerId.equals(currentUserId)) { // then player is allawed to remove the post!
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainActivity);
                        builder1.setTitle(mainActivity.getString(R.string.userClickedTheirPostAlertTitle));
                        builder1.setMessage(mainActivity.getString(R.string.userClickedTheirPostAlertText));

                        builder1.setPositiveButton(mainActivity.getString(R.string.playerPostAlertCANCELlabel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        builder1.setNegativeButton(mainActivity.getString(R.string.playerPostAlertDELETElabel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mainActivity, R.string.playerPostDeleteToast, Toast.LENGTH_SHORT).show();
                                deleteSelectedPost(postKey, false);
                            }
                        });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //init a player post to be posted
    public void prePostPlayerActivity(final String typeOfActivity, final String activityPlace, final int intensity) {
        String currentUserId = fbAuth.getCurrentUser().getUid();
        fbUsersDbRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firstName = (String) dataSnapshot.child("firstName").getValue();
                    String lastName = (String) dataSnapshot.child("lastName").getValue();
                    postPlayerActivity(typeOfActivity, activityPlace, intensity, firstName, lastName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //get players names and id for all players
    public void retrieveAllPlayersNameAndId() {
       final ArrayList<String> playerIds = new ArrayList<>();
        final ArrayList<String> firstNameLastNameArray = new ArrayList<>();

        fbUsersDbRef.addValueEventListener(new ValueEventListener() {////////////////////////////////////////////////////////////////////////////////////////////////
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                   Iterable<DataSnapshot> playerChildren = dataSnapshot.getChildren();

                   for(DataSnapshot players : playerChildren){
                       UsersModel um = players.getValue(UsersModel.class);
                       String fn,ln,userId;
                       fn = um.getFirstName();
                       ln = um.getLastName();
                       userId = players.getKey();

                       firstNameLastNameArray.add(fn+" "+ln);
                       playerIds.add(userId);
                   }
                    EditCampRecordsDialog ecr = new EditCampRecordsDialog();
                    ecr.setPlayerNames(firstNameLastNameArray);
                    ecr.setPlayerIds(playerIds);
                    ecr.show(mainActivity.getmFragmentManager(),"ecd");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
     /*a user can have more tan ONE register//////////////////////////////////////////////////////////////////////////////////////////////////////////*/
    //register player data related to camp matches.
    public void registerPlayerCampDataRecords(int numMinutPlayed, int numRedCard, int numYellowCard, int numGreenCard, int numPerfectPasses, int numScores,String playerId) {
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTitle));
        progressDialog.setMessage(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTextInfo));
        progressDialog.show();
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

            progressDialog.dismiss();

            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.toastPostRegSuccess), Toast.LENGTH_SHORT).show();
            mainActivity.showFragmentOfGivenCondition();
            mainActivity.clearBackStack();
        } catch (DatabaseException dbe) {
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
}
