package com.droidgeniuslabs.ccup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import java.util.Locale;
import android.content.res.Configuration;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchTheme, switchNotifications;
    private SharedPreferences sharedPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btnReportBug = view.findViewById(R.id.btnReportBug);
        Spinner spinnerLanguage = view.findViewById(R.id.spinnerLanguage);
        Switch switchTheme =view.findViewById(R.id.switchTheme);
        Switch switchNotifications = view.findViewById(R.id.switchNotifications);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppSettings", 0);
        setInitialSettings();

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected language as a String
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                changeLanguage(selectedLanguage);  // Pass the selected language to the function
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Please select a language", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle theme change
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> toggleDarkMode(isChecked));

        // Handle notification toggle
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> toggleNotifications(isChecked));

        // Handle bug reporting
        btnReportBug.setOnClickListener(v -> reportBug());
        return view;
    }


    private void changeLanguage(Object position) {
        String selectedLanguage = position.toString();

        Locale locale;
        if (selectedLanguage.equals("English")) {
            locale = new Locale("en");  // English
        } else if (selectedLanguage.equals("Greek")) {
            locale = new Locale("el");  // Greek
        } else {
            locale = Locale.getDefault();  // Default language if not recognized
        }

        // Update the app's configuration with the new locale
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);

        // Apply the locale change for the app's resources
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Refresh the parent activity to apply the language change
        if (getActivity() != null) {
            getActivity().recreate();  // This works inside a Fragment
        }
    }

    private void toggleDarkMode(boolean isEnabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkMode", isEnabled);
        editor.apply();

        // Change theme
        if (isEnabled) {
            getActivity().setTheme(R.style.Theme_App_Dark);
        } else {
            getActivity().setTheme(R.style.Theme_App_Light);
        }

        // Reload the app to apply theme changes
        getActivity().recreate();
    }

    private void toggleNotifications(boolean isEnabled) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("notifications", isEnabled);
        editor.apply();

        // Handle notification enable/disable logic here
    }

    private void reportBug() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@ccup.app"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug Report");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Describe the bug here...");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Bug Report..."));
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle the case where no email clients are installed
        }
    }
    private void setInitialSettings() {
        // Language setting
        String language = sharedPreferences.getString("language", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        // Theme setting
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        switchTheme.setChecked(isDarkMode);

        // Notifications setting
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications", true);
        switchNotifications.setChecked(notificationsEnabled);
    }
}