import entities.Expense;
import entities.ExpenseCategory;
import interfaces.ExpenseAmountValidator;
import interfaces.ExpenseAmountValidatorImpl;
import interfaces.ExpenseCalculator;
import interfaces.ExpenseCalculatorImpl;
import interfaces.ExpenseDateValidator;
import interfaces.ExpenseDateValidatorImpl;
import exceptions.InvalidExpenseAmountException;
import exceptions.InvalidExpenseDateException;
import utils.NotificationUtils;
import utils.ValidationUtils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExpenseTrackerAppV2 {
    public static int counter = 1;

    public static void main(String[] args) {
        // Instanciar las clases que implementan interfaces para su posterior uso
        Scanner scanner = new Scanner(System.in);
        ExpenseAmountValidator expenseAmountValidator = new ExpenseAmountValidatorImpl();
        ExpenseDateValidator expenseDateValidator = new ExpenseDateValidatorImpl();
        ExpenseCalculator expenseCalculator = new ExpenseCalculatorImpl();

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

            expense.setId(counter);
            expense.setAmount(expenseAmount);
            expenseCategory.setName(expenseCategoryName);
            expense.setCategory(expenseCategory);
            expense.setDate(expenseDate);

            expenses[index] = expense;
            counter++;
        }

        // Resultados finales que se muestran después de que el usuario haya cargado los gastos
        System.out.println("\nTotal de gastos ingresados: " + expenseCalculator.calculateTotalExpenses(expenses));
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
}