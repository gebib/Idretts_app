package com.example.gruppe43.idretts_app.application.Authentication.DatabaseInterface;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.model.PlayerPostsModel;
import com.example.gruppe43.idretts_app.application.model.TrainerPostsModel;
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by gebi9 on 08-May-17.
 */

public class DataBaseHelperB extends DataBaseHelperA {
    private int nowDate, nowMonth, nowYear, nowHour, nowMinute;
    private boolean onStartPosArchiveChecked;
    private int nTrainerPosts;
    private int nPlayerPosts;
    private boolean checkAddEventRecallCampData;

    public void setOnStartPosArchiveChecked(boolean onStartPosArchiveChecked) {
        this.onStartPosArchiveChecked = onStartPosArchiveChecked;
    }

    public DataBaseHelperB(MainActivity mainActivity) {
        super(mainActivity);
        checkAddEventRecallCampData = false;
    }

    //show general loading
    public void showGeneralProgressDialog(Boolean state) {
        if (state) {
            progressDialog = new ProgressDialog(mainActivity);
            progressDialog.setTitle(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTitle));
            progressDialog.setMessage(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTextInfo));
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }

    }

    //show general db failure alert
    public void showGeneralDbExceptionAlert() {
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


    //register player data related to camp matches.asdfasdf
    public void registerPlayerCampDataRecords(final int numMinutPlayed, final int numRedCard, final int numYellowCard, final int numGreenCard, final int numAccidents, final int numPerfectPasses, final int numScores, final String playerId) {
        DatabaseReference fbUserDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUserDbRef.child(playerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!checkAddEventRecallCampData) {
                        checkAddEventRecallCampData = true;
                        String rCard = (String) dataSnapshot.child("rCard").getValue();
                        String yCard = (String) dataSnapshot.child("yCard").getValue();
                        String gCard = (String) dataSnapshot.child("gCard").getValue();
                        String nMinutesPlayed = (String) dataSnapshot.child("nMinutesPlayed").getValue();
                        String nAccidents = (String) dataSnapshot.child("nAccidents").getValue();
                        String nGoalGivingPasses = (String) dataSnapshot.child("nGoalGivingPasses").getValue();
                        String nScores = (String) dataSnapshot.child("nScores").getValue();

                        String[] nActivityT = {rCard, yCard, gCard, nMinutesPlayed, nAccidents, nGoalGivingPasses, nScores};
                        int[] intVals = parseString(nActivityT);

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
                        if (intVals[4] < 0) {
                            intVals[4] = 0;
                        }
                        if (intVals[5] < 0) {
                            intVals[5] = 0;
                        }
                        if (intVals[6] < 0) {
                            intVals[6] = 0;
                        }

                        intVals[0] = intVals[0] + numRedCard;
                        intVals[1] = intVals[1] + numYellowCard;
                        intVals[2] = intVals[2] + numGreenCard;
                        intVals[3] = intVals[3] + numMinutPlayed;
                        intVals[4] = intVals[4] + numAccidents;
                        intVals[5] = intVals[5] + numPerfectPasses;
                        intVals[6] = intVals[6] + numScores;
                        try {
                            DatabaseReference nPlayerCampRecords = FirebaseDatabase.getInstance().getReference().child("Users");
                            nPlayerCampRecords.child(playerId).child("rCard").setValue(intVals[0] + "");
                            nPlayerCampRecords.child(playerId).child("yCard").setValue(intVals[1] + "");
                            nPlayerCampRecords.child(playerId).child("gCard").setValue(intVals[2] + "");
                            nPlayerCampRecords.child(playerId).child("nMinutesPlayed").setValue(intVals[3] + "");
                            nPlayerCampRecords.child(playerId).child("nAccidents").setValue(intVals[4] + "");
                            nPlayerCampRecords.child(playerId).child("nGoalGivingPasses").setValue(intVals[5] + "");
                            nPlayerCampRecords.child(playerId).child("nScores").setValue(intVals[6] + "");
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


    //get the current date as int
    public void getCurrentDateInt() {

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        nowDate = today.monthDay;
        nowMonth = today.month + 1;
        nowYear = today.year;
        nowHour = today.hour;
        nowMinute = today.minute;
    }

    //parse string date to int values
    public int[] parsePosteDate(String datePosted, String timePosted) {
        int[] parsedDate = new int[5];
        int t = 0;
        int arIndex = 0;
        String toBeParsed = datePosted + "." + timePosted;

        while (t < toBeParsed.length()) {
            String temp = "";
            char c = toBeParsed.charAt(t);
            while (t < toBeParsed.length() && c != '.' && c != ':') {
                temp += String.valueOf(c);
                t++;
                if (toBeParsed.length() == t) {
                    break;
                } else {
                    c = toBeParsed.charAt(t);
                }
            }
            try {
                parsedDate[arIndex] = Integer.parseInt(temp);
            } catch (Exception e) {
                Log.d("/////////////parse", "Parse erro");
            }
            t++;
            arIndex++;
        }
        return parsedDate;
    }

    //delete Trainer posts that has the date later than today
    public void checkOutdatedTrainerPosts() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("TrainerPosts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> trainerPostNodes = dataSnapshot.getChildren();
                    getCurrentDateInt();
                    for (DataSnapshot trainerPosts : trainerPostNodes) {
                        TrainerPostsModel tpm = trainerPosts.getValue(TrainerPostsModel.class);
                        int[] postDateAndTime = parsePosteDate(tpm.getActivityDate(), tpm.getTimePosted());
                        String title = tpm.getTitle();
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

                        if (nowYear > postDateAndTime[2]) {
                            deleteSelectedPost(trainerPosts.getKey(), true, activityType);
                        } else if (nowMonth > postDateAndTime[1]) {

                            deleteSelectedPost(trainerPosts.getKey(), true, activityType);
                        } else if (nowDate > postDateAndTime[0]) {

                            deleteSelectedPost(trainerPosts.getKey(), true, activityType);
                        } else if (nowHour > postDateAndTime[3]) {

                            deleteSelectedPost(trainerPosts.getKey(), true, activityType);
                        } else if (nowMinute > postDateAndTime[4]) {

                            deleteSelectedPost(trainerPosts.getKey(), true, activityType);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //delete Player posts that has the date later than today
    public void checkOutdatedPlayerPosts() {  //TODO if activity deleted AUTO no absence update!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("PlayerPosts");
        final ArrayList<DataSnapshot> listOfnodesToBeDeleted = new ArrayList<>();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> playerPostNodes = dataSnapshot.getChildren();
                    getCurrentDateInt();
                    for (DataSnapshot playerPosts : playerPostNodes) {
                        PlayerPostsModel ppm = playerPosts.getValue(PlayerPostsModel.class);
                        int[] postDateAndTime = parsePosteDate(ppm.getDatePosted(), ppm.getTimePosted());
                        if (nowYear > postDateAndTime[2]) {
                            listOfnodesToBeDeleted.add(playerPosts);
                            deleteSelectedPost(playerPosts.getKey(), false, "");
                        } else if (nowMonth > postDateAndTime[1]) {
                            listOfnodesToBeDeleted.add(playerPosts);
                            deleteSelectedPost(playerPosts.getKey(), false, "");
                        } else if (nowDate > postDateAndTime[0]) {
                            listOfnodesToBeDeleted.add(playerPosts);
                            deleteSelectedPost(playerPosts.getKey(), false, "");
                        } else if (nowHour > postDateAndTime[3]) {
                            listOfnodesToBeDeleted.add(playerPosts);
                            deleteSelectedPost(playerPosts.getKey(), false, "");
                        } else if (nowMinute > postDateAndTime[4]) {
                            listOfnodesToBeDeleted.add(playerPosts);
                            deleteSelectedPost(playerPosts.getKey(), false, "");
                        }
                    }
                    //archivePlayerPostBeforeDelete(listOfnodesToBeDeleted);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //set player absence from a PARTICULAR! activity type, from team absence check on activity
    public void setAbsenceForArrayOfAbsentPlayerIds(final ArrayList<String> absentPlayersIds, final String activityType, final String activityId) {
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUsersDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!decrementedPlayerAbsent && !absentPlayersIds.isEmpty()) {

                        Iterable<DataSnapshot> playerChildren = dataSnapshot.getChildren();
                        DatabaseReference setAbsents = FirebaseDatabase.getInstance().getReference().child("Users");
                        for (DataSnapshot players : playerChildren) {
                                UsersModel um = players.getValue(UsersModel.class);
                                String currentPlayerNodeKey = players.getKey();


                                if (absentPlayersIds.contains(currentPlayerNodeKey)) {
                                    String absFb = um.getAbsFb();
                                    String absGym = um.getAbsGym();
                                    String absMeet = um.getAbsMeet();
                                    String absCmp = um.getAbsCmp();

                                    String[] absValues = {absFb, absGym, absMeet, absCmp};
                                    int[] intVals = parseString(absValues);


                                    if (activityType.equals("footballT")) {
                                        intVals[0] = intVals[0] - 1;
                                    } else if (activityType.equals("gymT")) {
                                        intVals[1] = intVals[1] - 1;
                                    } else if (activityType.equals("Meet")) {
                                        intVals[2] = intVals[2] - 1;
                                    } else if (activityType.equals("camp")) {
                                        intVals[3] = intVals[3] - 1;
                                    }


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

                                    try {
                                        setAbsents.child(currentPlayerNodeKey).child("absFb").setValue(intVals[0] + "");
                                        setAbsents.child(currentPlayerNodeKey).child("absGym").setValue(intVals[1] + "");
                                        setAbsents.child(currentPlayerNodeKey).child("absMeet").setValue(intVals[2] + "");
                                        setAbsents.child(currentPlayerNodeKey).child("absCmp").setValue(intVals[3] + "");
                                    } catch (DatabaseException dbe) {
                                        Log.d("///////////", "set absence from team dbe");
                                    }
                            }
                        }//end players iteration
                        if (!absentPlayersIds.isEmpty() && !decrementedPlayerAbsent) {
                            registerAbsenceTableInDb(absentPlayersIds, activityId);
                            decrementedPlayerAbsent = true;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*a user can have more tan ONE register/////////////////////*/
    //register absent players to activity id. FOR if TEAM ABS REVISIT SHOW ABSENT PURPoSE
    private void registerAbsenceTableInDb(ArrayList<String> absentPlayersIds, String activityId) {
        ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTitle));
        progressDialog.setMessage(mainActivity.getResources().getString(R.string.adminPostingProgressDialogTextInfo));
        progressDialog.show();
        DatabaseReference fbAbsenceDbRef = FirebaseDatabase.getInstance().getReference().child("AbsenceRecords");
        try {
            for (int i = 0; i < absentPlayersIds.size(); i++) {
                System.out.println("///////////////////////////////////////// inside registerAbsenceTableInDb for loop");
                DatabaseReference absent_records = fbAbsenceDbRef.push();
                absent_records.child("absentPlayersId").setValue(absentPlayersIds.get(i));
                absent_records.child("activityId").setValue(activityId);
            }
            Toast.makeText(mainActivity, R.string.absentplayersregistered, Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } catch (DatabaseException dbe) {
            progressDialog.dismiss();
            showGeneralDbExceptionAlert();
        }
    }
}
