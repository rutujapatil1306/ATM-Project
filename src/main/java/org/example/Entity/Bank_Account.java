package org.example.Entity;

//package OOPs.Encapsulated;
//import OOPs.Database.database;


public class  Bank_Account{

    private long AccountNumber;
    private String AccountHolderName;
    private double AccountBalance;
    private int PIN;
    private String accountType;

    // Constructor
    public Bank_Account(long accountNumber, String accountHolderName, double accountBalance, int PIN, String accountType) {
        this.AccountNumber = accountNumber;
        this.AccountHolderName = accountHolderName;
        this.AccountBalance = accountBalance;
        this.PIN = PIN;
        this.accountType = accountType;
    }
    public Bank_Account() {

    }

    public int getPIN() {
        return PIN;
    }

    public void setPIN(int PIN) {
        this.PIN = PIN;
    }

    public long getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return AccountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        AccountHolderName = accountHolderName;
    }

    public double getAccountBalance() {
        return AccountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        AccountBalance = accountBalance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}

