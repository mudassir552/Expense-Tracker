package org.example.ExpenseTracker.Transactions;

import java.time.LocalDate;

public class Transactions {

    private TransactionType type; // INCOME or EXPENSE
    private TransactionCategory category;
    private double amount;
    private LocalDate date;

    public Transactions(TransactionType type, TransactionCategory category, double amount, LocalDate date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionCategory getCategory() {;
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
