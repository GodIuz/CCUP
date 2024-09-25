package com.droidgeniuslabs.ccup.dbhelper;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class AutoCompleteHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "UsernameHistory";
    private static final String KEY_EMAILS = "emails";

    public AutoCompleteHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUsername(String username) {
        Set<String> usernames = sharedPreferences.getStringSet(KEY_EMAILS, new HashSet<>());
        usernames.add(username);
        editor.putStringSet(KEY_EMAILS, usernames);
        editor.apply();
    }

    public Set<String> getEmails() {
        return sharedPreferences.getStringSet(KEY_EMAILS, new HashSet<>());
    }
}

