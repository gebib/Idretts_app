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
public class Registration extends Fragment implements View.OnClickListener {
    private TextView loginLink;
    private Button createAccount;

    public Registration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        loginLink = (TextView) view.findViewById(R.id.linkLoginTV);
        createAccount = (Button) view.findViewById(R.id.createAccountBT);
        createAccount.setOnClickListener(this);
        loginLink.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(final View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.linkLoginTV:
                Toast.makeText(getActivity(), "login link!", Toast.LENGTH_LONG).show();
                break;
            case R.id.createAccountBT:
                Toast.makeText(getActivity(), "createAccountBT!", Toast.LENGTH_LONG).show();
                break;
        }
    }

}
