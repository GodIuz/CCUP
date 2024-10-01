package com.droidgeniuslabs.ccup.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.droidgeniuslabs.ccup.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreenFragment extends Fragment {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        ImageView logo = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);
        TextView textView2 = view.findViewById(R.id.textView2);
        TextView textView3 = view.findViewById(R.id.textView3);


        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in);
        logo.startAnimation(fadeIn);
        textView.startAnimation(fadeIn);
        textView2.startAnimation(fadeIn);
        textView3.startAnimation(fadeIn);
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_splashScreenFragment_to_loginFragment);
        return view;
    }
}