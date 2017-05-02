package com.example.gruppe43.idretts_app.application.view.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.Authentication;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.helper_classes.DatePickerFragment;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class Registration extends Fragment implements View.OnClickListener{
    private EditText firstNameET, lastNameET, ageET, emailET, passET, rePassET;
    private CheckBox acceptTermsCB;
    private String firstName, lastName, age, email, pss, rPss;
    private Boolean checkBox; //DO change this
    private TextView loginLink;
    private Button createAccount;
    private FragmentActivityInterface mCallback;
    private Authentication authClass;


    private FirebaseDatabase fbAuth;

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
        authClass = new Authentication(mCallback.getContext());

        firstNameET = (EditText) view.findViewById(R.id.input_firstName);
        lastNameET = (EditText) view.findViewById(R.id.input_lastName);
        ageET = (EditText) view.findViewById(R.id.input_age);
        emailET = (EditText) view.findViewById(R.id.input_email);
        passET = (EditText) view.findViewById(R.id.input_password);
        rePassET = (EditText) view.findViewById(R.id.input_reEnterPassword);
        acceptTermsCB = (CheckBox) view.findViewById(R.id.familyApproveCB);
        ageET.setKeyListener(null);
        ageET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
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

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(ondate);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            Calendar calender = Calendar.getInstance();
            int thisYear = calender.get(Calendar.YEAR);
            age = String.valueOf(thisYear-year);
            ageET.setText(age);
        }
    };

    @Override
    public void onClick(final View v) {
        firstName = firstNameET.getText().toString().trim();
        lastName = lastNameET.getText().toString().trim();
        age = ageET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        pss = passET.getText().toString().trim();
        rPss = rePassET.getText().toString().trim();
        checkBox = acceptTermsCB.isChecked();

        switch (v.getId()) {
            case R.id.createAccountBT:
                boolean validated = validateUser();
                if (validated) {
                    authClass.FBregisterUserforAuthentication(email, pss,firstName,lastName,age);
                }else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setTitle(getString(R.string.registrationLocalError));
                    builder1.setMessage(getString(R.string.registrationLocalErrorTextInfo));
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
                mCallback.replaceFragmentWith(new Login(),"reg");
                break;
            default:
        }
    }

    private boolean checkIfDbImpty() {
        boolean isFirstTime = false;




        return isFirstTime;
    }

    //register new user if valid locally
    private boolean validateUser() {
        Boolean isValid = true;
        String nameRegEx = "^[a-zA-ZæøåÆØÅ-]\\w{2,20}";
        String passRegEx = "^.{6,}$";
        //format = new SimpleDateFormat("dd/MM/YYYY");
        //String ageFormat = ageET.getText().toString();

        if (!firstName.matches(nameRegEx)) {
            firstNameET.setError(getString(R.string.first_name_reg));
            isValid = false;
        } else {
            firstNameET.setError(null);
        }
        if (!lastName.matches(nameRegEx)) {
            lastNameET.setError(getString(R.string.last_name_reg));
            isValid = false;
        } else {
            lastNameET.setError(null);
        }
        if (age.matches("")) {
            ageET.setError(getString(R.string.age_reg));
            isValid = false;
        } else {
            ageET.setError(null);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError(getString(R.string.email_reg));
            isValid = false;
        } else {
            emailET.setError(null);
        }
        if (!pss.matches(passRegEx)) {
            passET.setError(getString(R.string.password_reg));
            rePassET.setError(getString(R.string.password_reg));
            isValid = false;
        } else {
            passET.setError(null);
        }
        if (!pss.matches(rPss) && !pss.equals("")) {
            passET.setError(getString(R.string.password_reg));
            rePassET.setError(getString(R.string.password_reg));
            isValid = false;
        } else {
            rePassET.setError(null);
        }
        if (!checkBox) {
            isValid = false;
            acceptTermsCB.setError(getString(R.string.please_check_user_agreement));
        } else {
            acceptTermsCB.setError(null);
        }
        return isValid;

    }
}
