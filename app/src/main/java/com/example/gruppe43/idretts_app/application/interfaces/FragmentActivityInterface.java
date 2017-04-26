package com.example.gruppe43.idretts_app.application.interfaces;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by gebi9 on 18-Mar-17.
 */

public interface FragmentActivityInterface {
    //void currentShowingFragment(String tabId);
    void replaceFragmentWith(Fragment fragmentClass);
    void initAfterLogin();
    void requIreAdminPass();
    //void initAfterLogin(Boolean isPlayerSignedIn, Boolean isTrainerSignedIn);
}
