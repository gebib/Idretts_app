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
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.Authentication.DatabaseInterface.DataBaseHelperB;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;

import java.util.ArrayList;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir

public class EditMatchRecordsDialog extends DialogFragment {
    private FragmentActivityInterface mCallback;
    private Spinner spinnerMatchRecordsSelectPlayer;
    private TextView minutesPlayed;
    private TextView redCards;
    private TextView yellowCards;
    private TextView greenCards;
    private TextView perfectPasses;
    private TextView scores;
    private TextView accidents;
    private Button matchRecordDialogAddBtn;
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

    public EditMatchRecordsDialog() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            System.out.print("///////////////////////////////??");
            mCallback = (FragmentActivityInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_match_records, null);
        getDialog().setTitle(R.string.dialogMatchRecords);
        spinnerMatchRecordsSelectPlayer = (Spinner) view.findViewById(R.id.spinnerMatchRecordsSelectPlayer);

        minutesPlayed = (EditText) view.findViewById(R.id.myStatus);
        redCards = (EditText) view.findViewById(R.id.redCards);
        yellowCards = (EditText) view.findViewById(R.id.yellowCards);
        greenCards = (EditText) view.findViewById(R.id.greenCards);
        perfectPasses = (EditText) view.findViewById(R.id.myPlayerNr);
        scores = (EditText) view.findViewById(R.id.myPlayerType);
        accidents = (EditText) view.findViewById(R.id.accidentTV);


        matchRecordDialogAddBtn = (Button) view.findViewById(R.id.allUsersSpinner);
        matchRecordDialogAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerId.equals("")) {
                    Toast.makeText(mCallback.getContext(), R.string.matchRecordsSelectUserToast, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String sNumMinPlayed = minutesPlayed.getText().toString().trim();
                        String sNumRedCard = redCards.getText().toString().trim();
                        String sNumYellowCard = yellowCards.getText().toString().trim();
                        String sNumGreenCard = greenCards.getText().toString().trim();
                        String sNumPerfectPasses = perfectPasses.getText().toString().trim();
                        String sNumScores = scores.getText().toString().trim();
                        String sNumAccidents = accidents.getText().toString().trim();

                        if (!sNumMinPlayed.equals("")) {
                            numMinutPlayed = Integer.parseInt(sNumMinPlayed);
                        } else {
                            numMinutPlayed = 0;
                        }
                        if (!sNumRedCard.equals("")) {
                            numRedCard = Integer.parseInt(sNumRedCard);
                        } else {
                            numRedCard = 0;
                        }
                        if (!sNumYellowCard.equals("")) {
                            numYellowCard = Integer.parseInt(sNumYellowCard);
                        } else {
                            numYellowCard = 0;
                        }
                        if (!sNumGreenCard.equals("")) {
                            numGreenCard = Integer.parseInt(sNumGreenCard);
                        } else {
                            numGreenCard = 0;
                        }
                        if (!sNumPerfectPasses.equals("")) {
                            numPerfectPasses = Integer.parseInt(sNumPerfectPasses);
                        } else {
                            numPerfectPasses = 0;
                        }
                        if (!sNumScores.equals("")) {
                            numScores = Integer.parseInt(sNumScores);
                        } else {
                            numScores = 0;
                        }
                        if (!sNumAccidents.equals("")) {
                            numAccidents = Integer.parseInt(sNumAccidents);
                        } else {
                            numAccidents = 0;
                        }

                    } catch (Exception e) {

                    }
                    getDialog().dismiss();
                    DataBaseHelperB dbh = new DataBaseHelperB(mCallback.getContext());
                    dbh.registerPlayerMatchDataRecords(numMinutPlayed, numRedCard, numYellowCard, numGreenCard, numAccidents, numPerfectPasses, numScores, playerId);
                }
            }
        });

        ArrayAdapter<String> playerNamesSpinner = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, playerNames);
        playerNamesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMatchRecordsSelectPlayer.setAdapter(playerNamesSpinner);
        spinnerMatchRecordsSelectPlayer.setSelection(0);

        spinnerMatchRecordsSelectPlayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                playerId = playerIds.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }
}
