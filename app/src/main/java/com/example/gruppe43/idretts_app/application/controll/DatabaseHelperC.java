package com.example.gruppe43.idretts_app.application.controll;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.model.AbcenceControllModel;
import com.example.gruppe43.idretts_app.application.model.UsersModel;
import com.example.gruppe43.idretts_app.application.view.fragments.FullActivityInfo;
import com.example.gruppe43.idretts_app.application.view.fragments.Team;
import com.example.gruppe43.idretts_app.application.view.fragments.TrainerActivityRegistration;
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

public class DatabaseHelperC extends DataBaseHelperB {
    private boolean absenceIteratedOnce;
    private boolean removedAbsentedNodes;
    private boolean teamDataPulled;
    private boolean incrementPlayerAbsents;
    private boolean removeActivityFromAbsenceRecord;

    public DatabaseHelperC(MainActivity mainActivity) {
        super(mainActivity);
        absenceIteratedOnce = false;
        removedAbsentedNodes = false;
        teamDataPulled = false;
        incrementPlayerAbsents = false;
        removeActivityFromAbsenceRecord = false;
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
                        Team.setListOfAllUsersId(listOfUserIdsList);//TODO does not need to do this.. just cache offline if i got time. same with images too
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
