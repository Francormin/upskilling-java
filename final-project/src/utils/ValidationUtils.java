package utils;

public class ValidationUtils {
    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isShort(String input) {
        return input.length() < 3;
    }

    public static boolean isValidExpenseCategoryName(String expenseCategoryName) {
        return expenseCategoryName.matches("[a-zA-Z]+");
    }

    public static int validateInteger(String input) throws NumberFormatException {
        return Integer.parseInt(input);
    }
}