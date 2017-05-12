package com.example.gruppe43.idretts_app.application.controll;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.model.AbcenceControllModel;
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.example.gruppe43.idretts_app.application.view.fragments.FullActivityInfo;
import com.example.gruppe43.idretts_app.application.view.fragments.ProfileView;
import com.example.gruppe43.idretts_app.application.view.fragments.Team;
import com.example.gruppe43.idretts_app.application.view.fragments.TrainerActivityRegistration;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

/**
 * Created by gebi9 on 08-May-17.
 */

public class DatabaseHelperC extends DataBaseHelperB {
    private boolean absenceIteratedOnce;
    private boolean removedAbsentedNodes;
    private boolean teamDataPulled;
    private boolean incrementPlayerAbsents;
    private boolean removeActivityFromAbsenceRecord;
    private boolean getProfileDateRun;
    private boolean getAditionalActivityInfoFromTrainer;
    private boolean getTrainerKey;

    public DatabaseHelperC(MainActivity mainActivity) {
        super(mainActivity);
        absenceIteratedOnce = false;
        removedAbsentedNodes = false;
        teamDataPulled = false;
        incrementPlayerAbsents = false;
        removeActivityFromAbsenceRecord = false;
        getProfileDateRun = false;
        getAditionalActivityInfoFromTrainer = false;
        getTrainerKey = false;
    }


    //retrieve to team list of all abcent CHecked players when Team initiates
    public void retrieveAllPlayersRegisteredAsAbsent() { //need to be done whenever team is visited.
        final ArrayList<String> idOfAbsentPlayers = new ArrayList<>();
        final DatabaseReference absentsDbRef = FirebaseDatabase.getInstance().getReference().child("AbsenceRecords");
        absentsDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!absenceIteratedOnce) {
                    absenceIteratedOnce = true;
                    if (dataSnapshot.exists()) {
                        Iterable<DataSnapshot> absentChildren = dataSnapshot.getChildren();
                        for (DataSnapshot absents : absentChildren) {
                            AbcenceControllModel um = absents.getValue(AbcenceControllModel.class);
                            //String absentNodeKey = absents.getKey();
                            if (um.getActivityId().equals(TrainerActivityRegistration.getSelectedActivityPostKey())) {
                                idOfAbsentPlayers.add(um.getAbsentPlayersId());
                            }
                        }

                        Team.setListOfPlayersAlreadMarkedAsAbsent(idOfAbsentPlayers);

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("absenceCheck", true);
                        Team teamFrag = new Team();
                        teamFrag.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = mainActivity.getmFragmentManager().beginTransaction();
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.replace(R.id.containerView, teamFrag).commit();
                    } else if (!dataSnapshot.exists()) { // no absents registered ata ll.
                        Team.setListOfPlayersAlreadMarkedAsAbsent(new ArrayList<String>());
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("absenceCheck", true);
                        Team teamFrag = new Team();
                        teamFrag.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = mainActivity.getmFragmentManager().beginTransaction();
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.replace(R.id.containerView, teamFrag).commit();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //for team! team tab uses local RecyclerView do to som unstability when clicking!. we need that to be acurate all the time!
    public void initiateDataInRecyclerViewForTeam() {
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        final ArrayList<String> listOfUserIdsList = new ArrayList<>();
        fbUsersDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!teamDataPulled) {
                        teamDataPulled = true;
                        Iterable<DataSnapshot> absentChildren = dataSnapshot.getChildren();
                        for (DataSnapshot absents : absentChildren) {
                            UsersModel um = absents.getValue(UsersModel.class);
                            String userId = absents.getKey();
                            listOfUserIdsList.add(userId);
                        }
                        Team.setListOfAllUsersId(listOfUserIdsList);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //when a user is clicked in team, get the data to be displayed about this user.
    public void getProfileViewDataForUser(String userId) {
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUsersDbRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!getProfileDateRun) {


                        ArrayList<String> profileData = new ArrayList<>();


                        String firstName = (String) dataSnapshot.child("firstName").getValue();
                        String lastName = (String) dataSnapshot.child("lastName").getValue();
                        String playerNr = (String) dataSnapshot.child("playerNr").getValue();
                        String playerAge = (String) dataSnapshot.child("playerAge").getValue();
                        String status = (String) dataSnapshot.child("status").getValue();
                        String registeredDate = (String) dataSnapshot.child("registeredDate").getValue();
                        String playerType = (String) dataSnapshot.child("playerType").getValue();
                        String nMinutesPlayed = (String) dataSnapshot.child("nMinutesPlayed").getValue();
                        String rCard = (String) dataSnapshot.child("rCard").getValue();
                        String yCard = (String) dataSnapshot.child("yCard").getValue();
                        String gCard = (String) dataSnapshot.child("gCard").getValue();
                        String nGoalGivingPasses = (String) dataSnapshot.child("nGoalGivingPasses").getValue();

                        String nPersonalTraining = (String) dataSnapshot.child("nPersonalTraining").getValue();

                        String absFb = (String) dataSnapshot.child("absFb").getValue();
                        String absGym = (String) dataSnapshot.child("absGym").getValue();
                        String absMeet = (String) dataSnapshot.child("absMeet").getValue();
                        String absCmp = (String) dataSnapshot.child("absCmp").getValue();

                        String nAccidents = (String) dataSnapshot.child("nAccidents").getValue();


                        profileData.add(firstName + " "+lastName);//0
                        profileData.add(playerNr);
                        profileData.add(playerAge);
                        profileData.add(status);
                        profileData.add(registeredDate);
                        profileData.add(playerType);
                        profileData.add(nMinutesPlayed);
                        profileData.add(rCard);
                        profileData.add(gCard);
                        profileData.add(yCard);
                        profileData.add(nGoalGivingPasses);
                        profileData.add(nPersonalTraining);

                        String[] absesAndNacc = {absFb,absGym,absMeet,absCmp,nAccidents};


                        getTrainerKey(profileData,absesAndNacc);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // showDatabaseDataFetchConnectionError();
            }
        });

    }

