package com.droidgeniuslabs.ccup.ui.finacialdata;


public class FinancialData {
    private String month; // Μήνας
    private float revenue; // Έσοδα
    private float expenses; // Έξοδα
    private float profitLoss; // Κέρδη/Ζημίες
    private float revenueGrowth; // Ποσοστό αύξησης εσόδων
    private float expensesGrowth; // Ποσοστό αύξησης εξόδων
    private int customerCount; // Αριθμός πελατών
    private float salesPerProduct; // Πωλήσεις ανά προϊόν

    public FinancialData(String month, float revenue, float expenses, float profitLoss, float revenueGrowth, float expensesGrowth, int customerCount, float salesPerProduct) {
        this.month = month;
        this.revenue = revenue;
        this.expenses = expenses;
        this.profitLoss = profitLoss;
        this.revenueGrowth = revenueGrowth;
        this.expensesGrowth = expensesGrowth;
        this.customerCount = customerCount;
        this.salesPerProduct = salesPerProduct;
    }

    public String getMonth() {
        return month;
    }

    public float getRevenue() {
        return revenue;
    }

    public float getExpenses() {
        return expenses;
    }

    public float getProfitLoss() {
        return profitLoss;
    }

    public float getRevenueGrowth() {
        return revenueGrowth;
    }

    public float getExpensesGrowth() {
        return expensesGrowth;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public float getSalesPerProduct() {
        return salesPerProduct;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public void setExpenses(float expenses) {
        this.expenses = expenses;
    }

    public void setProfitLoss(float profitLoss) {
        this.profitLoss = profitLoss;
    }

    public void setRevenueGrowth(float revenueGrowth) {
        this.revenueGrowth = revenueGrowth;
    }

    public void setExpensesGrowth(float expensesGrowth) {
        this.expensesGrowth = expensesGrowth;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public void setSalesPerProduct(float salesPerProduct) {
        this.salesPerProduct = salesPerProduct;
    }
}


