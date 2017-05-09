package com.example.gruppe43.idretts_app.application.helper_classes;

import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelperA;
import com.example.gruppe43.idretts_app.application.controll.DataBaseHelperB;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;

import java.util.ArrayList;

/**
 * Created by gebi9 on 06-May-17.
 */

public class EditCampRecordsDialog extends DialogFragment {
    private FragmentActivityInterface mCallback;
    private Spinner spinnerCampRecordsSelectPlayer;
    private TextView minutesPlayed;
    private TextView redCards;
    private TextView yellowCards;
    private TextView greenCards;
    private TextView perfectPasses;
    private TextView scores;
    private TextView accidents;
    private Button campRecordDialogAddBtn;
    private String playerId;
    private int numMinutPlayed;
    private int numRedCard;
    private int numYellowCard;
    private int numGreenCard;
    private int numPerfectPasses;
    private int numScores;
    private int numAccidents;
    private ArrayList<String> playerNames;
    private ArrayList<String> playerIds;

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public void setPlayerIds(ArrayList<String> playerIds) {
        this.playerIds = playerIds;
    }

    public EditCampRecordsDialog() {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_camp_records, null);
        getDialog().setTitle(R.string.dialogCampRecords);
        spinnerCampRecordsSelectPlayer = (Spinner) view.findViewById(R.id.spinnerCampRecordsSelectPlayer);

        minutesPlayed = (EditText) view.findViewById(R.id.minutesPlayed);
        redCards = (EditText) view.findViewById(R.id.redCards);
        yellowCards = (EditText) view.findViewById(R.id.yellowCards);
        greenCards = (EditText) view.findViewById(R.id.greenCards);
        perfectPasses = (EditText) view.findViewById(R.id.perfectPasses);
        scores = (EditText) view.findViewById(R.id.scores);
        accidents = (EditText) view.findViewById(R.id.accidentTV);



        redCards.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    redCards.setHint("");
                }
            }
        });

        yellowCards.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    yellowCards.setHint("");
                }
            }
        });

        greenCards.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    greenCards.setHint("");
                }
            }
        });

        scores.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    scores.setHint("");
                }
            }
        });

        perfectPasses.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    perfectPasses.setHint("");
                }
            }
        });

        accidents.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    accidents.setHint("");
                }
            }
        });

        campRecordDialogAddBtn = (Button) view.findViewById(R.id.campRecordDialogAddBtn);
        campRecordDialogAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    numMinutPlayed = Integer.parseInt(minutesPlayed.getText().toString().trim());
                    numRedCard = Integer.parseInt(redCards.getText().toString().trim());
                    numYellowCard = Integer.parseInt(yellowCards.getText().toString().trim());
                    numGreenCard = Integer.parseInt(greenCards.getText().toString().trim());
                    numPerfectPasses = Integer.parseInt(perfectPasses.getText().toString().trim());
                    numScores = Integer.parseInt(scores.getText().toString().trim());
                    numAccidents = Integer.parseInt(accidents.getText().toString().trim());
                }catch (Exception e){

                }
                DataBaseHelperB dbh = new DataBaseHelperB(mCallback.getContext());
                dbh.registerPlayerCampDataRecords(numMinutPlayed,numRedCard,numYellowCard,numGreenCard,numAccidents,numPerfectPasses,numScores,playerId);
            }
        });

        DataBaseHelperA dbh = new DataBaseHelperA(mCallback.getContext());
        ArrayAdapter<String> playerNamesSpinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, playerNames);
        playerNamesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCampRecordsSelectPlayer.setAdapter(playerNamesSpinner);
        spinnerCampRecordsSelectPlayer.setSelection(0);

        spinnerCampRecordsSelectPlayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                playerId = playerIds.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }
}
