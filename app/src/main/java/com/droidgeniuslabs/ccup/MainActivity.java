package com.droidgeniuslabs.ccup;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.droidgeniuslabs.ccup.dbhelper.DatabaseHelper;
import com.droidgeniuslabs.ccup.dbhelper.SessionManager;
import com.droidgeniuslabs.ccup.ui.users.LoginFragment;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager sessionManager = new SessionManager(this);
        DatabaseHelper databaseHelper = new DatabaseHelper();

        if (sessionManager.isLoggedIn()) {
            // Auto login user
            String username = sessionManager.getUsername();

            try {
                if (databaseHelper.isFirstLogin(username)) {
                    // Redirect to login/register fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_login, new LoginFragment())
                            .commit();
                } else {
                    // Redirect to home fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_home, new HomeFragment())
                            .commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Redirect to login fragment (first-time users)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_login, new LoginFragment())
                    .commit();
        }
    }
}