package service;

import events.Event;
import events.EventType;
import model.Transaction;
import model.TransactionType;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private final Consumer<Event<?>> eventNotifier;
    private final WalletService walletService;
    private Map<String, User> users = new HashMap<>();

    public AuthService(Consumer<Event<?>> eventNotifier, WalletService walletService) {
        this.eventNotifier = eventNotifier;
        this.walletService = walletService;
        registerTestUser();
    }

    public boolean register(String userName, String password) {
        if (users.containsKey(userName)) {
            eventNotifier.accept(new Event<>(EventType.AuthFailed, "Такой пользователь уже существует."));
            return false;
        }

        var wallet = walletService.createWallet();
        var user = new User(userName, getHashCode(password), wallet.getId());
        users.put(userName, user);
        eventNotifier.accept(new Event<>(EventType.AuthSuccess, userName));
        return true;
    }

    public boolean login(String userName, String password) {
        var user = users.get(userName);

        if (user == null || !checkPassword(password, user.getPasswordHash())) {
            eventNotifier.accept(new Event<>(
                    EventType.AuthFailed,
                    "Неправильно набран логин или пароль. Попробуйте еще раз"));

            return false;
        }

        eventNotifier.accept(new Event<>(EventType.AuthSuccess, userName));
        return true;
    }

    public User tryGetUser(String userName) {
        if(!users.containsKey(userName))
            return null;

        return users.get(userName);
    }

    private void registerTestUser() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("test_user.properties")) {
            Properties props = new Properties();
            props.load(input);

            var username = props.getProperty("username");
            var passwordHash = props.getProperty("password_hash");
            var testWallet = walletService.createWallet();

            var testTransaction = new Transaction();
            testTransaction.setType(TransactionType.Expense);
            testTransaction.setCategory("Продукты");
            testTransaction.setAmount(1000);
            testWallet.add(testTransaction);

            if (!users.containsKey(username)) {
                users.put(username, new User(username, passwordHash, testWallet.getId()));
            }
        } catch (IOException e) {
            System.err.println("Ошибка загрузки тестового пользователя: " + e.getMessage());
        }
    }

    private String getHashCode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}