package org.example.Contoller;

import org.example.Entity.Bank_Account;
import org.example.Service.BankService;
import org.example.database.database;

import java.sql.SQLException;
import java.util.Scanner;

public class BankController {

    public static void main(String[] args) throws SQLException {

        database db = new database();
        Scanner input = new Scanner(System.in);
        double accountBalance = 500 ;

        System.out.println("WELCOME TO GTasterix BANK!");
        System.out.println("1. Existing User");
        System.out.println("2. New User");

        int option = input.nextInt();
        BankService ba=new BankService();
        Bank_Account ba1 = new Bank_Account();
        long AcNo;

        if (option == 1) {
            // Existing user code
            int i;
            System.out.println("Enter your PIN:");
            for (i = 1; i <= 3; i++) {
                int PIN = input.nextInt();
                Bank_Account bankAccount = db.verifyPIN(PIN);
                if (bankAccount == null) {
                    System.out.println("Invalid PIN.");
                    System.out.println("Enter PIN again:");
                } else {
                    ba1 = bankAccount;
                    break;
                }
            }

            if ((i != 4) && (ba != null)) {
                System.out.println("Login Successfully!");

                System.out.println("Hello " + ba1.getAccountHolderName());
                AcNo = ba1.getAccountNumber();

                System.out.println("Account Number: " + AcNo);
                ba.displayMenu();

                int menuOption;
                System.out.println("Enter your choice:");
                menuOption = input.nextInt();

                switch (menuOption) {

                    case 1:
                        System.out.println("Enter the Amount you want to Deposit:");
                        int depositAmount = input.nextInt();
                        double updatedBalance = ba.deposit(depositAmount, db, AcNo); // Pass database and account number
                        db.updateAccountBalance( AcNo, updatedBalance); // Update balance in the database
                        break;

                    case 2:
                        double updateBalance = ba.withdraw(db,AcNo);
                        db.updateAccountBalance(AcNo, updateBalance);
                        break;

                    case 3:
                       double currentBalance = ba.checkBalance(db,AcNo);
                       break;

                    case 4:
                        int newPin = ba.changePIN(input, db);
                        db.updatePIN(AcNo, newPin);
                        break;

                    case 5:
                        ba.miniStatement();
                        break;

                    case 6:
                        System.out.println("Thank you for using our service.");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else {
                System.out.println("You have tried too many incorrect attempts.");
                System.out.println("Your account has been blocked for 24 hours.");
            }

        } else if (option == 2) {
            // New user code
            long accountNumber;

            while (true) {
                System.out.print("Enter the Account Number: ");
                accountNumber = input.nextLong();
                if (isValidAccountNumber(accountNumber)) {
                    if (db.isUniqueAccountNumber(accountNumber)) {
                        break;
                    } else {
                        System.out.println("Account Number already exists. Please enter another Account Number.");
                    }
                } else {
                    System.out.println("Invalid Account Number. Please enter a 12-digit Account Number.");
                }
            }

            System.out.print("Enter the Name of Account Holder: ");
            String accountHolderName = input.next();

            System.out.print("Generate Your Own PIN: ");
            int pin = input.nextInt();

            while (!db.isUniquePIN(pin)) {
                System.out.println("PIN already exists. Please choose another PIN.");
                System.out.print("Enter Another PIN: ");
                pin = input.nextInt();
            }

            System.out.print("Enter Account Type (Current/Saving): ");
            String accountType = input.next();

            Bank_Account bankAccount = new Bank_Account(accountNumber, accountHolderName, accountBalance, pin, accountType);
            db.insertAccountData(accountNumber, accountHolderName, accountBalance, pin, accountType);

            System.out.println("Account created successfully! You can now log in as an existing user.");
        } else {
            System.out.println("Invalid option selected.");
        }

        input.close();
    }

    private static boolean isValidAccountNumber(long accountNumber) {
        String accountNumberStr = String.valueOf(accountNumber);
        return accountNumberStr.length() == 12;
    }
}
