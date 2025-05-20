package org.example.ExpenseTracker;
import org.example.ExpenseTracker.FileHandler.TransactionsFileHandler;
import org.example.ExpenseTracker.TransactionManager.TransactionManager;
import org.example.ExpenseTracker.Transactions.TransactionCategory;
import org.example.ExpenseTracker.Transactions.TransactionType;
import org.example.ExpenseTracker.Transactions.Transactions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);


       System.out.println("Hiii!, Welcome to the Expense Tracker app");
        System.out.println("Choose a Transaction Category: INCOME or EXPENSE?");
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.isEmpty()) {
            System.out.println("Transaction category cannot be empty.");
            return;
        }

        TransactionCategory category;
        try {
            category = TransactionCategory.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid transaction type.");
            return;
        }

        System.out.println("Select sub-category:");
        TransactionType type;

        if (category == TransactionCategory.INCOME) {
            System.out.println("Options: SALARY, BUSINESS");
        } else if (category == TransactionCategory.EXPENSE) {
            System.out.println("Options: FOOD, RENT, TRAVEL");
        }

        String subCategoryInput = scanner.nextLine().trim().toUpperCase();

        try {
            type = TransactionType.valueOf(subCategoryInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid sub-category.");
            return;
        }

        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();

        LocalDate date = LocalDate.now();

        Transactions transaction = new Transactions( type,category, amount, date);
        String fname=category.name();


        TransactionsFileHandler.saveToFile(List.of(transaction), fname+"_123" );




        TransactionManager transactionManager = new TransactionManager();
        transactionManager.addTransaction(TransactionsFileHandler.loadAllFromCategory(TransactionCategory.INCOME, "INCOME_123"));
        transactionManager.addTransaction(TransactionsFileHandler.loadAllFromCategory(TransactionCategory.EXPENSE,"EXPENSE_123"));


        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();


        System.out.println("Transaction recorded:");
        System.out.println("Type: " + transaction.getCategory());
        System.out.println("Sub-category: " + transaction.getType());
        System.out.println("Amount: " + transaction.getAmount());
        System.out.println("Date: " + transaction.getDate());

    System.out.println("Loading... Monthly Transactions");
        transactionManager.viewMonthlySummary(year, month);
        scanner.close();

    }
    }
