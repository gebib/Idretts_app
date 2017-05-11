package com.example.gruppe43.idretts_app.application.controll;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.helper_classes.TeamAdapter;
import com.example.gruppe43.idretts_app.application.model.AbcenceControllModel;
import com.example.gruppe43.idretts_app.application.model.TeamModel;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

/**
 * Created by gebi9 on 08-May-17.
 */

public class DatabaseHelperC extends DataBaseHelperB {
    private boolean absenceIteratedOnce;
    private boolean removedAbsentedNodes;
    private boolean resetAbsensToOneSet;
    private boolean teamDataPulled;

    public DatabaseHelperC(MainActivity mainActivity) {
        super(mainActivity);
        absenceIteratedOnce = false;
        removedAbsentedNodes = false;
        resetAbsensToOneSet = false;
        teamDataPulled = false;
    }

    //delete from absent table if ACTIVITY deleted
    public void deleteAbsentEntry(String activityKey) { //from delete activity
        //TODO
    }

    //delete from absent table if USER no more absent
    public void deleteAbsentEtryIfUserNoMoreAbsent(String activityId) {
        /*final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("AbsenceRecords");
        final ArrayList<String> listOfNodeKEYonAbsenceRecordToBeDeleted = new ArrayList<>();
        fbUsersDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!removedAbsentedNodes) {
                        removedAbsentedNodes = true;
                        int itrIndex = 0;
                        Iterable<DataSnapshot> absentChildren = dataSnapshot.getChildren();
                        for (DataSnapshot absents : absentChildren) {
                            itrIndex ++;
                            AbcenceControllModel um = absents.getValue(AbcenceControllModel.class);
                            String absentNodeKey = absents.getKey();
                            String absentPlayerKey = um.getAbsentPlayersId();
                            if(absentPlayerKey.equals(listOfnoMoreAbsentPlayersID.get(itrIndex))){
                                listOfNodeKEYonAbsenceRecordToBeDeleted.add(absentNodeKey);//tobe deleted once for loop ends.
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    //retrieve to team list of all abcent CHecked players when Team initiates
    public void retrieveAllPlayersRegisteredAsAbsent() { //need to be done whenever team is visited.
        final ArrayList<String> idOfAbsentPlayers = new ArrayList<>();
        final DatabaseReference absentsDbRef = FirebaseDatabase.getInstance().getReference().child("AbsenceRecords");
        absentsDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (!absenceIteratedOnce) {
                        absenceIteratedOnce = true;
                        Iterable<DataSnapshot> absentChildren = dataSnapshot.getChildren();
                        for (DataSnapshot absents : absentChildren) {
                            AbcenceControllModel um = absents.getValue(AbcenceControllModel.class);
                            //String absentNodeKey = absents.getKey();
                            idOfAbsentPlayers.add(um.getAbsentPlayersId());
                        }

                        Team.setListOfPlayersAlreadMarkedAsAbsent(idOfAbsentPlayers);

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("absenceCheck", true);
                        Team teamFrag = new Team();
                        teamFrag.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = mainActivity.getmFragmentManager().beginTransaction();
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.replace(R.id.containerView, teamFrag).commit();
                    }
                } else {
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //set remove (add participation) and update absent for list of particular players.
    public void removeAbsentFromPlayerId(final ArrayList<String> listOfnoMoreAbsentPlayersID) { // if trainer choose to edit absent in team
        final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        fbUsersDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && listOfnoMoreAbsentPlayersID != null) { //////TODO CHCHCCHCHCCHCCCCHCHCHEEEEEEECKCKCKCK work??????? !=null
                    if (!resetAbsensToOneSet) {
                        resetAbsensToOneSet = true;
                        Iterable<DataSnapshot> playerChildren = dataSnapshot.getChildren();
                        DatabaseReference setUpdateAbsents = FirebaseDatabase.getInstance().getReference().child("Users");

                        int setList = 0;
                        for (DataSnapshot players : playerChildren) {
                            UsersModel um = players.getValue(UsersModel.class);

                            String userKey = players.getKey();// the key fo the current player node

                            if (userKey.equals(listOfnoMoreAbsentPlayersID.get(setList))) {
                                String absFb = um.getAbsFb();                  //TODO becouse now we have their actual id ok what to do..... after this.. call deleteAbsentEtryIfUserNoMoreAbsent then done. DEBUG
                                String absGym = um.getAbsGym();
                                String absMeet = um.getAbsMeet();
                                String absCmp = um.getAbsCmp();

                                String[] absValues = {absFb, absGym, absMeet, absCmp};
                                int[] intVals = parseString(absValues);

                                String activityType = FullActivityInfo.getActivityType();//////////////////////////////////////////////////////////////////////////////////////////////

                                if (activityType.equals("footballT")) {
                                    intVals[0] = intVals[0] + 1;
                                } else if (activityType.equals("gymT")) {
                                    intVals[1] = intVals[1] + 1;
                                } else if (activityType.equals("Meet")) {
                                    intVals[2] = intVals[2] + 1;
                                } else if (activityType.equals("camp")) {
                                    intVals[3] = intVals[3] + 1;
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
                                setList++;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //if user  actually not absent anyway
        deleteAbsentEtryIfUserNoMoreAbsent(TrainerActivityRegistration.getSelectedActivityPostKey());
    }

    //for team! team tab uses local RecyclerView do to som unstability when clicking!. we need that to be acurate all the time!
    public void initiateDataInRecyclerViewForTeam() {
         final DatabaseReference fbUsersDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        final ArrayList<String> userImageList = new ArrayList<>();
        final ArrayList<String> fullUserNamesList = new ArrayList<>();
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
                            String fullUserName = um.getFirstName() + " " + um.getLastName();
                            String userImage = um.getImage();
                            userImageList.add(userImage);
                            fullUserNamesList.add(fullUserName);
                            listOfUserIdsList.add(userId);
                        }
                        Team.setAllUserFirstNameAndLastNames(fullUserNamesList);
                        Team.setAllUserImages(userImageList);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
