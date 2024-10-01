package com.droidgeniuslabs.ccup.ui.users;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.droidgeniuslabs.ccup.R;
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
        loginButton.setFragment(this);

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