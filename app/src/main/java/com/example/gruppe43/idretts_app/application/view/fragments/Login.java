package com.example.gruppe43.idretts_app.application.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;


public class Login extends Fragment implements View.OnClickListener {
    private TextView signUp;
    private Button loginButton;


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        signUp = (TextView) view.findViewById(R.id.link_signup);
        loginButton = (Button) view.findViewById(R.id.loginBT);
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
                Toast.makeText(getActivity(), "LoginBT!", Toast.LENGTH_LONG).show();
                break;
        }
    }

}
