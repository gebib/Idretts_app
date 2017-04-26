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
import com.example.gruppe43.idretts_app.application.model.TeamDummyData;

import java.util.ArrayList;
import java.util.List;


public class Team extends Fragment {
    private RecyclerView recyclerViewTeamRV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_team,container,false);

        recyclerViewTeamRV = (RecyclerView) layout.findViewById(R.id.recyclerViewTeam);


        recyclerViewTeamRV.setLayoutManager(new GridLayoutManager(getActivity(),4));
        return layout;
    }
}
