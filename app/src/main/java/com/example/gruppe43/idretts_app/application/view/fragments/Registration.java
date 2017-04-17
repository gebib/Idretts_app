package com.example.gruppe43.idretts_app.application.view.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.R.attr.format;


public class Registration extends Fragment implements View.OnClickListener {
    
    private EditText firstNameET, lastNameET, ageET, emailET, passET, rePassET;
    private Boolean isValid;
    private CheckBox acceptTermsCB;

    private String firstName, lastName, age, email, pss, rPss;
    private Boolean checkBox; //DO change this

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
        ageET.setOnClickListener(this);

        firstNameET = (EditText) view.findViewById(R.id.input_firstName);
        lastNameET  = (EditText) view.findViewById(R.id.input_lastName);
        ageET       = (EditText) view.findViewById(R.id.input_age);
        emailET     = (EditText) view.findViewById(R.id.input_email);
        passET      = (EditText) view.findViewById(R.id.input_password);
        rePassET    = (EditText) view.findViewById(R.id.input_reEnterPassword);
        acceptTermsCB = (CheckBox) view.findViewById(R.id.familyApproveCB);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(final View v) {
        //do what you want to do when button is clicked
        firstName   = firstNameET.getText().toString().trim();
        lastName    = lastNameET.getText().toString().trim();
        age         = ageET.getText().toString().trim();
        email       = emailET.getText().toString().trim();
        pss         = passET.getText().toString().trim();
        rPss        = rePassET.getText().toString().trim();
        checkBox    = acceptTermsCB.isChecked();


        switch (v.getId()) {
            case R.id.createAccountBT:
                if (validateUser()) {
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
            case R.id.input_age: //TODO: fragmentManager/Transaction
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
                break;
        }
    }


    //register new user if valid
    private boolean validateUser() {
        Boolean isValid;
        String nameRegEx    = "^[a-zA-ZæøåÆØÅ- ]\\w{2,20}";
        String ageRegEx     = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20|21)\\\\d\\\\d)";
        String passRegEx    = "^.{6,}$";
        //format = new SimpleDateFormat("dd/MM/YYYY");
        //String ageFormat = ageET.getText().toString();

        if (!firstName.matches(nameRegEx)){
            firstNameET.setError(getString(R.string.first_name_reg));
            isValid = false;

        } else if (!lastName.matches(nameRegEx)){
            firstNameET.setError(getString(R.string.last_name_reg));
            isValid = false;
        //TODO: remove after DatePickerFragment communicates
        } else if (!age.matches(ageRegEx) /* FORMATTEST*/ ){
            ageET.setError(getString(R.string.age_reg));
            isValid = false;

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ageET.setError(getString(R.string.email_reg));
            isValid = false;

        } else if (!pss.matches(passRegEx)){
            passET.setError(getString(R.string.password_reg));
            isValid = false;

        } else (!pss.matches(rPss)){
            passET.setError(getString(R.string.password_must_match));
            isValid = false;
        }
        return isValid;



    }
}
