package com.droidgeniuslabs.ccup.ui.users;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.droidgeniuslabs.ccup.HomeFragment;
import com.droidgeniuslabs.ccup.R;
import com.droidgeniuslabs.ccup.dbhelper.AutoCompleteHelper;
import com.droidgeniuslabs.ccup.dbhelper.DatabaseHelper;
import com.droidgeniuslabs.ccup.dbhelper.SessionManager;
import java.sql.SQLException;


public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText editTextEmail = view.findViewById(R.id.editTextTextEmailAddress);
        EditText editTextPass = view.findViewById(R.id.editTextTextPassword);
        CheckBox rememberCheckbox = view.findViewById(R.id.checkBox);
        TextView forgorPass = view.findViewById(R.id.textViewForgotRemember);
        TextView register = view.findViewById(R.id.textViewRegister);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);

        AutoCompleteHelper autoCompleteHelper = new AutoCompleteHelper(requireContext());
        DatabaseHelper databaseHelper = new DatabaseHelper();
        SessionManager sessionManager = new SessionManager(requireContext());

        if (sessionManager.isRemembered()) {
            String savedUsername = sessionManager.getUsername();
            if (savedUsername != null) {
                editTextEmail.setText(savedUsername);
                rememberCheckbox.setChecked(true);
            }
        }

        buttonLogin.setOnClickListener(v -> {
            String username = editTextEmail.getText().toString();
            String password = editTextPass.getText().toString();
            boolean rememberMe = rememberCheckbox.isChecked();

            try {
                if (databaseHelper.loginUser(username, password, rememberMe)) {
                    sessionManager.saveLoginDetails(username, rememberMe);
                    autoCompleteHelper.saveUsername(username);  // Save for autocomplete

                    // Redirect to home fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_home, new HomeFragment())
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return view;
    }
}