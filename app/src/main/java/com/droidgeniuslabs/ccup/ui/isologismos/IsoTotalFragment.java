package com.droidgeniuslabs.ccup.ui.isologismos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.droidgeniuslabs.ccup.R;

class IsoTotalFragment extends Fragment {

    private TextView textViewSummary;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_iso_total, container, false);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        textViewSummary = view.findViewById(R.id.textViewSummary);
        Button buttonBack = view.findViewById(R.id.buttonBack);
        Button buttonFinish = view.findViewById(R.id.buttonFinish);

        buttonBack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_isoTotalFragment_to_liabilitiesIsoFragment);
        });

        buttonFinish.setOnClickListener(view1 -> {
            submitData();
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_isoTotalFragment_to_homeFragment);
        });
        return view;
    }

    private void collectDataFromViewModel() {
        sharedViewModel.getEditTextPagio().observe(getViewLifecycleOwner(), pagio -> appendToSummary("Pagio: " + pagio));
        sharedViewModel.getEditTextApothema().observe(getViewLifecycleOwner(), apothema -> appendToSummary("Apothema: " + apothema));
        sharedViewModel.getEditTextApaitiseis().observe(getViewLifecycleOwner(), apaitiseis -> appendToSummary("Apaitiseis: " + apaitiseis));
        sharedViewModel.getEditTextDiathesima().observe(getViewLifecycleOwner(), diathesima -> appendToSummary("Diathesima: " + diathesima));
        sharedViewModel.getEditTextKefalaia().observe(getViewLifecycleOwner(), kefalaia -> appendToSummary("Kefalaia: " + kefalaia));
        sharedViewModel.getEditTextProvlepseis().observe(getViewLifecycleOwner(), provlepseis -> appendToSummary("Provlepseis: " + provlepseis));
        sharedViewModel.getEditTextM_Ypo().observe(getViewLifecycleOwner(), m_ypo -> appendToSummary("Makroprothesmes Ypoxrewseis: " + m_ypo));
        sharedViewModel.getEditTextB_Ypo().observe(getViewLifecycleOwner(), text4 -> appendToSummary("Braxyprothesmes Ypoxrewseis: " + text4));
    }

    @SuppressLint("SetTextI18n")
    private void appendToSummary(String data) {
        String currentText = textViewSummary.getText().toString();
        textViewSummary.setText(currentText + "\n" + data);
    }
    private void submitData() {
        // Perform final action, like saving data to a database or making a server request
        Toast.makeText(requireContext(), "Data submitted successfully!", Toast.LENGTH_SHORT).show();
    }
}