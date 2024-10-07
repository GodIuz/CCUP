package com.droidgeniuslabs.ccup.ui.isologismos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.droidgeniuslabs.ccup.R;

public class ActiveIsoFragment extends Fragment {

    public ActiveIsoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_iso, container, false);
        Button buttonNext =view.findViewById(R.id.buttonNext);
        EditText editTextPagio = view.findViewById(R.id.editTextPagio);
        EditText editTextApothema = view.findViewById(R.id.editTextApothema);
        EditText editTextApaitiseis = view.findViewById(R.id.editTextApaitiseis);
        EditText editTextDiathesima = view.findViewById(R.id.editTextDiathesima);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedViewModel.setEditTextPagio(editTextPagio.getText().toString());
                sharedViewModel.setEditTextApothema(editTextApothema.getText().toString());
                sharedViewModel.setEditTextApaitiseis(editTextApaitiseis.getText().toString());
                sharedViewModel.setEditTextDiathesima(editTextDiathesima.getText().toString());
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_activeIsoFragment_to_liabilitiesIsoFragment);
            }
        });

        return view;
    }
}