package com.example.gruppe43.idretts_app.application.helper_classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by gebi9 on 30-Apr-17.
 */

public class PrefferencesClass {
    private Context ctx;
    public PrefferencesClass(Context ctx) {
        this.ctx = ctx;
    }

    public void saveSharedPrefData(String key, String value) {
        SharedPreferences.Editor editor;
        try {
            editor = ctx.getSharedPreferences("IdrettsAppData", Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.commit();
        } catch (NullPointerException npe) {
            Log.e("///////SHARED_PREF_SAVE", "isNullpointing");
        }
    }

    public String loadSharedPrefData(String key) {
        String data = "No name defined";
        try {
            SharedPreferences prefs = ctx.getSharedPreferences("IdrettsAppData", Context.MODE_PRIVATE);
            data = prefs.getString(key, "No name defined");
        } catch (NullPointerException npe) {
            Log.e("///////SHARED_PREF_LOAD", "isNullpointing");
        }
        return data;
    }
}
