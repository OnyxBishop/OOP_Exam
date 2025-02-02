package model;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date;
    private double amount;
    private TransactionType type;
    private String category;

    public Transaction() {
        date = LocalDateTime.now();
    }

    //region getters
    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getTime() {
        return date;
    }

    //endregion

    //region setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    //endregion
}