package com.example.gruppe43.idretts_app.application.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gruppe43.idretts_app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAllPlayers extends Fragment {


    public ShowAllPlayers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_all_players, container, false);
    }

}
