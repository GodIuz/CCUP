package com.droidgeniuslabs.ccup.dbhelper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlserver://192.168.1.28:1433;databaseName=CCUP_DB;user=ccup_db_login;password=xarikos1;encrypt=false;trustServerCertificate=true";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }



    public boolean isFirstLogin(String email) throws SQLException {
        String query = "SELECT IsFirstLogin FROM Users WHERE Email = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getBoolean("IsFirstLogin");
        }
    }

    public boolean registerUser(String firstname,String lastname,String email ,String password) throws SQLException {
        String query = "INSERT INTO Users (Firstame,Lastname, Email, Password, Dob) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstname);
            statement.setString(2,lastname);
            statement.setString(3,email);
            statement.setString(4,password);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean loginUser(String email, String password) throws SQLException {
        String query = "SELECT PasswordHash FROM Users WHERE Username = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
        }
        return false;
    }

    public void updateRememberMe(String email, boolean rememberMe) throws SQLException {
        String query = "UPDATE Users SET RememberMe = ? WHERE Username = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, rememberMe);
            statement.setString(2, email);
            statement.executeUpdate();
        }
    }

    public String hashPassword(String password) {
        return String.valueOf(password.hashCode());  // Placeholder for real hashing
    }

    public boolean checkPassword(String inputPassword, String storedPasswordHash) {
        return storedPasswordHash.equals(String.valueOf(inputPassword.hashCode()));
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }
}
