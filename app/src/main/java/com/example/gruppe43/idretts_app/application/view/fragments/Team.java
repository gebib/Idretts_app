package com.example.gruppe43.idretts_app.application.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.TeamAdapter;
import com.example.gruppe43.idretts_app.application.model.TeamDummyData;

import java.util.ArrayList;
import java.util.List;


public class Team extends Fragment {

    private RecyclerView recyclerViewTeamRV;
    private TeamAdapter teamAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_team,container,false);

        recyclerViewTeamRV = (RecyclerView) layout.findViewById(R.id.recyclerViewTeam);
        teamAdapter = new TeamAdapter(getActivity(),getData());
        recyclerViewTeamRV.setAdapter(teamAdapter);
        recyclerViewTeamRV.setLayoutManager(new GridLayoutManager(getActivity(),4));
        return layout;
    }

    public static List<TeamDummyData> getData(){
        List<TeamDummyData> data = new ArrayList<>();

        int[] userImages = {R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,
                R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,
                R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,R.drawable.pph_m,};
        String[] tittles = {"PersonIfo","PersonIfo","PersonIfo","PersonIfo","PersonIfo","PersonIfo","PersonIfo",
                "PersonIfo","PersonIfo","PersonIfo","PersonIfo","PersonIfo","PersonIfo","PersonIfo","PersonIfo",};

        //create list of dummydata objects to display.
        for (int i = 0; i <tittles.length && i < userImages.length ; i++) {//so that if any of the arrays are shorter, that the app does not crash here..
            TeamDummyData current = new TeamDummyData();
            current.iconId = userImages[i];
            current.tittle = tittles[i];
            data.add(current);
        }
        return data;
    }
}
