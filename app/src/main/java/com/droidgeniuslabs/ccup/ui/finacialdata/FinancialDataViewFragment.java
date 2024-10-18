package com.droidgeniuslabs.ccup.ui.finacialdata;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.droidgeniuslabs.ccup.R;
import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinancialDataViewFragment extends Fragment {

    private SparkView sparkView;
    private Spinner dataSpinner;
    private FinancialDataViewModel financialDataViewModel;

    public FinancialDataViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_financial_data_view, container, false);

        dataSpinner=view.findViewById(R.id.dataSpinner);
        sparkView = view.findViewById(R.id.sparkview);
        financialDataViewModel = new ViewModelProvider(requireActivity()).get(FinancialDataViewModel.class);

        setupSpinnerListener();
        observeFinancialData();

        return view;
    }

    private void setupSpinnerListener() {
        dataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedVariable = (String) parent.getItemAtPosition(position);
                updateChart(selectedVariable);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void observeFinancialData() {
        // Observe the months and update the chart when data is fetched
        financialDataViewModel.getEditTextMonth().observe(getViewLifecycleOwner(), months -> {
            if (months != null) {
                // Populate the chart based on the current spinner selection
                updateChart(dataSpinner.getSelectedItem().toString());
            }
        });
    }

    private void updateChart(String selectedVariable) {
        List<String> months = Collections.singletonList(financialDataViewModel.getEditTextMonth().getValue());
        List<Float> values = new ArrayList<>();

        switch (selectedVariable) {
            case "Revenue":
                values = convertToFloatList(Collections.singletonList(financialDataViewModel.getEditTextRevenue().getValue()));
                break;
            case "Expenses":
                values = convertToFloatList(Collections.singletonList(financialDataViewModel.getEditTextExpenses().getValue()));
                break;
            case "Profit/Loss":
                values = convertToFloatList(Collections.singletonList(financialDataViewModel.getEditTextProfitLoss().getValue()));
                break;
            case "Revenue Growth":
                values = convertToFloatList(Collections.singletonList(financialDataViewModel.getEditTextNumberExpensesGrowth().getValue()));
                break;
            case "Expenses Growth":
                values = convertToFloatList(Collections.singletonList(financialDataViewModel.getEditTextRevenueGrowth().getValue()));
                break;
            case "Customer Count":
                values = convertToFloatList(Collections.singletonList(financialDataViewModel.getEditTextCustomerCount().getValue()));
                break;
            case "Sales Per Product":
                values = convertToFloatList(Collections.singletonList(financialDataViewModel.getEditTextSalesPerProduct().getValue()));
                break;
        }

        if (values != null) {
            // Set up the SparkAdapter and display the data
            List<Float> finalValues = values;
            sparkView.setAdapter(new SparkAdapter() {
                @Override
                public int getCount() {
                    return finalValues.size();
                }

                @Override
                public Object getItem(int index) {
                    return finalValues.get(index);
                }

                @Override
                public float getY(int index) {
                    return finalValues.get(index);
                }
            });
        }
    }

    private List<Float> convertToFloatList(List<Double> doubleList) {
        if (doubleList == null) return null;
        List<Float> floatList = new ArrayList<>();
        for (Double value : doubleList) {
            floatList.add(value.floatValue());
        }
        return floatList;
    }
}