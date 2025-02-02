package view;

import model.Transaction;
import model.Wallet;

import java.util.List;

public class ReportGenerator {
    public void showBalance(Wallet wallet){
        System.out.printf("Баланс: %.2f\n", wallet.getBalance());
    }

    public void showTransactions(List<Transaction> transactions){
        transactions.forEach(System.out::println);
    }
}
