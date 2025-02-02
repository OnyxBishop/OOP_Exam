package utils;

public class CommandProcessor {
    public InputType handleInput(String command) {
        switch (command) {
            case "логин" -> {
                return InputType.Auth;
            }
            case "регистрация" -> {
                return InputType.Register;
            }
            case "транзакция" -> {
                return InputType.MakeTransaction;
            }
            case "сводка" -> {
                return InputType.Summary;
            }
            case "баланс" -> {
                return InputType.CheckBalance;
            }
            case "выход" -> {
                return InputType.Exit;
            }
            default -> {
                return InputType.Error;
            }
        }
    }
}
