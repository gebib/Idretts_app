package com.example.gruppe43.idretts_app.application.helpers;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by W7-Stdnt on 11.02.2017.
 */

//this is the super class of the application, that will contain a common ressources
public abstract class AppCommonRessources extends AppCompatActivity implements View.OnClickListener {
    protected boolean isCoach,isPlayer,isFamily;
    protected Activity homeActivity;
}
