package com.example.gruppe43.idretts_app.application.fragment_interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by gebi9 on 18-Mar-17.
 */

public interface FragmentActivityInterface {
    void currentShowingFragment(String tabId);
    void replaceFragmentWith(Fragment fragmentClass);
}
