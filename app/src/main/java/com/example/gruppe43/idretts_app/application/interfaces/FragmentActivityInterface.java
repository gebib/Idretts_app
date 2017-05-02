package com.example.gruppe43.idretts_app.application.interfaces;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.gruppe43.idretts_app.application.view.main.MainActivity;

import java.util.HashMap;

/**
 * Created by gebi9 on 18-Mar-17.
 */

public interface FragmentActivityInterface {
    //void currentShowingFragment(String tabId);
    void replaceFragmentWith(Fragment fragmentClass,String from);
    void initAfterLogin(String userType);
    void currentShowingFragment(String tabId);
    void onSignOut();
    void hideKeyboard();
    void showFragmentOfGivenCondition();
    void dumpBackStack();
    MainActivity getContext();
    void setIsRegisterSuccesfull(boolean isRegisterSuccesfull);
    void setOnNewActivityRegisterPage(boolean b);
    //void initAfterLogin(Boolean isPlayerSignedIn, Boolean isTrainerSignedIn);
}
