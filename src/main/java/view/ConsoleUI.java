package view;

import model.CategorySummary;
import model.Transaction;
import model.TransactionType;
import model.UserState;

import java.util.List;
import java.util.Map;

public class ConsoleUI {
    public void showWelcome(String username) {
        System.out.println("Добро пожаловать, " + username + "!");
    }

    public void showError(String message) {
        System.out.println("Ошибка: " + message);
    }

    public void updateBalance(double balance) {
        System.out.printf("Баланс обновлен: %.2f\n", balance);
    }

    public void showSummary(Map<String, List<CategorySummary>> summary) {
        summary.forEach((category, summaries) -> {
            summaries.forEach(s -> {
                String typeLabel = s.getType() == TransactionType.Income ? "+" : "-";
                System.out.printf(
                        "%s: %.2f руб. кол-во %d %s\n",
                        category, s.getTotalAmount(), s.getCount(), typeLabel
                );
            });
        });
    }

    public void showTransaction(Transaction transaction) {
        System.out.println("Добавлена операция: " + transaction.getCategory());
    }

    public void showMessage(String value) {
        System.out.println(value);
    }

    public void showPromptWith(String value) {
        System.out.println("Введите " + value + ":");
    }

    public void showCommands(UserState currentState) {
        System.out.println("Доступные команды: ");
        String commands = currentState == UserState.Unauthorized ? "Логин, Регистрация, Выход" : "Сводка, Транзакция";
        System.out.println(commands);
    }
}