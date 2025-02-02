package service;

public class NotificationService {
    private final int lowBalance = 100;

    public void alertIfLowBalance(double amount) {
        if (amount < lowBalance)
            System.out.println("Низкий баланс.");
    }
}