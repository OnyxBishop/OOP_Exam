package utils;

import java.util.Scanner;

public class InputReader {
    private final Scanner scanner = new Scanner(System.in);

    public String readCommand(){
        return scanner.nextLine().trim().toLowerCase();
    }

    public String readInput() {
        return scanner.nextLine();
    }

    public double readPositiveDouble(String prompt) {
        while (true) {
            double value = readDouble(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Ошибка: Число должно быть больше нуля!");
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите корректное число!");
            }
        }
    }
}
