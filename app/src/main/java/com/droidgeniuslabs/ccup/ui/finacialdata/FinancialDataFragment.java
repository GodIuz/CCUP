package com.droidgeniuslabs.ccup.ui.finacialdata;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.droidgeniuslabs.ccup.R;
import com.droidgeniuslabs.ccup.dbhelper.DatabaseHelper;

public class FinancialDataFragment extends Fragment {

    private FinancialDataViewModel mViewModel;
    private double month,revene,expenses,profitloss,expensesgrowth,revenuegrowth,customercount,salesperprodut;

    public static FinancialDataFragment newInstance() {
        return new FinancialDataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financial_data1, container, false);
        FinancialDataViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(FinancialDataViewModel.class);

        Button buttonSumbit =  view.findViewById(R.id.buttonSumbit);
        EditText editTextMonth = view.findViewById(R.id.editTextMonth);
        EditText editTextRevenue = view.findViewById(R.id.editTextRevenue);
        EditText editTextExpenses = view.findViewById(R.id.editTextExpenses);
        EditText editTextProfitLoss = view.findViewById(R.id.editTextProfitLoss);
        EditText editTextExpensesGrowth = view.findViewById(R.id.editTextNumberExpensesGrowth);
        EditText editTextRevenueGrowth = view.findViewById(R.id.editTextRevenueGrowth);
        EditText editTextCustomerCount = view.findViewById(R.id.editTextCustomerCount);
        EditText editTextSalesPerProduct = view.findViewById(R.id.editTextSalesPerProduct);

        buttonSumbit.setOnClickListener(view1 -> {
            sharedViewModel.setEditTextMonth(editTextMonth.getText().toString());
            sharedViewModel.setEditTextRevenue(Double.valueOf(editTextRevenue.getText().toString()));
            sharedViewModel.setEditTextExpenses(Double.valueOf(editTextExpenses.getText().toString()));
            sharedViewModel.setEditTextProfitLoss(Double.valueOf(editTextProfitLoss.getText().toString()));
            sharedViewModel.setEditTextNumberExpensesGrowth(Double.valueOf(editTextExpensesGrowth.getText().toString()));
            sharedViewModel.setditTextRevenueGrowth(Double.valueOf(editTextRevenueGrowth.getText().toString()));
            sharedViewModel.setEditTextCustomerCount(Double.valueOf(editTextCustomerCount.getText().toString()));
            sharedViewModel.setEditTextSalesPerProduct(Double.valueOf(editTextSalesPerProduct.getText().toString()));
            DatabaseHelper databaseHelper = new DatabaseHelper();
            databaseHelper.storeFinancialData(sharedViewModel);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FinancialDataViewModel.class);
    }
}