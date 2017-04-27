package com.example.gruppe43.idretts_app.application.view.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gruppe43.idretts_app.R;

public class Player extends Fragment {
    private RecyclerView recyclerViewPlayerRV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_player,container,false);

        recyclerViewPlayerRV = (RecyclerView) layout.findViewById(R.id.recyclerViewPlayer);


        recyclerViewPlayerRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }


}
