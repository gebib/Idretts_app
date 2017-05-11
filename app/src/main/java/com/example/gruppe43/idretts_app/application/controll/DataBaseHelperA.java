package com.example.gruppe43.idretts_app.application.controll;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helper_classes.EditCampRecordsDialog;
import com.example.gruppe43.idretts_app.application.helper_classes.PrefferencesClass;
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.example.gruppe43.idretts_app.application.view.fragments.FullActivityInfo;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class DataBaseHelperA extends Authentication {

    protected ProgressDialog progressDialog;
    private String postOwnerUserFirstName;
    private String postOwnerUserLastName;
    private static String[] activityDataCache;
    private DatabaseReference trainer_posts;
    private String firstANdLastNameOfPlayerPostOwner;
    private String nFbAct, nGymAct, nMeetAct, nCmpAct;
    private boolean hasRun;
    private boolean hasBeenCalledOnce;
    private boolean defaultAbsentValuesIsSet;
    protected boolean decrementedPlayerAbsent;

    public DataBaseHelperA(MainActivity mainActivity) {
        super(mainActivity);
        hasRun = false;
        hasBeenCalledOnce = false;
        defaultAbsentValuesIsSet = false;
        decrementedPlayerAbsent = false;
    }

    public String[] getActivityDataCache() {
        return activityDataCache;
    }

    //if on registration first time admin is confirmed!
    public void setIsAdmin() {
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
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
        final DatabaseReference fbTrainerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
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

            //TODO add to trainer activity posted counter
            progressDialog.dismiss();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.toastPostRegSuccess), Toast.LENGTH_SHORT).show();
            mainActivity.showFragmentOfGivenCondition();
            mainActivity.clearBackStack();
            mainActivity.hideKeyboard();

            String activityType = "";
            if (title.equals("Football training") || title.equals("Fotballtrening")) {
                activityType = "footballT";
            } else if (title.equals("Gym/Strength") || title.equals("Gym/Styrke")) {
                activityType = "gymT";
            } else if (title.equals("Theory/meeting") || title.equals("Teori/møte")) {
                activityType = "Meet";
            } else if (title.equals("Football camp") || title.equals("Fotballkamp")) {
                activityType = "camp";
            }
            if (hasRun) {
                hasRun = false;
            }
            setTrainerNactivityRecord(activityType, "add");
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

    //set trainer activity record
    public void setTrainerNactivityRecord(final String activityType, final String addOrRemove) {
        final String trainerKey = fbAuth.getCurrentUser().getUid();
        DatabaseReference fbUserDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUserDbRef.child(trainerKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!hasRun) {
                        hasRun = true;
                        String nFbAct = (String) dataSnapshot.child("nFbAct").getValue();
                        String nGymAct = (String) dataSnapshot.child("nGymAct").getValue();
                        String nMeetAct = (String) dataSnapshot.child("nMeetAct").getValue();
                        String nCmpAct = (String) dataSnapshot.child("nCmpAct").getValue();

                        String[] nActivityT = {nFbAct, nGymAct, nMeetAct, nCmpAct};
                        int[] intVals = parseString(nActivityT);


                        if (addOrRemove.equals("add")) {
                            if (activityType.equals("footballT")) {
                                intVals[0] = intVals[0] + 1;
                            } else if (activityType.equals("gymT")) {
                                intVals[1] = intVals[1] + 1;
                            } else if (activityType.equals("Meet")) {
                                intVals[2] = intVals[2] + 1;
                            } else if (activityType.equals("camp")) {
                                intVals[3] = intVals[3] + 1;
                            }
                        } else if (addOrRemove.equals("delete")) {
                            if (activityType.equals("footballT")) {
                                intVals[0] = intVals[0] - 1;
                            } else if (activityType.equals("gymT")) {
                                intVals[1] = intVals[1] - 1;
                            } else if (activityType.equals("Meet")) {
                                intVals[2] = intVals[2] - 1;
                            } else if (activityType.equals("camp")) {
                                intVals[3] = intVals[3] - 1;
                            }
                        }
                        try {
                            if (intVals[0] < 0) {
                                intVals[0] = 0;
                            }
                            if (intVals[1] < 0) {
                                intVals[1] = 0;
                            }
                            if (intVals[2] < 0) {
                                intVals[2] = 0;
                            }
                            if (intVals[3] < 0) {
                                intVals[3] = 0;
                            }
                            DatabaseReference nTrainerUpdateRef = FirebaseDatabase.getInstance().getReference().child("Users");
                            nTrainerUpdateRef.child(trainerKey).child("nFbAct").setValue(intVals[0] + "");
                            nTrainerUpdateRef.child(trainerKey).child("nGymAct").setValue(intVals[1] + "");
                            nTrainerUpdateRef.child(trainerKey).child("nMeetAct").setValue(intVals[2] + "");
                            nTrainerUpdateRef.child(trainerKey).child("nCmpAct").setValue(intVals[3] + "");

                            if (addOrRemove.equals("add")) {
                                setPlayerAbsenceRecord(activityType, "add");
                            } else if (addOrRemove.equals("delete")) {
                                setPlayerAbsenceRecord(activityType, "delete");
                            }
                        } catch (DatabaseException dbe) {
                            Log.d("//////////", "trainer incr activity record dbe");
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //all players are assumed to have attended on ALL! activities tha are posted by the trainer uless trainer sets some as absents.
    public void setPlayerAbsenceRecord(final String activityType, final String addOrRemove) {
        // final ArrayList<String> playerIds = new ArrayList<>();
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUsersDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!defaultAbsentValuesIsSet) {
                        defaultAbsentValuesIsSet = true;
                        Iterable<DataSnapshot> playerChildren = dataSnapshot.getChildren();
                        DatabaseReference setUpdateAbsents = FirebaseDatabase.getInstance().getReference().child("Users");////////////////////////////////////////////////////////////////// Iterate get or set
                        for (DataSnapshot players : playerChildren) {
                            UsersModel um = players.getValue(UsersModel.class);
                            String userKey = players.getKey();

                            String absFb = um.getAbsFb();
                            String absGym = um.getAbsGym();
                            String absMeet = um.getAbsMeet();
                            String absCmp = um.getAbsCmp();

                            String[] absValues = {absFb, absGym, absMeet, absCmp};
                            int[] intVals = parseString(absValues);

                            if (addOrRemove.equals("add")) {
                                if (activityType.equals("footballT")) {
                                    intVals[0] = intVals[0] + 1;
                                } else if (activityType.equals("gymT")) {
                                    intVals[1] = intVals[1] + 1;
                                } else if (activityType.equals("Meet")) {
                                    intVals[2] = intVals[2] + 1;
                                } else if (activityType.equals("camp")) {
                                    intVals[3] = intVals[3] + 1;
                                }
                            } else if (addOrRemove.equals("delete")) {
                                if (activityType.equals("footballT")) {
                                    intVals[0] = intVals[0] - 1;
                                } else if (activityType.equals("gymT")) {
                                    intVals[1] = intVals[1] - 1;
                                } else if (activityType.equals("Meet")) {
                                    intVals[2] = intVals[2] - 1;
                                } else if (activityType.equals("camp")) {
                                    intVals[3] = intVals[3] - 1;
                                }
                            }
                            try {
                                if (intVals[0] < 0) {
                                    intVals[0] = 0;
                                }
                                if (intVals[1] < 0) {
                                    intVals[1] = 0;
                                }
                                if (intVals[2] < 0) {
                                    intVals[2] = 0;
                                }
                                if (intVals[3] < 0) {
                                    intVals[3] = 0;
                                }
                                setUpdateAbsents.child(userKey).child("absFb").setValue(intVals[0] + "");
                                setUpdateAbsents.child(userKey).child("absGym").setValue(intVals[1] + "");
                                setUpdateAbsents.child(userKey).child("absMeet").setValue(intVals[2] + "");
                                setUpdateAbsents.child(userKey).child("absCmp").setValue(intVals[3] + "");
                            } catch (DatabaseException dbe) {
                                Log.d("//////////", "trainer incr activity record dbe");
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //parse string to int
    public int[] parseString(String[] nActivityT) {
        int[] parsed = new int[nActivityT.length];
        try {
            for (int i = 0; i < nActivityT.length; i++) {
                int parsedIndexValue = Integer.parseInt(nActivityT[i]);
                if (parsedIndexValue < 0) {
                    parsed[i] = 0;
                } else {
                    parsed[i] = parsedIndexValue;
                }
            }
        } catch (Exception pe) {
            Log.d("//////////", "Integer parse exception parseString()");
        }
        return parsed;
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

            final DatabaseReference fbPlayerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
            DatabaseReference player_posts = fbPlayerPostsDbRef.push();

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

            setPlayerNactivityRecord("add");

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

    //set player activity record
    public void setPlayerNactivityRecord(final String addOrRemove) {
        final String trainerKey = fbAuth.getCurrentUser().getUid();
        DatabaseReference fbUserDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUserDbRef.child(trainerKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!hasRun) {
                        hasRun = true;
                        String nPersonalTraining = (String) dataSnapshot.child("nPersonalTraining").getValue();
                        String[] nActivityT = {nPersonalTraining};
                        int[] intVals = parseString(nActivityT);
                        if (addOrRemove.equals("add")) {
                            intVals[0] = intVals[0] + 1;
                        } else if (addOrRemove.equals("delete")) {
                            intVals[0] = intVals[0] - 1;
                        }
                        try {
                            DatabaseReference nPlayerUpdateRef = FirebaseDatabase.getInstance().getReference().child("Users");
                            nPlayerUpdateRef.child(trainerKey).child("nPersonalTraining").setValue(intVals[0] + "");
                        } catch (DatabaseException dbe) {
                            Log.d("//////////", "player incr activity record dbe");
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //retrieve dataes of the selected activity for displaying
    public void getSelectedActivityInfo(String postKey) {
        activityDataCache = new String[12];
        final DatabaseReference fbTrainerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
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
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUsersDbRef.child(postOwnersId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!hasBeenCalledOnce) {
                        hasBeenCalledOnce = true;
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // showDatabaseDataFetchConnectionError();
            }
        });
    }

    //delete the selected plost if the user chooses to do so.
    public void deleteSelectedPost(String postKey, boolean isTrainerPost, String activityType) {
        DatabaseReference dbPostRef;
        try {
            if (isTrainerPost) {
                dbPostRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
            } else {
                dbPostRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
            }
            if (isTrainerPost) {
                setTrainerNactivityRecord(activityType, "delete");
                dbPostRef.child(postKey).removeValue();
                Toast.makeText(mainActivity, R.string.post_deleted_deletepost, Toast.LENGTH_SHORT).show();
            } else {
                dbPostRef.child(postKey).removeValue();
                setPlayerNactivityRecord("delete");
                Toast.makeText(mainActivity, R.string.post_deleted_deletepost, Toast.LENGTH_SHORT).show();
            }

        } catch (DatabaseException dbe) {
            Log.d("//////////", "deletepost dbe");
        }
    }

    //first check if player owns the post and send delete request if OK
    public void checkDoesPlayerOwnThePost(final String postKey) {
        final DatabaseReference fbPlayerPostsDbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        fbPlayerPostsDbRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String postOwnerId = (String) dataSnapshot.child("PlayerId").getValue();
                    final String currentUserId = fbAuth.getCurrentUser().getUid();
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
                                deleteSelectedPost(postKey, false, "");
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

    //save logged in player name locally for use in the app instead of requesting it everytieme.
    public void saveSignedInUserName() {
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        String currentUserId = fbAuth.getCurrentUser().getUid();
        fbUsersDbRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firstName = (String) dataSnapshot.child("firstName").getValue();
                    String lastName = (String) dataSnapshot.child("lastName").getValue();
                    PrefferencesClass pc = new PrefferencesClass(mainActivity);
                    pc.saveLoggedInUserNameLocally(firstName, lastName);
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
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUsersDbRef.addValueEventListener(new ValueEventListener() {////////////////////////////////////////////////////////////////////////////////////////////////
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> playerChildren = dataSnapshot.getChildren();

                    for (DataSnapshot players : playerChildren) {
                        UsersModel um = players.getValue(UsersModel.class);
                        String fn, ln, userId;
                        fn = um.getFirstName();
                        ln = um.getLastName();
                        userId = players.getKey();

                        firstNameLastNameArray.add(fn + " " + ln);
                        playerIds.add(userId);
                    }
                    EditCampRecordsDialog ecr = new EditCampRecordsDialog();
                    ecr.setPlayerNames(firstNameLastNameArray);
                    ecr.setPlayerIds(playerIds);
                    ecr.show(mainActivity.getmFragmentManager(), "ecd");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
