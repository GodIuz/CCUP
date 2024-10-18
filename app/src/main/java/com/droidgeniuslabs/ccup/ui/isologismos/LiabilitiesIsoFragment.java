package com.droidgeniuslabs.ccup.ui.isologismos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.droidgeniuslabs.ccup.R;

public class LiabilitiesIsoFragment extends Fragment {


    public LiabilitiesIsoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_liabilities_iso, container, false);
      SharedViewModel  sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

      EditText editTextKefalaia = view.findViewById(R.id.editTextKefalaia);
      EditText editTextProvlepseis = view.findViewById(R.id.editTextProvlepseis);
      EditText editTextM_Ypo = view.findViewById(R.id.editTextM_Ypo);
      EditText editTextB_Ypo = view.findViewById(R.id.editTextB_Ypo);
      Button buttonNext = view.findViewById(R.id.buttonNext);
      Button buttonPrevious = view.findViewById(R.id.buttonPrevious);

      buttonPrevious.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              NavController navController = Navigation.findNavController(view);
              navController.navigate(R.id.action_liabilitiesIsoFragment_to_activeIsoFragment);
          }
      });

      buttonNext.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              sharedViewModel.setEditTextKefalaia(editTextKefalaia.getText().toString());
              sharedViewModel.setEditTextProvlepseis(editTextProvlepseis.getText().toString());
              sharedViewModel.setEditTextM_Ypo(editTextM_Ypo.getText().toString());
              sharedViewModel.setEditTextB_Ypo(editTextB_Ypo.getText().toString());
              NavController navController = Navigation.findNavController(view);
              navController.navigate(R.id.action_liabilitiesIsoFragment_to_isoTotalFragment);
          }
      });
      return view;
    }
}