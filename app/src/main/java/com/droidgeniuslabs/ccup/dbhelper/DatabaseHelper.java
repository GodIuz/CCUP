package com.droidgeniuslabs.ccup.dbhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;
import com.droidgeniuslabs.ccup.ui.finacialdata.FinancialData;
import com.droidgeniuslabs.ccup.ui.finacialdata.FinancialDataViewModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlserver://192.168.1.28:1433:<PORT>;databaseName=CCUP_DB";
    private static final String USERNAME = "ccup_db_login";
    private static final String PASSWORD = "xarikos1";
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    public static boolean checkDatabaseConnection() {
        boolean result = DatabaseHelper.isConnectedToInternet(context);
        if(result) {
            Connection connection = null;
            try {
                // Establishing a connection
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                return connection != null; // If connection is not null, we are connected
            } catch (SQLException e) {
                e.printStackTrace(); // Print any SQL exceptions
                return false; // Connection failed
            } finally {
                // Close the connection if it's not null
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean loginUser(String email, String password,boolean rememberMe) {
        boolean isConnected = DatabaseHelper.checkDatabaseConnection();
        if (isConnected) {
            Toast.makeText(context, "Connected to the SQL Server database!", Toast.LENGTH_SHORT).show();
            Connection connection = null;
            try {
                // Establish connection to the database
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                // Prepare SQL query to check user credentials
                String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password); // You may want to hash the password

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // User authenticated successfully
                    if (rememberMe) {
                        saveUserCredentials(email, context);
                    }
                    return true; // Login successful
                } else {
                    return false; // Invalid credentials
                }
            } catch (Exception e) {
                Log.e("DatabaseError", "Error during login", e);
                return false; // Error during connection
            } finally {
                // Close the connection
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        Log.e("DatabaseError", "Error closing connection", e);
                    }
                }
            }
        } else {
            Toast.makeText(context, "Failed to connect to the SQL Server database.", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public static String getSavedUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("saved_username", null); // Return null if not found
    }

    public static boolean isRememberMeChecked(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("remember_me", false);
    }

    private static void saveUserCredentials(String username, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("saved_username", username);
        editor.putBoolean("remember_me", true);
        editor.apply(); // Save changes
    }

    public static boolean registerUser(String firstname, String lastname, String email, String password) {
        Connection connection = null;
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Check if the username already exists
            String checkQuery = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, email);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                // Username already exists
                return false; // Registration failed
            }

            // Insert new user into the database
            String insertQuery = "INSERT INTO Users (username, password) VALUES (?, ?,?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, firstname);
            insertStatement.setString(2, lastname);
            insertStatement.setString(3, email);
            insertStatement.setString(4, password);
            insertStatement.executeUpdate();

            return true; // Registration successful
        } catch (Exception e) {
            Log.e("DatabaseError", "Error during registration", e);
            return false; // Error during connection
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    Log.e("DatabaseError", "Error closing connection", e);
                }
            }
        }
    }

    public void storeFinancialData(FinancialDataViewModel financialDataViewModel) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Prepare the SQL insert query
            String query = "INSERT INTO FinancialData (user_id, month, revenue, expenses, profit_loss, revenue_growth, expenses_growth, customer_count, sales_per_product) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, financialDataViewModel.getEditTextMonth().getValue());
            preparedStatement.setFloat(2, financialDataViewModel.getEditTextRevenue().getValue().floatValue());
            preparedStatement.setFloat(3, financialDataViewModel.getEditTextExpenses().getValue().floatValue());
            preparedStatement.setFloat(4, financialDataViewModel.getEditTextProfitLoss().getValue().floatValue());
            preparedStatement.setFloat(5, financialDataViewModel.getEditTextRevenueGrowth().getValue().floatValue());
            preparedStatement.setFloat(6, financialDataViewModel.getEditTextNumberExpensesGrowth().getValue().floatValue());
            preparedStatement.setInt(7, financialDataViewModel.getEditTextCustomerCount().getValue().intValue());
            preparedStatement.setFloat(8, financialDataViewModel.getEditTextSalesPerProduct().getValue().floatValue());

            // Execute the insert statement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
