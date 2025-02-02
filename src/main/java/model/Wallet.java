package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet {
    private UUID id;
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();

    public Wallet() {
        id = UUID.randomUUID();
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public boolean transfer(Wallet target, double amount) {
        if (this.balance < amount)
            return false;

        this.balance -= amount;
        target.balance += amount;
        return true;
    }

    public double getBalance() {
        return this.balance;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public UUID getId() {
        return this.id;
    }
}