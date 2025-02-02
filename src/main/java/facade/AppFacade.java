package facade;

import events.Event;
import events.EventType;
import model.Transaction;
import model.User;
import service.AuthService;
import service.NotificationService;
import service.TransferService;
import service.WalletService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AppFacade {
    private final AuthService authService;
    private final WalletService walletService;
    private final TransferService transferService;
    private final NotificationService notificationService;
    private final List<Consumer<Event<?>>> subscribers = new ArrayList<>();
    private User currentUser;

    public AppFacade() {
        this.walletService = new WalletService(this::onWalletEvent);
        this.authService = new AuthService(this::onAuthEvent, this.walletService);
        this.transferService = new TransferService();
        this.notificationService = new NotificationService();
    }

    public void subscribe(Consumer<Event<?>> listener) {
        subscribers.add(listener);
    }

    public boolean register(String username, String password) {
        if(!authService.register(username, password))
            return false;

        currentUser = authService.tryGetUser(username);
        return true;
    }

    public boolean login(String username, String password) {
        if(!authService.login(username, password))
            return false;

        currentUser = authService.tryGetUser(username);
        return true;
    }

    public void addTransaction(Transaction transaction) {
        try {
            walletService.addTransaction(currentUser.getWalletId(), transaction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showSummary() throws Exception {
        var user = authService.tryGetUser(currentUser.getLogin());

        if(user == null)
            throw new Exception();

        var walletId = user.getWalletId();
        var summary = walletService.getCategorySummaries(walletId);

        notifySubscribers(new Event<>(EventType.SummaryCreated, summary));
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void onAuthEvent(Event<?> event) {
        notifySubscribers(event);
    }

    private void onWalletEvent(Event<?> event) {
        notifySubscribers(event);
        //notificationService.alertIfLowBalance(walletService.getBalance());
    }

    private void notifySubscribers(Event<?> event) {
        subscribers.forEach(listener -> listener.accept(event));
    }
}
