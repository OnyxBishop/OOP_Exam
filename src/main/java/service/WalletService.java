package service;

import events.Event;
import events.EventType;
import model.CategorySummary;
import model.Transaction;
import model.Wallet;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class WalletService {
    private final Map<UUID, Wallet> wallets = new HashMap<>();
    private final Consumer<Event<?>> eventNotifier;
    private final List<Transaction> transactions = new ArrayList<>();

    public WalletService(Consumer<Event<?>> eventNotifier) {
        this.eventNotifier = eventNotifier;
    }

    public Wallet createWallet() {
        var wallet = new Wallet();
        wallets.put(wallet.getId(), wallet);
        return wallet;
    }

    public void addTransaction(UUID walletId, Transaction item) throws Exception {
        var wallet = wallets.get(walletId);

        if (wallet == null)
            throw new Exception();

        wallet.add(item);
        eventNotifier.accept(new Event<>(EventType.TransactionAdded, item));
    }

    public Map<String, List<CategorySummary>> getCategorySummaries(UUID walletId) {
        var wallet = wallets.get(walletId);

        if (wallet == null)
            return null;

        return wallet.getTransactions()
                .stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory, Collectors.collectingAndThen(
                                Collectors.toList(),
                                this::createCategorySummaries)));
    }

    private List<CategorySummary> createCategorySummaries(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getType,
                        Collectors.summarizingDouble(Transaction::getAmount)
                ))
                .entrySet()
                .stream()
                .map(entry -> new CategorySummary(
                        entry.getValue().getSum(),
                        (int) entry.getValue().getCount(),
                        entry.getKey()
                ))
                .toList();
    }
}