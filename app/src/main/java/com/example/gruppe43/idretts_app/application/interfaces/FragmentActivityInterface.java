package com.example.gruppe43.idretts_app.application.interfaces;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;

import com.example.gruppe43.idretts_app.application.view.main.MainActivity;

//Idretts-app bachelor oppgave 2017
//Ole-Kristian Steiro, Tasmia Faruque, Gebi Beshir

public interface FragmentActivityInterface {
    //void currentShowingFragment(String tabId);
    void initAfterLogin(String userType);
    void currentShowingFragment(String tabId);
    void onSignOut();
    void hideKeyboard();
    void showFragmentOfGivenCondition();
    MainActivity getContext();
    void setIsOnNewActivityRegisterPage(boolean b);
    FragmentManager getmFragmentManager();
    FloatingActionButton getFab();
    void clearBackStack();
}
