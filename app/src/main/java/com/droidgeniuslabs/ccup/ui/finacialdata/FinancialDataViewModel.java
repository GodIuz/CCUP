package com.droidgeniuslabs.ccup.ui.finacialdata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.xml.sax.Attributes;

public class FinancialDataViewModel extends ViewModel {
    private final MutableLiveData<String> editTextMonth = new MutableLiveData<>();
    private final MutableLiveData<Double> editTextRevenue = new MutableLiveData<>();
    private final MutableLiveData<Double> editTextExpenses = new MutableLiveData<>();
    private final MutableLiveData<Double> editTextProfitLoss = new MutableLiveData<>();
    private final MutableLiveData<Double> editTextNumberExpensesGrowth = new MutableLiveData<>();
    private final MutableLiveData<Double> editTextRevenueGrowth = new MutableLiveData<>();
    private final MutableLiveData<Double> editTextCustomerCount = new MutableLiveData<>();
    private final MutableLiveData<Double> editTextSalesPerProduct = new MutableLiveData<>();

    public void setEditTextMonth(String value) { editTextMonth.setValue(value); }
    public MutableLiveData<String> getEditTextMonth() { return editTextMonth; }

    public void setEditTextRevenue(Double value) { editTextRevenue.setValue(value); }
    public MutableLiveData<Double> getEditTextRevenue() { return editTextRevenue; }

    public void setEditTextExpenses(Double value) { editTextExpenses.setValue(value); }
    public MutableLiveData<Double> getEditTextExpenses() { return editTextExpenses; }

    public void setEditTextProfitLoss(Double value) { editTextProfitLoss.setValue(value); }
    public MutableLiveData<Double> getEditTextProfitLoss() { return editTextProfitLoss; }

    public void setEditTextNumberExpensesGrowth(Double value) { editTextNumberExpensesGrowth.setValue(value); }
    public MutableLiveData<Double> getEditTextNumberExpensesGrowth() { return editTextNumberExpensesGrowth; }

    public void setditTextRevenueGrowth(Double value) { editTextRevenueGrowth.setValue(value); }
    public MutableLiveData<Double> getEditTextRevenueGrowth() { return editTextRevenueGrowth; }

    public void setEditTextCustomerCount(Double value) { editTextCustomerCount.setValue(value); }
    public MutableLiveData<Double> getEditTextCustomerCount() { return editTextCustomerCount; }

    public void setEditTextSalesPerProduct(Double value) { editTextSalesPerProduct.setValue(value); }
    public MutableLiveData<Double> getEditTextSalesPerProduct() { return editTextSalesPerProduct; }

}