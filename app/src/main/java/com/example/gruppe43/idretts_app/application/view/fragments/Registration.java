package com.example.gruppe43.idretts_app.application.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;


public class Registration extends Fragment implements View.OnClickListener {

    private EditText nameET;
    private EditText surNameET;
    private EditText ageET;
    private EditText passET;
    private EditText rePassET;
    private CheckBox acceptTermsCB;


    private String name, lastName, age, pss, rPss;
    private Boolean checkBox;

    private TextView loginLink;
    private Button createAccount;

    public Registration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        loginLink = (TextView) view.findViewById(R.id.linkLoginTV);
        createAccount = (Button) view.findViewById(R.id.createAccountBT);
        createAccount.setOnClickListener(this);
        loginLink.setOnClickListener(this);

        nameET = (EditText) view.findViewById(R.id.input_email);
        surNameET = (EditText) view.findViewById(R.id.input_surname);
        ageET = (EditText) view.findViewById(R.id.input_age);
        passET = (EditText) view.findViewById(R.id.input_password);
        rePassET = (EditText) view.findViewById(R.id.input_reEnterPassword);

        acceptTermsCB = (CheckBox) view.findViewById(R.id.familyApproveCB);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(final View v) {
        //do what you want to do when button is clicked
        name = nameET.getText().toString().trim();
        lastName = surNameET.getText().toString().trim();
        age = ageET.getText().toString().trim();
        pss = passET.getText().toString().trim();
        rPss = rePassET.getText().toString().trim();
        checkBox = acceptTermsCB.isChecked();


        switch (v.getId()) {
            case R.id.createAccountBT:
                if (checkRegistrationValidity()) {

                    registerUser();
                    Toast.makeText(getActivity(), "You are registered!", Toast.LENGTH_LONG).show();

                }else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setTitle(R.string.registrationFailureAlertTittle);
                    builder1.setMessage(R.string.alertTextRegisterFailure);
                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //ingen action.
                        }
                    });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            case R.id.linkLoginTV:

                break;
        }
    }

    //check for valid registration information using regX
    private boolean checkRegistrationValidity() {
        Boolean isValidInfo;
        String nameRegex = "^[a-zA-Z\\s]+";
        boolean isNumber, isNumberB;
        int ageLenth = age.length();
        try {
            Integer.parseInt(age);
            isNumberB = true;
        } catch (Exception e) {
            isNumberB = false;
        }
        Boolean nameOk = name.matches(nameRegex);
        Boolean lastNameOk = lastName.matches(nameRegex);
        Boolean ageOk = ageLenth == 2 && isNumberB;

        Boolean passOk = pss.length() > 3 ;
        Boolean rPassOk = rPss.length() > 3;
        Boolean completePassOk = false;
        if(passOk && rPassOk && pss.matches(rPss)){
            completePassOk = true;
            passET.setBackgroundColor(Color.parseColor("#68C3FC"));
            passET.setTextColor(Color.BLACK);
            rePassET.setBackgroundColor(Color.parseColor("#68C3FC"));
            rePassET.setTextColor(Color.BLACK);
        }else{
            passET.setBackgroundColor(Color.parseColor("#009DFF"));
            passET.setTextColor(Color.RED);
            rePassET.setBackgroundColor(Color.parseColor("#009DFF"));
            rePassET.setTextColor(Color.RED);
        }

        if(!checkBox){
            acceptTermsCB.setBackgroundColor(Color.parseColor("#009DFF"));
        }else{
            acceptTermsCB.setBackgroundColor(Color.parseColor("#68C3FC"));
        }

        if(nameOk && lastNameOk && ageOk && completePassOk && checkBox){
            isValidInfo = true;
        }else{
            isValidInfo = false;
            EditText values[] = {nameET,surNameET,ageET};
            Boolean isValids[] = {nameOk,lastNameOk,ageOk};
            for (int i = 0; i < values.length; i++) {
                if(!isValids[i]) {
                    values[i].setBackgroundColor(Color.parseColor("#009DFF"));
                    values[i].setTextColor(Color.RED);
                }else{
                    values[i].setBackgroundColor(Color.parseColor("#68C3FC"));
                    values[i].setTextColor(Color.BLACK);
                }
            }
        }
        return isValidInfo;
    }

    //register new user if valid
    private void registerUser() {

    }
}
