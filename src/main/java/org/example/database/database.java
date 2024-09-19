package org.example.database;

import org.example.Entity.Bank_Account;

import java.sql.*;

public class database {
    private static int PIN;
    Bank_Account ba = new Bank_Account();
    public static String URL = "jdbc:mysql://localhost:3306/";
    public static String username = "root";
    public static String password = "Rituja@123";

    public database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL server without specifying the database
            try (Connection abc = DriverManager.getConnection(URL, username, password);
                 Statement st = abc.createStatement()) {

                // Create the database if it doesn't exist
                String createDBQuery = "CREATE DATABASE IF NOT EXISTS ATM";
                st.execute(createDBQuery);

                System.out.println("Database 'ATM' Created or Already Exists.");
                createTable();

                // Now connect to the 'ATM' database
                try (Connection abc2 = DriverManager.getConnection(URL + "ATM", username, password);
                     Statement st2 = abc2.createStatement()) {
                    createTable();
                } catch (SQLException e) {
                    System.err.println("SQL Exception: " + e.getMessage());
                    throw new RuntimeException(e);
                }

            } catch (SQLException e) {
                System.err.println("SQL Exception: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
    }

    public void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Bank_Account (" +
                "AccountNumber DOUBLE PRIMARY KEY, " +
                "AccountHolderName VARCHAR(255), " +
                "AccountBalance DOUBLE, " +
                "PIN INT UNIQUE," +
                "AccountType VARCHAR (255) )";

        try (Connection abc = DriverManager.getConnection(URL + "ATM", username, password);
             Statement st = abc.createStatement()) {

            st.execute(createTableQuery);
            System.out.println("Table 'Bank_Account' created or already exists.");

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }


    // Method to insert account data into the database
    public void insertAccountData(long AccountNumber, String AccountHolderName, double AccountBalance, int PIN, String accountType) {

        String URL = "jdbc:mysql://localhost:3306/ATM";

        String username = "root";
        String password = "Rituja@123";

        String insertQuery = "INSERT INTO Bank_Account (AccountNumber, AccountHolderName, AccountBalance, PIN,AccountType) VALUES (?, ?, ?,?,?)";

        System.out.println(" Account Number is : " + AccountNumber);
        try (Connection abc = DriverManager.getConnection(URL, username, password);
             PreparedStatement ps = abc.prepareStatement(insertQuery)) {

            // Setting parameters
            ps.setDouble(1, AccountNumber);
            ps.setString(2, AccountHolderName);
            ps.setDouble(3, AccountBalance);
            ps.setInt(4, PIN);
            ps.setString(5, accountType);

            // Execute the INSERT query
            ps.executeUpdate();
//            System.out.println("Account data inserted successfully.");

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }



    public boolean isUniquePIN(int PIN) throws SQLException {
        String countQuery = " SELECT COUNT(*) FROM BANK_ACCOUNT WHERE PIN = ?   ";

        try (Connection abc = DriverManager.getConnection(URL + "ATM", username, password);
             PreparedStatement ps = abc.prepareStatement(countQuery)) {
            ps.setInt(1, PIN);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) == 0; // Return True if PIN is unique

        }
    }

    public boolean addNewAccount() throws SQLException {
        try (Connection abc = DriverManager.getConnection(URL + "ATM", username, password)) {
            if (isUniquePIN(this.PIN)) {
                System.out.println(" PIN already exists. Please choose a different Password");
                return false;
            }
        }
        return true;
    }

    public boolean isUniqueAccountNumber(double accountNumber) throws SQLException {
        String query3 = " SELECT COUNT(*) FROM BANK_ACCOUNT WHERE AccountNumber = ?";

        try (Connection conn = DriverManager.getConnection(URL + "ATM", username, password);
             PreparedStatement ps = conn.prepareStatement(query3)) {

            ps.setDouble(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1) == 0;
        }

    }

    public Bank_Account verifyPIN(int PIN) throws SQLException {
        String query1 = "SELECT * FROM Bank_Account WHERE PIN = ?";

        try (Connection abc = DriverManager.getConnection(URL + "ATM", username, password);
             PreparedStatement ps = abc.prepareStatement(query1)) {

            ps.setInt(1, PIN);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Long accountNumber = rs.getLong("AccountNumber"); // Retrieve account number
                String accountHolderName = rs.getString("AccountHolderName");
                double accountBalance = rs.getDouble("AccountBalance");
                String accountType = rs.getString("AccountType");

                // Return the Bank_Account object with the correct account number
                return new Bank_Account((Long) accountNumber, accountHolderName, accountBalance, PIN, accountType);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }

        return null; // Return null if account not found or PIN is incorrect
    }


    public void updatePIN(double accountNumber, int newPIN) {
        String updateQuery = "UPDATE Bank_Account SET PIN = ? WHERE AccountNumber = ?";
        try (Connection conn = DriverManager.getConnection(URL + "ATM", username, password);
             PreparedStatement ps = conn.prepareStatement(updateQuery)) {

            // Set the parameters for the query
            ps.setInt(1, newPIN);
            ps.setDouble(2, accountNumber);

            // Execute the update
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("PIN updated successfully.");
            } else {
                System.out.println("Account not found. PIN update failed.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }

    public void updateAccountBalance(long accountNumber, double newBalance) {
        String updateQuery = "UPDATE Bank_Account SET AccountBalance = ? WHERE AccountNumber = ?";
        try (Connection conn = DriverManager.getConnection(URL + "ATM", username, password);
             PreparedStatement ps = conn.prepareStatement(updateQuery)) {

            ps.setDouble(1, newBalance);
            ps.setLong(2, accountNumber);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Account Balance updated successfully.");
            } else {
                System.out.println("Account not found. Account Balance update failed.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }


    public double getAccountBalance(long accountNumber) throws SQLException {
        String query = "SELECT AccountBalance FROM Bank_Account WHERE AccountNumber = ?";
        try (Connection conn = DriverManager.getConnection(URL + "ATM", username, password);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, accountNumber);  // Ensure accountNumber is set correctly

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("AccountBalance");  // Retrieve balance
               System.out.println("Previous balance from database: " + balance);
                return balance;
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
        return 0.0; // Return 0.0 if account not found or error occurred
    }

}



















