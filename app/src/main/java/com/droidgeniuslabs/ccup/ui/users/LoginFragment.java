package com.droidgeniuslabs.ccup.ui.users;

import static com.droidgeniuslabs.ccup.dbhelper.DatabaseHelper.isConnectedToInternet;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.droidgeniuslabs.ccup.dbhelper.AutoCompleteHelper;
import com.droidgeniuslabs.ccup.dbhelper.DatabaseHelper;
import com.droidgeniuslabs.ccup.dbhelper.SessionManager;
import java.sql.SQLException;
import com.droidgeniuslabs.ccup.R;


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
        TextView forgotPass = view.findViewById(R.id.textViewForgotRemember);
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
            String email = editTextEmail.getText().toString();
            String password = editTextPass.getText().toString();
            if(isConnectedToInternet(requireContext())) {
                if (!email.isEmpty()) {
                    if (!password.isEmpty()) {
                        try {
                            boolean result = databaseHelper.loginUser(email, password);
                            if (result) {
                                Toast.makeText(getContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                                NavController navController = Navigation.findNavController(view);
                                navController.navigate(R.id.action_loginFragment_to_homeFragment);
                            } else {
                                Toast.makeText(getContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        editTextPass.setError("Password is required");
                    }
                } else {
                    editTextEmail.setError("Email is required");
                }
            }else{
                Toast.makeText(requireContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        return view;
    }
}