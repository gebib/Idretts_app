package com.example.gruppe43.idretts_app.application.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;

import java.util.ArrayList;

public class ProfileView extends Fragment {
    private FragmentActivityInterface mCallback;

    private ImageView profImageIV;//profile image

    private TextView profileNameTv;
    private TextView textView3PlayerNr;
    private TextView textView11PlayerAge;
    private TextView textView8Status;

    private ImageView imageButtonEditProfile;//use as button

    private TextView dateRegisteredDate;
    private TextView playerTypeText;
    private TextView numberOfminutePlayedText;
    private TextView numberOfRedCardText;
    private TextView numberOfGreenCardText;
    private TextView numberOfYellowCardText;
    private TextView numberOfPerfectPassingsText;
    private TextView personalActivityActivitiesText;
    private TextView coachActivityAttendedText;
    private TextView numberOfAccidentsText;
    private ArrayList<String> selectedUserIfoData;//TODO set before call


    public void setSelectedUserIfoData(ArrayList<String> selectedUserIfoData) {
        this.selectedUserIfoData = selectedUserIfoData;
    }

    public ProfileView() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        mCallback.getFab().hide();


        profileNameTv = (TextView) view.findViewById(R.id.profileNameTv);
        profImageIV = (ImageView) view.findViewById(R.id.profImageIV);
        textView3PlayerNr = (TextView) view.findViewById(R.id.textView3PlayerNr);

        textView11PlayerAge = (TextView) view.findViewById(R.id.textView11PlayerAge);
        textView8Status = (TextView) view.findViewById(R.id.textView8Status);

        imageButtonEditProfile = (ImageView) view.findViewById(R.id.imageButtonEditProfile);

        dateRegisteredDate = (TextView) view.findViewById(R.id.dateRegisteredDate);
        playerTypeText = (TextView) view.findViewById(R.id.playerTypeText);
        numberOfminutePlayedText = (TextView) view.findViewById(R.id.numberOfminutePlayedText);
        numberOfRedCardText = (TextView) view.findViewById(R.id.numberOfRedCardText);
        numberOfGreenCardText = (TextView) view.findViewById(R.id.numberOfGreenCardText);
        numberOfYellowCardText = (TextView) view.findViewById(R.id.numberOfYellowCardText);
        numberOfPerfectPassingsText = (TextView) view.findViewById(R.id.numberOfPerfectPassingsText);
        personalActivityActivitiesText = (TextView) view.findViewById(R.id.personalActivityActivitiesText);
        coachActivityAttendedText = (TextView) view.findViewById(R.id.coachActivityAttendedText);
        numberOfAccidentsText = (TextView) view.findViewById(R.id.numberOfAccidentsText);


        profImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("////////////////// set image picasso");//TODO
            }
        });

        imageButtonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("////////////////// identify current user and get popup to fill inn the missings");//TODO
            }
        });

        //profImageIV TODO picasso
        if(selectedUserIfoData != null){
            profileNameTv.setText(selectedUserIfoData.get(0));
            textView3PlayerNr.setText(selectedUserIfoData.get(1));
            textView11PlayerAge.setText(selectedUserIfoData.get(2));
            textView8Status.setText(selectedUserIfoData.get(3));

            dateRegisteredDate.setText(selectedUserIfoData.get(4));
            playerTypeText.setText(selectedUserIfoData.get(5));
            numberOfminutePlayedText.setText(selectedUserIfoData.get(6));
            numberOfRedCardText.setText(selectedUserIfoData.get(7));
            numberOfGreenCardText.setText(selectedUserIfoData.get(8));
            numberOfYellowCardText.setText(selectedUserIfoData.get(9));
            numberOfPerfectPassingsText.setText(selectedUserIfoData.get(10));

            personalActivityActivitiesText.setText(selectedUserIfoData.get(11));
            coachActivityAttendedText.setText(selectedUserIfoData.get(12));
            numberOfAccidentsText.setText(selectedUserIfoData.get(13));
        }


        return view;
    }

}
