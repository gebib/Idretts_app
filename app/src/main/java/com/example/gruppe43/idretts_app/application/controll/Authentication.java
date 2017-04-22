package com.example.gruppe43.idretts_app.application.controll;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.gruppe43.idretts_app.R;
import com.example.gruppe43.idretts_app.application.interfaces.FragmentActivityInterface;
import com.example.gruppe43.idretts_app.application.view.fragments.Tabs;
import com.example.gruppe43.idretts_app.application.view.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by gebi9 on 22-Apr-17.
 */

public class Authentication extends MainActivity{
    private FirebaseAuth fbAuth;
    private DatabaseReference fbDb;
    private ProgressDialog progressDialog;
    private FragmentActivityInterface mCallback;

    public Authentication() {
        fbAuth = FirebaseAuth.getInstance();
        fbDb = FirebaseDatabase.getInstance().getReference();
        mCallback = (FragmentActivityInterface) mainContext;
    }
    public void singIn(String email, String pass){
        progressDialog(true,"Sign in", "Signing in ...");
        fbAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mCallback.replaceFragmentWith(new Tabs());
                    mCallback.initAfterLogin(false, true);//TODO  check who is
                    progressDialog(false, "..", "..");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alert("Sign in", "Failed to sign in.. please check your internet connection and correct user email and password, and try again");
                progressDialog(false, "..", "..");
            }
        });
    }

    //register using email and password
    public void FBregisterUserforAuthentication(String email, String pass) {
        progressDialog(true, "Registration", "Processing please wait...");
        fbAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mCallback.replaceFragmentWith(new Tabs());
                    Toast.makeText(mainContext, "You have registered!", Toast.LENGTH_LONG).show();
                    mCallback.initAfterLogin(false, true);//TODO  remove this is temp
                    progressDialog(false, "..", "..");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alert(mainContext.getString(R.string.dbConnectionErrror), mainContext.getString(R.string.dbConnectionErrorTextnfo));
                progressDialog(false, "..", "..");
            }
        });
    }

    //register users additional informations after user is registered
    public void FBregisterUserAdditionalInfo(HashMap<String, String> newUserInfo) {
        fbDb.push().setValue(newUserInfo);
    }

    //show progress dialog while loading something
    public void progressDialog(Boolean showProgressDialog, String title, String message) {
        if (showProgressDialog) {
            progressDialog = new ProgressDialog(mainContext);
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    public void alert(String title, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainContext);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //ingen action.
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
