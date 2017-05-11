package com.example.gruppe43.idretts_app.application.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelperA;
import com.example.gruppe43.idretts_app.application.controll.DatabaseHelperC;
import com.example.gruppe43.idretts_app.application.helper_classes.PrefferencesClass;
import com.example.gruppe43.idretts_app.application.helper_classes.TeamAdapter;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.model.TeamModel;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


public class Team extends Fragment {

    private RecyclerView recyclerViewTeamRV;
    private TeamAdapter teamAdapter;



    private DatabaseReference fbDbRef;
    private DatabaseReference fbDbUsersRef;
    private DataBaseHelperA databaseHelper;
    private FragmentActivityInterface mCallback;
    private PrefferencesClass prefs;
    static boolean isForCheckAbsence;
    private ArrayList<String> absentPlayersIdsToBeSent;
    private ArrayList<View> correspondingAbsentPlayerView;
    private RelativeLayout adtoAbsentHolderRLayout;
    private Button button2AddToAbsentLists;
    private static ArrayList<String> listOfPlayersAlreadMarkedAsAbsent;
    private static ArrayList<String> listOfTobeRemovedFromAbsent;

    private static ArrayList<String> allUserImages;
    private static ArrayList<String> allUserFirstNameAndLastNames;

    public static ArrayList<String> getAllUserImages() {
        return allUserImages;
    }

    public static void setAllUserImages(ArrayList<String> allUserImages) {
        Team.allUserImages = allUserImages;
    }

    public static ArrayList<String> getAllUserFirstNameAndLastNames() {
        return allUserFirstNameAndLastNames;
    }

    public static void setAllUserFirstNameAndLastNames(ArrayList<String> allUserFirstNameAndLastNames) {
        Team.allUserFirstNameAndLastNames = allUserFirstNameAndLastNames;
    }

    public static void setListOfPlayersAlreadMarkedAsAbsent(ArrayList<String> listOfPlayersAlreadMarkedAsAbsent) {
        Team.listOfPlayersAlreadMarkedAsAbsent = listOfPlayersAlreadMarkedAsAbsent;
    }

    public static ArrayList<String> getListOfTobeRemovedFromAbsent() {
        return listOfTobeRemovedFromAbsent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentActivityInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_team,container,false);

        recyclerViewTeamRV = (RecyclerView) view.findViewById(R.id.recyclerViewTeam);
        teamAdapter = new TeamAdapter(getActivity(),getData());
        recyclerViewTeamRV.setAdapter(teamAdapter);
        recyclerViewTeamRV.setLayoutManager(new GridLayoutManager(getActivity(),4));


        button2AddToAbsentLists = (Button) view.findViewById(R.id.button2AddToAbsentLists);

        button2AddToAbsentLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelperC dbhc = new DatabaseHelperC(mCallback.getContext());
                dbhc.initiateDataInRecyclerViewForTeam();
            }
        });


        return view;
    }
    public List<TeamModel> getData(){
        List<TeamModel> data = new ArrayList<>();

        for (int i = 0; i <allUserFirstNameAndLastNames.size(); i++) {
            TeamModel current = new TeamModel();
            current.iconId = R.drawable.pph_m;
            current.tittle = allUserFirstNameAndLastNames.get(i);
            data.add(current);
        }
        return data;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    ////////////////////////////////////////////////////////////////////////////////////

}
