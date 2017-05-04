package com.example.gruppe43.idretts_app.application.interfaces;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;

import com.example.gruppe43.idretts_app.application.view.main.MainActivity;

/**
 * Created by gebi9 on 18-Mar-17.
 */

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
