package com.example.gruppe43.idretts_app.application.view.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.controll.Authentication;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class Login extends Fragment implements View.OnClickListener {
    private TextView signUpTV;
    private Button loginButtonBT;
    private EditText emailAdressET;
    private EditText passwordET;
    private FragmentActivityInterface mCallback;
    private Authentication authClass;



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
        authClass = new Authentication();
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


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.link_signup:
                mCallback.replaceFragmentWith(new Registration(),"");
                MainActivity.onRegisterPage = true;
                break;
            case R.id.loginBT:
                Boolean validInfo = validFormat();
                if (validInfo) {
                    mCallback.hideKeyboard();
                   authClass.signIn(emailAdressET.getText().toString().trim(),passwordET.getText().toString().trim());
                } else if (!validInfo) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setTitle(R.string.loginFailureAlertTitle);
                    builder1.setMessage(getString(R.string.loginFailureTextInfo));
                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //ingen action.
                        }
                    });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            default:
        }
    }

    //validate loggin info format locally
    private Boolean validFormat() {
        Boolean isValid = false;
        String eMail, password;
        eMail = emailAdressET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(eMail).matches()) {
            emailAdressET.setError(null);
            isValid = true;
        } else {
            emailAdressET.setError(getString(R.string.email_reg));
        }
        if (password.equals("")) {
            passwordET.setError(getString(R.string.password_reg));
            isValid = false;
        } else {
            passwordET.setError(null);
        }
        return isValid;
    }
}
