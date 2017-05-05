package com.example.gruppe43.idretts_app.application.view.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelper;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;

public class PlayerActivityRegistration extends Fragment {
    private TextView info;
    private CheckBox fotballTraining;
    private CheckBox gymnastics;
    private CheckBox localOutside;
    private CheckBox school;
    private TextView prosent;
    private SeekBar prosentSeeker;
    public static PlayerActivityRegistration par;
    private FragmentActivityInterface mCallback;
    private int unPursedIntensityValue;

    public PlayerActivityRegistration() {
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
        View view = inflater.inflate(R.layout.fragment_player_activity_registration, container, false);
        par = this;
        info = (TextView) view.findViewById(R.id.textView3Info);
        fotballTraining = (CheckBox) view.findViewById(R.id.checkBoxFBtraining);
        localOutside = (CheckBox) view.findViewById(R.id.checkBox3LocalOut);
        school = (CheckBox) view.findViewById(R.id.checkBox4School);
        prosent = (TextView) view.findViewById(R.id.textView9Percent);
        prosentSeeker = (SeekBar) view.findViewById(R.id.seekBarIntensity);
        gymnastics = (CheckBox) view.findViewById(R.id.checkBox2Gymnstcs);

        prosentSeeker.setMax(100);
        prosentSeeker.setProgress(0);
        prosent.setText(0 + "%");
        fotballTraining.setChecked(true);
        localOutside.setChecked(true);
        prosent.setTextColor(Color.rgb(111,255,0));


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mCallback.getContext());
                builder1.setTitle(R.string.informationLogPlayerActivityTextTitle);
                builder1.setMessage(R.string.informationLogPlayerActivityTextInfo);
                builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //ingen action.
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        //we use seOnClick intentionaly.
        fotballTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotballTraining.setChecked(true);
                gymnastics.setChecked(false);
            }
        });

        gymnastics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gymnastics.setChecked(true);
                fotballTraining.setChecked(false);
            }
        });

        localOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localOutside.setChecked(true);
                school.setChecked(false);
            }
        });

        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school.setChecked(true);
                localOutside.setChecked(false);
            }
        });

        prosentSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                unPursedIntensityValue = progress;
                prosent.setText(progress + "%");
                if (progress > 0 && progress < 30) {
                    prosent.setTextColor(Color.rgb(111,255,0));
                }
                if (progress > 30 && progress < 60) {
                    prosent.setTextColor(Color.rgb(214, 196, 0));
                }
                if (progress > 60 && progress < 100) {
                    prosent.setTextColor(Color.rgb(255,56,56));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return view;
    }

    //register player activity in database.
    public void registerPlayerActivity() {
        DataBaseHelper dbh = new DataBaseHelper(mCallback.getContext());

        String typeOfActivity = "";
        if (fotballTraining.isChecked() && !gymnastics.isChecked()) {
            typeOfActivity = getString(R.string.personalTrainingTypeFootball);
        } else if (!fotballTraining.isChecked() && gymnastics.isChecked()) {
            typeOfActivity = getString(R.string.personalTrainingTypeGymnastic);
        }

        String activityPlace = "";
        if (localOutside.isChecked() && !school.isChecked()) {
            activityPlace = getString(R.string.personalTrainingPlaceLocalOutside);
        } else if (!fotballTraining.isChecked() && gymnastics.isChecked()) {
            activityPlace = getString(R.string.personalTrainingPlaceSchool);
        }


        dbh.prePostPlayerActivity(typeOfActivity, activityPlace, unPursedIntensityValue);
    }

    @Override
    public void onStop() {
        super.onStop();
        mCallback.setIsOnNewActivityRegisterPage(false);
    }
}
