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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;


public class Login extends Fragment implements View.OnClickListener {
    private TextView signUpTV;
    private Button loginButtonBT;
    private EditText emailAdressET;
    private EditText passwordET;


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signUpTV = (TextView) view.findViewById(R.id.link_signup);
        loginButtonBT = (Button) view.findViewById(R.id.loginBT);
        emailAdressET = (EditText) view.findViewById(R.id.input_email);
        passwordET = (EditText) view.findViewById(R.id.input_password);
        loginButtonBT.setOnClickListener(this);
        signUpTV.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(final View v) {
        //do what you want to do when button is clicked
        Boolean emailNotEmpty = false;
        Boolean paswordNotEmpty = false;
        switch (v.getId()) {
            case R.id.link_signup:
                Toast.makeText(getActivity(), "signUplink!", Toast.LENGTH_LONG).show();
                break;
            case R.id.loginBT:
                String eMail, password;
                eMail = emailAdressET.getText().toString().trim();
                password = passwordET.getText().toString().trim();

                if (eMail.equals("") && password.equals("")) {
                    emailAdressET.setBackgroundColor(Color.parseColor("#009DFF"));
                    emailAdressET.setTextColor(Color.RED);
                    passwordET.setBackgroundColor(Color.parseColor("#009DFF"));
                    passwordET.setTextColor(Color.RED);
                    emailNotEmpty = false;
                    paswordNotEmpty = false;
                }
                if (eMail.equals("")) {
                    emailAdressET.setBackgroundColor(Color.parseColor("#009DFF"));
                    emailAdressET.setTextColor(Color.RED);
                    emailNotEmpty = false;
                }
                if (!eMail.equals("")) {
                    emailAdressET.setBackgroundColor(Color.parseColor("#68C3FC"));
                    emailAdressET.setTextColor(Color.BLACK);
                    emailNotEmpty = true;
                }
                if (password.equals("")) {
                    passwordET.setBackgroundColor(Color.parseColor("#009DFF"));
                    passwordET.setTextColor(Color.RED);
                    paswordNotEmpty = false;
                }
                if (!password.equals("")) {
                    passwordET.setBackgroundColor(Color.parseColor("#68C3FC"));
                    passwordET.setTextColor(Color.BLACK);
                    paswordNotEmpty = true;
                }
                break;
        }
        if (emailNotEmpty && paswordNotEmpty) {
            signIn();
            Toast.makeText(getActivity(), "signInOK!", Toast.LENGTH_LONG).show();
        } else if(v.getId() != R.id.link_signup) {
            popUpDialog();
        }
    }

    //sign in
    public void signIn() {

    }

    //popup message
    public void popUpDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle(R.string.loginFailureTitle);
        builder1.setMessage(R.string.loginFailureMessage);
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //ingen action.
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
