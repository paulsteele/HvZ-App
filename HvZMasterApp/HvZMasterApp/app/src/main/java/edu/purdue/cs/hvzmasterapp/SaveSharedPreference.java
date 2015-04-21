package edu.purdue.cs.hvzmasterapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Wells on 4/2/2015.
 */
public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void clearUserName(Context ctx) {
        Log.d("SaveSharedPreference", "Clearing username");
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public static void setUserName(Context ctx, String userName) {
        Log.d("SaveSharedPreference", "Setting username" + userName);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        Log.d("SaveSharedPreference", "Getting username" + PREF_USER_NAME);
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
}