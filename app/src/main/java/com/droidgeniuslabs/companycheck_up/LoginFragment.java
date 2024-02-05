package com.droidgeniuslabs.companycheck_up;

import static com.droidgeniuslabs.companycheck_up.utilities.NetworkUtils.isNetworkAvailable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class LoginFragment extends Fragment {

    private Context applicationContext;
    private CallbackManager callbackManager;
    private  Arrays Arrays;
    public LoginFragment() {
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstanceState, AccessToken accessToken) {
        super.onCreate(savedInstanceState);

        if(isNetworkAvailable(requireContext())) {
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            GraphRequest request = GraphRequest.newMeRequest(
                                    accessToken,
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            getUserProfile(loginResult.getAccessToken());
                                        }

                                        final boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                                    });

                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name");
                            request.setParameters(parameters);
                            request.executeAsync();
                        }

                        @Override
                        public void onCancel() {
                            Log.d("Facebook Login", "Login canceled by user");
                        }

                        @Override
                        public void onError(@NonNull FacebookException error) {
                            if (error instanceof FacebookAuthorizationException) {
                                // Authorization error
                                // For example, the user denied some permissions
                                Log.e("Facebook Login", "Authorization Error: " + error.getMessage());
                            } else if (error instanceof FacebookOperationCanceledException) {
                                // User canceled the login
                                Log.e("Facebook Login", "Operation Canceled: " + error.getMessage());
                            } else {
                                // Other errors
                                Log.e("Facebook Login", "Error: " + error.getMessage());
                            }
                        }
                    });
            LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));

            }else{
                Toast.makeText(requireContext(),"Not Connected", Toast.LENGTH_LONG).show();
            }
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_login, container, false);
        EditText email = view.findViewById(R.id.editTextEmail);
        EditText pass = view.findViewById(R.id.editTextTextPassword);
        String InputEmail = email.getText().toString();
        String InputPass = pass.getText().toString();
        Button loginButton = view.findViewById(R.id.LoginButtonApp);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView register = view.findViewById(R.id.textViewSignUp);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_loginFragment_to_activeIsologismosFragment);
                Toast.makeText(requireContext(),"Welcome",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    private void getUserProfile(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    try {
                        String email = Objects.requireNonNull(object).getString("email");
                        String firstName = object.getString("name");

                        // Use the retrieved information as needed
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}