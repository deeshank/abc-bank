package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private double availableBalance;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.availableBalance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            this.availableBalance += amount;
        }
    }

    public void deposit(double amount, String dt) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount,DateProvider.getInstance().toDate(dt)));
            this.availableBalance += amount;
        }
    }


    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            this.setAvailableBalance(this.getAvailableBalance()-amount);
        }
    }

    public double interestEarned() {
        double amount = this.getAvailableBalance();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
            case MAXI_SAVINGS:
                Date endDate = DateProvider.getInstance().now();
                Date startDate = DateProvider.getInstance().addDays(endDate,-10);
                if (this.checkAnyWithdrawlTransaction(startDate,endDate))
                    return amount * 0.001;
                else
                    return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    public double dailyInterestEarned() {
        double amount = this.getAvailableBalance();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return Account.dailyInterest(amount, 0.1, 365);
                else
                    return Account.dailyInterest(1000, 0.1, 365) + Account.dailyInterest(amount - 1000, 0.2, 365);
            case MAXI_SAVINGS:
                Date endDate = DateProvider.getInstance().now();
                Date startDate = DateProvider.getInstance().addDays(endDate,-10);
                if (this.checkAnyWithdrawlTransaction(startDate,endDate))
                    return Account.dailyInterest(amount, 0.1, 365);
                else
                    return Account.dailyInterest(amount, 5, 365);
            default:
                return Account.dailyInterest(amount, 0.1, 365);
        }
    }

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.getAmount();
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public boolean checkAnyWithdrawlTransaction(Date startDate, Date endDate) {
        for(Transaction t:transactions){
            if (DateProvider.getInstance().isWithinRange(t.getTransactionDate(),startDate,endDate)) {
                if(t.getAmount()<0)
                    return true;
            }
        }
        return false;
    }

    public List<Transaction> getAllTransactions(Date startDate, Date endDate){
        List<Transaction> _transactions = new ArrayList<Transaction>();
        for(Transaction t: transactions){
            if (DateProvider.getInstance().isWithinRange(t.getTransactionDate(),startDate,endDate))
                _transactions.add(t);
        }
        return _transactions;
    }

    public static double dailyInterest(double Principal, double rate, int days) {
        double Interest = Principal;
        Interest *= 1 + ((rate/100.0)/365.0)*days;
        return (double) Math.round((Interest-Principal) * 100) / 100;
    }
}
