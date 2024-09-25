package com.droidgeniuslabs.ccup.dbhelper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "UserSession";
    private static final String IS_REMEMBERED = "isRemembered";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveLoginDetails(String username, boolean rememberMe) {
        editor.putBoolean(IS_REMEMBERED, rememberMe);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public boolean isRemembered() {
        return sharedPreferences.getBoolean(IS_REMEMBERED, false);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
