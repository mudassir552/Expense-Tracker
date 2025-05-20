package org.example.ExpenseTracker.TransactionManager;

import org.example.ExpenseTracker.FileHandler.TransactionsFileHandler;
import org.example.ExpenseTracker.Transactions.TransactionCategory;
import org.example.ExpenseTracker.Transactions.TransactionType;
import org.example.ExpenseTracker.Transactions.Transactions;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.example.ExpenseTracker.FileHandler.TransactionsFileHandler.TRANSACTION_DIR;

public class TransactionManager {

    private List<Transactions> transactions = new ArrayList<>();


    public void addTransaction(List<Transactions> transaction) {

        for(Transactions currentTransaction: transaction){


            transactions.add(currentTransaction);

        }

    }

    // View monthly summary
    public void viewMonthlySummary(int year, int month) {
        double totalIncome = 0;
        double totalExpense = 0;

        System.out.println("Monthly Summary for " + month + "/" + year + ":");
        for (Transactions transaction : transactions) {
            if (transaction.getDate().getYear() == year && transaction.getDate().getMonthValue() == month) {
                if (transaction.getCategory()== TransactionCategory.INCOME) {
                    totalIncome += transaction.getAmount();
                } else if (transaction.getCategory()==TransactionCategory.EXPENSE) {
                    totalExpense += transaction.getAmount();
                }
            }
        }
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Net Balance: " + (totalIncome - totalExpense));
    }

    public static List<Transactions> loadAllFromCategory(TransactionCategory category) throws IOException {
        List<Transactions> transactions = new ArrayList<>();
        Path categoryDir = Paths.get(TRANSACTION_DIR, category.name());

        if (!Files.exists(categoryDir)) {
            return transactions;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(categoryDir, "*.txt")) {
            TransactionsFileHandler transactionsFileHandler= new TransactionsFileHandler();
            for (Path file : stream) {
                transactions.addAll(transactionsFileHandler.loadAllFromCategory(category, file.getFileName().toString()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

}