    //search through nodes and get trainer key
    private void getTrainerKey(final ArrayList<String> profileData, final String[] absesAndNacc) {
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUsersDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!getTrainerKey) { /////////////////////////////////////////////////////////////////////////////////////////////////TODO should work!!
                        getTrainerKey = true;
                        Iterable<DataSnapshot> usersNodes = dataSnapshot.getChildren();
                        for (DataSnapshot user : usersNodes) {
                            UsersModel um = user.getValue(UsersModel.class);
                            String userId = user.getKey();
                            if(um.getIsAdmin().equals("true")){
                                getAdditionalProfileDataForSelectedUser(profileData,userId,absesAndNacc);
                                break;
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

    //add additional info needed from trainer to the profile view data before viewing profile.
    public void getAdditionalProfileDataForSelectedUser(final ArrayList<String> profileData, String trainerKey, final String[] absesAndNacc){
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUsersDbRef.child(trainerKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!getAditionalActivityInfoFromTrainer) {

                        String nFbAct = (String) dataSnapshot.child("nFbAct").getValue();
                        String nGymAct = (String) dataSnapshot.child("nGymAct").getValue();
                        String nMeetAct = (String) dataSnapshot.child("nMeetAct").getValue();
                        String nCmpAct = (String) dataSnapshot.child("nCmpAct").getValue();

                        profileData.add(absesAndNacc[0] +"/"+nFbAct);
                        profileData.add(absesAndNacc[1]+"/"+nGymAct);
                        profileData.add(absesAndNacc[2]+"/"+nMeetAct);
                        profileData.add(absesAndNacc[3]+"/"+nCmpAct);
                        profileData.add(absesAndNacc[4]);

                        String[] attendedACtivities = {absesAndNacc[0],absesAndNacc[1],absesAndNacc[2],absesAndNacc[3]};
                        String[] nTrainerActivities = {nFbAct,nGymAct,nMeetAct,nCmpAct};

                        int[] intValuesNactivityAttended = parseString(attendedACtivities);
                        int[] intValuesNtotalActivities = parseString(nTrainerActivities);

                        int totalActivities = 0;
                        int totalAttendance = 0;

                        for (int i = 0; i < intValuesNtotalActivities.length; i++) {
                            totalActivities += intValuesNtotalActivities[i];
                            totalAttendance += intValuesNactivityAttended[i];
                        }
                        profileData.add(totalAttendance+"/"+totalActivities);

                        ProfileView pv = new ProfileView();
                        pv.setSelectedUserIfoData(profileData);

                        FragmentTransaction fragmentTransaction = mainActivity.getmFragmentManager().beginTransaction();
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.replace(R.id.containerView, new ProfileView()).commit();

                        getAditionalActivityInfoFromTrainer = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // showDatabaseDataFetchConnectionError();
            }
        });

    }
    //update profile of player.
    public void updateProfileEdit(String statuss, String playerNr, String playerType) {
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        String currentUserId = fbAuth.getCurrentUser().getUid();

        DatabaseReference editProfile = FirebaseDatabase.getInstance().getReference().child("Users");
        try {
            if(!statuss.equals("")){
                editProfile.child(currentUserId).child("status").setValue(statuss);
            }
            if(!playerNr.equals("")){
                editProfile.child(currentUserId).child("playerNr").setValue(playerNr);
            }
            if(!playerType.equals("")){
                editProfile.child(currentUserId).child("playerType").setValue(playerType);
            }
            Toast.makeText(mainActivity, R.string.statusUpdated, Toast.LENGTH_SHORT).show();
        } catch (DatabaseException dbe) {
            Log.d("///////////", "set absence from team dbe");
        }
    }
}
