package com.example.gruppe43.idretts_app.application.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;

public class FirstPage extends Fragment implements View.OnClickListener {
    private Button coachButton;
    private Button playerButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);
        coachButton = (Button) view.findViewById(R.id.coachBT);
        playerButton = (Button) view.findViewById(R.id.playerBT);
        coachButton.setOnClickListener(this);
        playerButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(final View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.coachBT:
                /*is coach first time */
                Toast.makeText(getActivity(), "coachBT!", Toast.LENGTH_LONG).show();
                Log.e("/////////////////","TEEEEEESSSSSSTTTT");
                break;
            case R.id.playerBT:
                /*is player first time */
                Toast.makeText(getActivity(), "playerBT!", Toast.LENGTH_LONG).show();
                break;
            default:
        }
    }
}
