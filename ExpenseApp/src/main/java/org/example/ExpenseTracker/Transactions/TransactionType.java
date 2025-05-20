package org.example.ExpenseTracker.Transactions;


public enum TransactionType {
    SALARY("Income"),
    BUSINESS("Income"),
    FREELANCE("Income"),

    // Expense Categories
    FOOD("Expense"),
    RENT("Expense"),
    TRAVEL("Expense"),
    UTILITIES("Expense");

    private String type;


    private TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

