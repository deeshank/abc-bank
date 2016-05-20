package com.abc;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private Map<Integer,Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<Integer, Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Integer account) {
        accounts.put(account,new Account(account));
        return this;
    }

    public Customer openAccount(Account account) {
        accounts.put(account.getAccountType(),account);
        return this;
    }

    public Account getAccount(int accountType) {
        return accounts.get(accountType);
    }

    public String transfer(int from_account, int to_account, double amount) {
        if (accounts.containsKey(from_account) && accounts.containsKey(to_account)) {
            if(amount<=accounts.get(from_account).getAvailableBalance()){
                double to_curr_bal = accounts.get(to_account).getAvailableBalance();
                double from_curr_bal = accounts.get(from_account).getAvailableBalance();
                accounts.get(to_account).setAvailableBalance(to_curr_bal+amount);
                accounts.get(to_account).transactions.add(new Transaction(amount));
                accounts.get(from_account).setAvailableBalance(from_curr_bal-amount);
                accounts.get(from_account).transactions.add(new Transaction(-amount));
                return "Transfer Succeeded";
            }
            else {
                return "Insufficient Balance";
            }
        }
        return "Account Not Found";
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Map.Entry<Integer, Account> entry : accounts.entrySet())
            total += entry.getValue().interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Map.Entry<Integer,Account> a : accounts.entrySet()) {
            statement += "\n" + statementForAccount(a.getValue()) + "\n";
            total += a.getValue().sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
