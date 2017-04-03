package com.example.gruppe43.idretts_app.application.view.fragments;

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
    private EditText tlfET;
    private EditText ageET;
    private EditText passET;
    private EditText rePassET;
    private CheckBox acceptTermsCB;
    private EditText adminIdET;

    private String a, b, c, d, e, f, h;
    private Boolean g;

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

        nameET = (EditText) view.findViewById(R.id.input_name);
        surNameET = (EditText) view.findViewById(R.id.input_surname);
        tlfET = (EditText) view.findViewById(R.id.input_phone);
        ageET = (EditText) view.findViewById(R.id.input_age);
        passET = (EditText) view.findViewById(R.id.input_password);
        rePassET = (EditText) view.findViewById(R.id.input_reEnterPassword);

        acceptTermsCB = (CheckBox) view.findViewById(R.id.familyApproveCB);

        adminIdET = (EditText) view.findViewById(R.id.coachCode);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(final View v) {
        //do what you want to do when button is clicked

        a = nameET.getText().toString();
        b = surNameET.getText().toString();
        c = tlfET.getText().toString();
        d = ageET.getText().toString();
        e = passET.getText().toString();
        f = rePassET.getText().toString();
        g = acceptTermsCB.isChecked();
        h = adminIdET.getText().toString();

        switch (v.getId()) {
            case R.id.createAccountBT:
                if (checkValidity()) {

                    Toast.makeText(getActivity(), "You are registered!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Please use a valid registration information", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.linkLoginTV:
                Toast.makeText(getActivity(), "loginLink", Toast.LENGTH_LONG).show();
                break;
        }
    }

    //check for valid registration information using regX
    private boolean checkValidity() {

        return true;
    }
}
