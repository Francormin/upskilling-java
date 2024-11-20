import entities.Expense;
import entities.ExpenseCategory;
import exceptions.InvalidCutLogicVarException;
import exceptions.InvalidExpenseAmountException;
import exceptions.InvalidExpenseDateException;
import exceptions.InvalidExpenseDescriptionException;
import interfaces.ExpenseAmountValidator;
import interfaces.ExpenseDateValidator;
import interfaces.ExpenseDescriptionValidator;
import interfaces.ExpenseProcessor;
import interfaces.impl.ExpenseAmountValidatorImpl;
import interfaces.impl.ExpenseDateValidatorImpl;
import interfaces.impl.ExpenseDescriptionValidatorImpl;
import utils.NotificationUtils;
import utils.Utilities;
import utils.ValidationUtils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ExpenseTrackerAppV3 {
    public static int counter = 1;

    public static void main(String[] args) {
        // Instanciar las clases que implementan interfaces para su posterior uso
        Scanner scanner = new Scanner(System.in);
        ExpenseAmountValidator expenseAmountValidator = new ExpenseAmountValidatorImpl();
        ExpenseDateValidator expenseDateValidator = new ExpenseDateValidatorImpl();
        ExpenseDescriptionValidator expenseDescriptionValidator = new ExpenseDescriptionValidatorImpl();

        // Crear una lista de gastos a la que se agregará, posteriormente, los datos ingresados por el usuario
        List<Expense> expenses = new ArrayList<>();

        try {

            // Llamar al método principal que se encarga de cargar gastos al sistema
            uploadExpenses(
                    scanner,
                    expenseAmountValidator,
                    expenseDateValidator,
                    expenseDescriptionValidator,
                    expenses);

            // Contar y mostrar las categorías de los gastos del usuario
            Map<String, Integer> categoryCounter = countExpenseCategories(expenses);
            displayCategoryCounter(categoryCounter);

            // Implementaciones directas de la interfaz funcional ExpenseProcesor
            // a través de la utilización de expresiones lambda

            // Calcular y mostrar la suma total de los gastos ingresados por el usuario
            double totalSpent = calculateTotalExpenses(expenses);
            System.out.println("\nTotal de gastos: " + totalSpent);

            // Calcular y mostrar el promedio de los gastos ingresados por el usuario
            double averageSpent = calculateAverageExpense(expenses);
            System.out.println("Promedio de gastos: " + averageSpent);

            // Resultados finales que se muestran después de que el usuario haya cargado los gastos
            System.out.println("\nDETALLE DE GASTOS INGRESADOS");
            Utilities.printElements(expenses);

        } catch (InvalidCutLogicVarException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void uploadExpenses(
            Scanner scanner,
            ExpenseAmountValidator expenseAmountValidator,
            ExpenseDateValidator expenseDateValidator,
            ExpenseDescriptionValidator expenseDescriptionValidator,
            List<Expense> expenses) throws InvalidCutLogicVarException {

        // Variable de corte para preguntar al usuario si desea cargar un nuevo gasto en el sistema
        boolean cutLogicVar;
        System.out.print("¿Desea cargar un gasto? TRUE / FALSE: ");

        try {
            cutLogicVar = scanner.nextBoolean();

            // Crear y agregar gastos (con sus correspondientes datos) a la lista de gastos creada arriba
            while (cutLogicVar) {
                Expense expense = new Expense();
                ExpenseCategory expenseCategory = new ExpenseCategory();

                double expenseAmount = requestExpenseAmount(scanner, expenseAmountValidator);
                String expenseCategoryName = requestExpenseCategory(scanner);
                String expenseDate = requestExpenseDate(scanner, expenseDateValidator);
                String expenseDescription = requestExpenseDescription(scanner, expenseDescriptionValidator);

                expense.setId(counter);
                expense.setAmount(expenseAmount);
                expenseCategory.setName(expenseCategoryName);
                expense.setCategory(expenseCategory);
                expense.setDate(expenseDate);
                expense.setDescription(expenseDescription);

                expenses.add(expense);
                counter++;

                System.out.print("\n¿Desea cargar otro gasto? TRUE / FALSE: ");
                cutLogicVar = scanner.nextBoolean();
            }
        } catch (InputMismatchException e) {
            throw new InvalidCutLogicVarException("El valor a ingresar debe ser 'true' o 'false'");
        }

    }

    private static double requestExpenseAmount(Scanner scanner, ExpenseAmountValidator validator) {
        double expenseAmount;

        while (true) {
            try {
                System.out.print("\nIngrese el monto del gasto " + counter + ": ");
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

    private static Map<String, Integer> countExpenseCategories(List<Expense> expenses) {
        return expenses.stream()
                .map(expense -> expense.getCategory().getName())
                .collect(Collectors.toMap(
                        categoryName -> categoryName,
                        categoryName -> 1,
                        Integer::sum
                ));
    }

    private static void displayCategoryCounter(Map<String, Integer> categoryCounter) {
        System.out.println("\nContador por categoría:");
        categoryCounter.forEach((category, count) ->
                System.out.println("Categoría: " + category + ", Contador: " + count)
        );
    }

    private static double calculateTotalExpenses(List<Expense> expenses) {
        ExpenseProcessor totalCalculator = expense -> expense.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
        return totalCalculator.process(expenses);
    }

    private static double calculateAverageExpense(List<Expense> expenses) {
        ExpenseProcessor averageCalculator = expense -> expense.stream()
                .mapToDouble(Expense::getAmount)
                .average()
                .orElse(0.0);
        return averageCalculator.process(expenses);
    }
}