package view;

import events.Event;
import facade.AppFacade;
import model.CategorySummary;
import model.Transaction;

import java.util.List;
import java.util.Map;

public class ConsolePresenter {
    private final AppFacade facade;
    private final ConsoleUI ui;

    public ConsolePresenter(AppFacade facade, ConsoleUI ui) {
        this.facade = facade;
        this.ui = ui;

        this.facade.subscribe(this::handleEvent);
    }

    private void handleEvent(Event<?> event) {
        switch (event.getType()) {
            case AuthSuccess -> ui.showWelcome((String) event.getData());
            case AuthFailed -> ui.showError("Auth failed");
            case SummaryCreated -> ui.showSummary((Map<String, List<CategorySummary>>) event.getData());
            case TransactionAdded -> ui.showTransaction((Transaction) event.getData());
        }
    }
}
