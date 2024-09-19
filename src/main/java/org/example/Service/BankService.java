package org.example.Service;

import org.example.database.database;

import java.sql.SQLException;
import java.util.Scanner;

public class BankService {

    private double AccountBalance;
    private int PIN;

    public void displayAccountOptions() {
        System.out.println("Select Account Type:");
        System.out.println("1. Current Account");
        System.out.println("2. Saving Account");
        System.out.println(" ----------*----------*----------*----------*----------");
    }

    public void displayMenu() {
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Change PIN");
        System.out.println("5. Mini Statement");
        System.out.println("6. Exit");
        System.out.println(" ----------*----------*----------*----------*----------");
    }

    public double deposit(int amount, database db, long accountNumber) throws SQLException {
        if (amount > 0) {
            AccountBalance = db.getAccountBalance(accountNumber); // Get current balance
            AccountBalance += amount; // Add deposit amount
            System.out.println("Amount Deposited: " + amount);
            System.out.println("Total Balance is: " + AccountBalance);
            return AccountBalance;
        } else {
            System.out.println("Invalid Deposit Amount.");
        }
        System.out.println(" ----------*----------*----------*----------*----------");
        return 0.0;
    }

    public double withdraw(database db, long accountNumber) throws SQLException{
        System.out.println("1. Rs 500");
        System.out.println("2. Rs 2000");
        System.out.println("3. Rs 5000");
        System.out.println("4. Rs 10000");
        System.out.println("5. Others");

        System.out.println("Enter any one amount from above to withdraw:");
        int option = new Scanner(System.in).nextInt();

        switch (option) {
            case 1:
                option = 500;
                AccountBalance = db.getAccountBalance(accountNumber); // Get current balance
                AccountBalance = AccountBalance - option;
                System.out.println("Amount Withdrawn: " + option);
                break;
            case 2:
                option = 2000;
                AccountBalance = db.getAccountBalance(accountNumber);
                AccountBalance = AccountBalance - option;
                System.out.println("Amount Withdrawn: " + option);
                break;
            case 3:
                option = 5000;
                AccountBalance = db.getAccountBalance(accountNumber);
                AccountBalance = AccountBalance - option;
                System.out.println("Amount Withdrawn: " + option);
                break;
            case 4:
                option = 10000;
                AccountBalance = db.getAccountBalance(accountNumber);
                AccountBalance = AccountBalance - option;
                System.out.println("Amount Withdrawn: " + option);
                break;
            case 5:
                System.out.println("Enter the Amount You want to Withdraw:");
                option = new Scanner(System.in).nextInt();
                AccountBalance = db.getAccountBalance(accountNumber);
                AccountBalance = AccountBalance - option;
                System.out.println("Amount Withdrawn: " + option);
                break;
            default:
                System.out.println("Invalid Withdraw Amount.");
        }

        System.out.println("Total Balance is: " + AccountBalance);
        System.out.println(" ----------*----------*----------*----------*----------");
        return AccountBalance ;
    }

    public double checkBalance(database db, long accountNumber)  {
        try{
            System.out.println(" Fetching balance for Account Number : "+ accountNumber);
            double currentBalance = db.getAccountBalance(accountNumber);
            System.out.println("Your current balance is: " + currentBalance);
            System.out.println(" ----------*----------*----------*----------*----------");
            return currentBalance ;
           }catch (SQLException e){
            System.out.println(" Error retriveing balance : " + e.getMessage());
            return 0.0;
        }

    }

    public int changePIN(Scanner input, database db) throws SQLException {
        System.out.print("Enter new PIN: ");
        int newPIN = input.nextInt();

        while (!db.isUniquePIN(newPIN)) {
            System.out.println("PIN already exists. Please choose another PIN.");
            System.out.print("Enter Another PIN: ");
            newPIN = input.nextInt();
        }

        setPIN(newPIN);
//        System.out.println("PIN changed successfully.");
//        System.out.println(" ----------*----------*----------*----------*----------");
        return newPIN;
    }

    public void miniStatement() {
        System.out.println("Mini Statement not implemented.");
        // Placeholder for future implementation
    }

    public void setPIN(int pin) {
        this.PIN = pin;
    }

    public double getAccountBalance() {
        return AccountBalance;
    }
}
