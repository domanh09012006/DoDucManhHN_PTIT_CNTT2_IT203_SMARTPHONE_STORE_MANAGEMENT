package util;

import java.util.Scanner;

public class InputUtil {

    public static String getString(Scanner scanner, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (!ValidationUtil.isEmpty(input)) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }

    public static String getEmail(Scanner scanner, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (ValidationUtil.isValidEmail(input)) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }

    public static String getPhone(Scanner scanner, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (ValidationUtil.isValidPhone(input)) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }

    public static String getPassword(Scanner scanner, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (ValidationUtil.isValidPassword(input)) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }

    public static int getInt(Scanner scanner, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static double getDouble(Scanner scanner, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }
}