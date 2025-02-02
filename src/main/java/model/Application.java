package model;

import facade.AppFacade;
import utils.CommandProcessor;
import utils.InputReader;
import view.ConsoleUI;

public class Application {
    private final AppFacade facade;
    private final CommandProcessor processor;
    private final InputReader reader;
    private final ConsoleUI ui;
    private boolean isRunning;

    public Application(AppFacade facade, CommandProcessor processor, InputReader reader, ConsoleUI uI) {
        this.facade = facade;
        this.processor = processor;
        this.reader = reader;
        this.ui = uI;
    }

    //TODO: Переделать на машину состояний. И лучше обернуть команды по паттерну.
    public void start() {
        isRunning = true;
        UserState currentState = UserState.Unauthorized;

        while (isRunning) {
            if (currentState == UserState.Unauthorized) {
                ui.showCommands(currentState);
                var inputType = processor.handleInput(reader.readCommand());

                switch (inputType) {
                    case Auth -> {
                        if (facade.login(getAuthData("логин"), getAuthData("пароль"))){
                            currentState = UserState.Authorized;
                        }
                    }
                    case Register -> {
                        if (facade.register(getAuthData("логин"), getAuthData("пароль"))){
                            currentState = UserState.Authorized;
                        }
                    }
                    case Exit -> {
                        isRunning = false;
                    }
                    case Error -> {
                        System.out.println("Неверная команда");
                    }
                }
            } else if (currentState == UserState.Authorized) {
                ui.showCommands(currentState);
                var inputType = processor.handleInput(reader.readCommand());

                switch (inputType) {
                    case MakeTransaction -> {
                        var transaction = new Transaction();
                        transaction.setType(handleTransactionType());
                        transaction.setCategory(handleTransactionCategory());
                        transaction.setAmount(reader.readPositiveDouble("Введите сумму"));
                        facade.addTransaction(transaction);
                    }
                    case Summary -> {
                        try {
                            facade.showSummary();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case CheckBalance -> {
                    }
                    case Exit -> {
                        isRunning = false;
                    }
                    case Error -> {
                        System.out.println("Неверная команда");
                    }
                }
            }
        }

        System.out.println("Всего доброго!");
    }

    private String getAuthData(String type) {
        ui.showPromptWith(type);
        return reader.readInput();
    }

    private double getTransactionAmount() {
        return reader.readPositiveDouble("Введите сумму");
    }

    //region TransactionHandlers
    private TransactionType handleTransactionType() {
        ui.showPromptWith("Введите + или -");
        var input = reader.readInput().trim();

        if (input.equals("-"))
            return TransactionType.Expense;
        else if (input.equals("+"))
            return TransactionType.Income;

        return TransactionType.Error;
    }

    private String handleTransactionCategory() {
        ui.showPromptWith("категорию");
        return reader.readInput();
    }
    //endregion
}