package com.droidgeniuslabs.ccup.ui.users;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.droidgeniuslabs.ccup.R;
import com.droidgeniuslabs.ccup.dbhelper.DatabaseHelper;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.SQLException;


public class RegisterFragment extends Fragment {

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        CallbackManager callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = view.findViewById(R.id.login_button);
        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        Button registerButton = view.findViewById(R.id.buttonRegister);
        EditText editTextFirst = view.findViewById(R.id.editTextFirstName);
        EditText editTextLast = view.findViewById(R.id.editTextLastName);
        EditText editTextEmail = view.findViewById(R.id.editTextEmailAddress);
        EditText editTextPass = view.findViewById(R.id.editTextPassword);
        EditText editTextConfirm = view.findViewById(R.id.editTextConfirmPassword);

        loginButton.setFragment(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = editTextFirst.getText().toString();
                String lastname = editTextLast.getText().toString();
                String email = editTextEmail.getText().toString();
                String pass = editTextPass.getText().toString();
                String confirm = editTextConfirm.getText().toString();
                DatabaseHelper databaseHelper = new DatabaseHelper();
                if (!firstname.isEmpty()) {
                  if(!lastname.isEmpty()) {
                      if(!email.isEmpty()){
                          if(!pass.isEmpty())
                          {
                              if(!confirm.isEmpty()) {
                                  if (confirm.equals(pass)) {
                                      try {
                                          boolean result = databaseHelper.registerUser(firstname, lastname, email, pass);
                                          if (result) {
                                              Toast.makeText(getContext(), "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                                              NavController navController = Navigation.findNavController(view);
                                              navController.navigate(R.id.action_registerFragment_to_homeFragment);
                                          } else {
                                              Toast.makeText(getContext(), "Registration Failed!", Toast.LENGTH_SHORT).show();
                                          }
                                      } catch (SQLException e) {
                                          throw new RuntimeException(e);
                                      }
                                  } else {
                                      Toast.makeText(requireContext(), "Passwords did not match", Toast.LENGTH_SHORT).show();
                                  }
                              }else{
                                  Toast.makeText(requireContext(), "Please fill this field",Toast.LENGTH_SHORT).show();
                              }
                          }else{
                              editTextPass.setError("Password is required");
                          }
                      }else{
                          editTextEmail.setError("Email is required.");
                      }
                  }else{
                      editTextLast.setError("Last name is required.");
                  }
                }else{
                 editTextFirst.setError("First name is required.");
                }
            }
        });



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.e("FacebookLogin", "Error: " + e.getMessage());
                Toast.makeText(getContext(), "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Log.d("FacebookLogin", "Login successful. AccessToken: " + accessToken.getToken());
                Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();

                // You can also retrieve user info here
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String facebookId = object.getString("id");
                                    Log.d("FacebookLogin", "User Info: Name=" + name + ", Email=" + email + ", ID=" + facebookId);
                                    // Handle user data (store in database or preferences)
                                } catch (JSONException e) {
                                    Log.e("FacebookLogin", "Error parsing user info: " + e.getMessage());
                                }
                            }
                        });

                // Bundle the request parameters
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d("FacebookLogin", "Login canceled");
                Toast.makeText(getContext(), "Login canceled", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}