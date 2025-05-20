package org.example.ExpenseTracker.FileHandler;
import org.example.ExpenseTracker.Transactions.TransactionCategory;
import org.example.ExpenseTracker.Transactions.TransactionType;
import org.example.ExpenseTracker.Transactions.Transactions;
import java.io.IOException;
import java.nio.file.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TransactionsFileHandler {

    public static final String TRANSACTION_DIR = Paths.get(
            System.getProperty("user.home"),
            "Documents", "ExpenseTracker", "ExpenseApp", "TransactionFiles"
    ).toString();

    public static void saveToFile(List<Transactions> transactions, String fileName) throws IOException {


        TransactionCategory transactionCategory = transactions.get(0).getCategory();
        Path subDir = Paths.get(TRANSACTION_DIR, transactionCategory.name()); // .../TransactionFiles/INCOME or EXPENSE
        Files.createDirectories(subDir);

        Path filePath = subDir.resolve(fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            for (Transactions t : transactions) {
                writer.write(t.getType().name() + "," + t.getCategory().name() + "," + t.getAmount() + "," + t.getDate() + "\n");

            }
        }

        System.out.println("Saved to: " + filePath);
    }


    public static List<Transactions> loadAllFromCategory(TransactionCategory transactionCategory, String fileName) {

        List<Transactions> transactions = new ArrayList<>();
        Path filePath = Paths.get(TRANSACTION_DIR, transactionCategory.name(), fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 4) continue;

                try {
                    TransactionType type = TransactionType.valueOf(parts[0].trim());
                    TransactionCategory category = TransactionCategory.valueOf(parts[1].trim());
                    double amount = Double.parseDouble(parts[2].trim());
                    LocalDate date = LocalDate.parse(parts[3].trim());

                    transactions.add(new Transactions(type, category, amount, date));
                } catch (Exception e) {
                    System.err.println("Skipping invalid line: " + line);
                }
            }

            return transactions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
