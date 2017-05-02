package com.example.gruppe43.idretts_app.application.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gruppe43.idretts_app.R;


public class FullActivityInfo extends Fragment {
    private Button activityEditButton;
    private Button activityDeleteButton;

    private ImageView fullactivityinfoImageView;

    private TextView fullactivityinfoTitleTV;
    private TextView fullactivityinfoDate;
    private TextView fullactivityinfoStartEndTime;
    private TextView fullactivityinfoPlaceTV;
    private TextView postOwnerNameAndPostedDate;
    private TextView activityTextInfoTV;
    private String[] postDataToDisplay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_activity_info, container, false);

        activityEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("///////////////////////EDIT button");
            }
        });

        activityDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("///////////////////////Delete button");
            }
        });
        activityEditButton = (Button) view.findViewById(R.id.showFullActivityInfoEditButton);
        activityDeleteButton = (Button) view.findViewById(R.id.showFullActivityInfoDeleteButton);

        fullactivityinfoImageView = (ImageView) view.findViewById(R.id.fullactivityinfoImageView);

        fullactivityinfoTitleTV = (TextView) view.findViewById(R.id.fullactivityinfoTitleTV);
        fullactivityinfoDate = (TextView) view.findViewById(R.id.fullactivityinfoDate);
        fullactivityinfoStartEndTime = (TextView) view.findViewById(R.id.fullactivityinfoStartEndTime);
        fullactivityinfoPlaceTV = (TextView) view.findViewById(R.id.fullactivityinfoPlaceTV);
        postOwnerNameAndPostedDate = (TextView) view.findViewById(R.id.postDatePostedByOwner);
        activityTextInfoTV = (TextView) view.findViewById(R.id.activityTextInfoTV);

        postDataToDisplay = getArguments().getStringArray("postData");

        fullactivityinfoTitleTV.setText(postDataToDisplay[0]);
        fullactivityinfoDate.setText(postDataToDisplay[1]);
        String activityStartEnd = getString(R.string.fullActivityShowStartS)+ postDataToDisplay[2]+getString(R.string.fullActivityShowEndS)+postDataToDisplay[3];
        fullactivityinfoStartEndTime.setText(activityStartEnd);
        fullactivityinfoPlaceTV.setText(postDataToDisplay[4]);
        String postDateAndOwner =  "by "+postDataToDisplay[10]+" "+postDataToDisplay[11]+" " +postDataToDisplay[8];
        postOwnerNameAndPostedDate.setText(postDateAndOwner);
        activityTextInfoTV.setText(postDataToDisplay[7]);





        return view;
    }
}
