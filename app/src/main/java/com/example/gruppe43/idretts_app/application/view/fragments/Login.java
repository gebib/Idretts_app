package com.example.gruppe43.idretts_app.application.view.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;


public class Login extends Fragment implements View.OnClickListener {
    private TextView signUp;
    private Button loginButton;
    private EditText mobileNumberET;
    private EditText passwordET;


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signUp = (TextView) view.findViewById(R.id.link_signup);
        loginButton = (Button) view.findViewById(R.id.loginBT);
        mobileNumberET = (EditText) view.findViewById(R.id.input_phone);
        passwordET = (EditText) view.findViewById(R.id.input_password);
        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(final View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.link_signup:
                Toast.makeText(getActivity(), "signUplink!", Toast.LENGTH_LONG).show();
                break;
            case R.id.loginBT:
                String mobileNumber, password;
                mobileNumber = mobileNumberET.getText().toString().trim();
                password = passwordET.getText().toString().trim();
                if (mobileNumberET.getText().toString().trim().equals("") || mobileNumber.length() < 8) {
                    mobileNumberET.setBackgroundColor(Color.parseColor("#009DFF"));
                    mobileNumberET.setTextColor(Color.RED);
                } else {
                    mobileNumberET.setBackgroundColor(Color.parseColor("#68C3FC"));
                    mobileNumberET.setTextColor(Color.BLACK);
                }
                if (passwordET.getText().toString().trim().equals("")) {
                    passwordET.setBackgroundColor(Color.parseColor("#009DFF"));
                    passwordET.setTextColor(Color.RED);
                } else {
                    passwordET.setBackgroundColor(Color.parseColor("#68C3FC"));
                    passwordET.setTextColor(Color.BLACK);
                }
                if (mobileNumber.length() > 7 && !password.equals("")) {
                    Toast.makeText(getActivity(), "in!", Toast.LENGTH_LONG).show();
                    signIn();
                }
                break;
        }
    }

    //sign in
    public void signIn(){

    }
}
