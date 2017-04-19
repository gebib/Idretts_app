package com.example.gruppe43.idretts_app.application.view.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.fragment_interfaces.FragmentActivityInterface;


public class Login extends Fragment implements View.OnClickListener {
    private TextView signUpTV;
    private Button loginButtonBT;
    private EditText emailAdressET;
    private EditText passwordET;
    private FragmentActivityInterface mCallback;


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
                mCallback.replaceFragmentWith(new Registration());
                break;
            case R.id.loginBT:
                Boolean validInfo = validFormat();
                Boolean signInOk = signInAuthenthication();
                if (validInfo && signInOk) {
                    gotoHomePage();
                }else if(!validInfo || ! signInOk){
                    alertDialog();
                }
                break;
            default:
        }
    }

    //open home page after successfull sign in.
    public void gotoHomePage() {
        mCallback.replaceFragmentWith(new Tabs());
        mCallback.initAfterLogin(false, true);// will be set when user signs in!
    }

    //popup message
    public void alertDialog() {
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

    //external firebase authentication
    private Boolean signInAuthenthication() {
        //TODO firebase sign in check! use progress spinner...
        String loginEmail = emailAdressET.getText().toString().trim();
        String pass = passwordET.getText().toString().trim();
        return true;
    }

}
