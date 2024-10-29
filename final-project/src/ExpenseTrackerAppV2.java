import entities.Expense;
import entities.ExpenseCategory;
import exceptions.InvalidExpenseAmountException;
import exceptions.InvalidExpenseDateException;
import exceptions.InvalidExpenseDescriptionException;
import interfaces.ExpenseAmountValidator;
import interfaces.ExpenseCalculator;
import interfaces.ExpenseDateValidator;
import interfaces.ExpenseDescriptionValidator;
import interfaces.impl.ExpenseAmountValidatorImpl;
import interfaces.impl.ExpenseCalculatorImpl;
import interfaces.impl.ExpenseDateValidatorImpl;
import interfaces.impl.ExpenseDescriptionValidatorImpl;
import utils.NotificationUtils;
import utils.ValidationUtils;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ExpenseTrackerAppV2 {
    public static int counter = 1;

    public static void main(String[] args) {
        // Instanciar las clases que implementan interfaces para su posterior uso
        Scanner scanner = new Scanner(System.in);
        ExpenseAmountValidator expenseAmountValidator = new ExpenseAmountValidatorImpl();
        ExpenseDateValidator expenseDateValidator = new ExpenseDateValidatorImpl();
        ExpenseCalculator expenseCalculator = new ExpenseCalculatorImpl();
        ExpenseDescriptionValidator expenseDescriptionValidator = new ExpenseDescriptionValidatorImpl();

        // Cantidad de gastos a cargar en el sistema
        int totalExpensesToEnter = requestTotalExpenses(scanner);

        // Crear un arreglo de gastos al que se agregará, posteriormente, los datos ingresados por el usuario
        Expense[] expenses = new Expense[totalExpensesToEnter];

        // Crear y agregar esa cantidad de gastos (con sus correspondientes datos) al arreglo de gastos creado arriba
        for (int index = 0; index < totalExpensesToEnter; index++) {
            Expense expense = new Expense();
            ExpenseCategory expenseCategory = new ExpenseCategory();

            double expenseAmount = requestExpenseAmount(scanner, expenseAmountValidator, index);
            String expenseCategoryName = requestExpenseCategory(scanner);
            String expenseDate = requestExpenseDate(scanner, expenseDateValidator);
            String expenseDescription = requestExpenseDescription(scanner, expenseDescriptionValidator);

            expense.setId(counter);
            expense.setAmount(expenseAmount);
            expenseCategory.setName(expenseCategoryName);
            expense.setCategory(expenseCategory);
            expense.setDate(expenseDate);
            expense.setDescription(expenseDescription);

            expenses[index] = expense;
            counter++;
        }

        // Contar y mostrar las categorías de los gastos del usuario
        Map<String, Integer> categoryCounter = countExpenseCategories(expenses);
        displayCategoryCounter(categoryCounter);

        // Mostrar la suma total de los gastos ingresados por el usuario
        System.out.println("\nTotal de gastos ingresados: " + expenseCalculator.calculateTotalExpenses(expenses));

        // Resultados finales que se muestran después de que el usuario haya cargado los gastos
        System.out.println("\nDETALLE DE GASTOS INGRESADOS");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    private static int requestTotalExpenses(Scanner scanner) {
        boolean isValidInput = false;
        int totalExpensesToEnter = 0;

        do {
            System.out.print("Ingrese la cantidad de gastos que desee cargar: ");
            String input = scanner.nextLine().trim();

            if (ValidationUtils.isBlank(input)) {
                NotificationUtils.showError("El valor a ingresar no puede estar vacío.");
            } else {
                try {
                    totalExpensesToEnter = ValidationUtils.validateInteger(input);
                    if (totalExpensesToEnter <= 0) {
                        NotificationUtils.showError("El valor a ingresar debe ser un número entero mayor que cero.");
                    } else {
                        isValidInput = true;
                    }
                } catch (NumberFormatException e) {
                    NotificationUtils.showError("El valor a ingresar debe ser un número entero.");
                }
            }
        } while (!isValidInput);

        return totalExpensesToEnter;
    }

    private static double requestExpenseAmount(Scanner scanner, ExpenseAmountValidator validator, int index) {
        double expenseAmount = 0;

        while (true) {
            try {
                System.out.print("\nIngrese el monto del gasto " + (index + 1) + ": ");
                expenseAmount = scanner.nextDouble();
                validator.validateAmount(expenseAmount);
                break;
            } catch (InputMismatchException e) {
                NotificationUtils.showError("El valor ingresado no es un número válido.");
                scanner.next();
            } catch (InvalidExpenseAmountException e) {
                NotificationUtils.showError(e.getMessage());
            }
        }

        scanner.nextLine();
        return expenseAmount;
    }

    private static String requestExpenseCategory(Scanner scanner) {
        String expenseCategoryName;

        while (true) {
            System.out.print("Ingrese la categoría del gasto: ");
            expenseCategoryName = scanner.nextLine().toLowerCase().trim();

            if (ValidationUtils.isBlank(expenseCategoryName)) {
                NotificationUtils.showError("El nombre de la categoría no puede estar vacío.");
            } else if (ValidationUtils.isValidExpenseCategoryName(expenseCategoryName)) {
                return expenseCategoryName;
            } else {
                NotificationUtils.showError("El nombre de la categoría solo puede contener letras.");
            }
        }
    }

    private static String requestExpenseDate(Scanner scanner, ExpenseDateValidator validator) {
        String expenseDate;

        while (true) {
            System.out.print("Ingrese la fecha del gasto (dd/MM/yyyy): ");
            expenseDate = scanner.nextLine().trim();

            if (ValidationUtils.isBlank(expenseDate)) {
                NotificationUtils.showError("La fecha no puede estar vacía.");
                continue;
            }

            try {
                validator.validateDate(expenseDate);
                return expenseDate;
            } catch (InvalidExpenseDateException e) {
                NotificationUtils.showError(e.getMessage());
            }
        }
    }

    private static String requestExpenseDescription(Scanner scanner, ExpenseDescriptionValidator validator) {
        String expenseDescription;

        while (true) {
            System.out.print("Ingrese la descripción del gasto: ");
            expenseDescription = scanner.nextLine().trim();

            if (ValidationUtils.isBlank(expenseDescription)) {
                NotificationUtils.showError("La descripción no puede estar vacía.");
                continue;
            }

            try {
                validator.validateDescription(expenseDescription);
                return expenseDescription;
            } catch (InvalidExpenseDescriptionException e) {
                NotificationUtils.showError(e.getMessage());
            }
        }
    }

    private static Map<String, Integer> countExpenseCategories(Expense[] expenses) {
        Map<String, Integer> categoryCounter = new HashMap<>();
        for (Expense expense : expenses) {
            String categoryName = expense.getCategory().getName();
            categoryCounter.put(categoryName, categoryCounter.getOrDefault(categoryName, 0) + 1);
        }
        return categoryCounter;
    }

    private static void displayCategoryCounter(Map<String, Integer> categoryCounter) {
        System.out.println("\nContador por categoría:");
        for (Map.Entry<String, Integer> entry : categoryCounter.entrySet()) {
            System.out.println("Categoría: " + entry.getKey() + ", Contador: " + entry.getValue());
        }
    }
}