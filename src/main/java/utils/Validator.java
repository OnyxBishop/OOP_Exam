package utils;

public class Validator {
    public static boolean isValidNumber(double amount) {
        return amount > 0;
    }

    public static boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
    }
}