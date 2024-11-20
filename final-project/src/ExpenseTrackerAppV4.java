import dao.impl.ExpenseDAO;
import entities.Expense;
import entities.ExpenseCategory;
import exceptions.InvalidCutLogicVarException;
import exceptions.InvalidExpenseAmountException;
import exceptions.InvalidExpenseDateException;
import exceptions.InvalidExpenseDescriptionException;
import interfaces.ExpenseAmountValidator;
import interfaces.ExpenseDateValidator;
import interfaces.ExpenseDescriptionValidator;
import interfaces.impl.ExpenseAmountValidatorImpl;
import interfaces.impl.ExpenseDateValidatorImpl;
import interfaces.impl.ExpenseDescriptionValidatorImpl;
import services.Service;
import services.impl.ExpenseService;
import utils.NotificationUtils;
import utils.ValidationUtils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerAppV4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseAmountValidator expenseAmountValidator = new ExpenseAmountValidatorImpl();
        ExpenseDateValidator expenseDateValidator = new ExpenseDateValidatorImpl();
        ExpenseDescriptionValidator expenseDescriptionValidator = new ExpenseDescriptionValidatorImpl();

        Service<Expense> expenseService = new ExpenseService(new ExpenseDAO());
        // Connection conn = DatabaseConfig.getDatabaseConnection();
        // DatabaseConfig.populateDatabase(conn);

        try {
            uploadExpenses(
                    scanner,
                    expenseAmountValidator,
                    expenseDateValidator,
                    expenseDescriptionValidator,
                    expenseService);

            List<Expense> expensesStoredInDatabase = expenseService.getAll();
            if (!expensesStoredInDatabase.isEmpty()) {
                System.out.println("\nGastos guardados en la BD:");
                expensesStoredInDatabase.forEach(System.out::println);
            } else {
                System.out.println("\nNo hay gastos guardados en la BD.");
            }

            requestExpenseIdToSearchForIt(scanner, expenseService);
        } catch (InvalidCutLogicVarException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void uploadExpenses(
            Scanner scanner,
            ExpenseAmountValidator expenseAmountValidator,
            ExpenseDateValidator expenseDateValidator,
            ExpenseDescriptionValidator expenseDescriptionValidator,
            Service<Expense> expenseService) throws InvalidCutLogicVarException {

        boolean cutLogicVar;
        System.out.print("¿Desea cargar un gasto? TRUE / FALSE: ");

        try {
            cutLogicVar = scanner.nextBoolean();

            while (cutLogicVar) {
                Expense expense = new Expense();
                ExpenseCategory expenseCategory = new ExpenseCategory();

                double expenseAmount = requestExpenseAmount(scanner, expenseAmountValidator);
                String expenseCategoryName = requestExpenseCategory(scanner);
                String expenseDate = requestExpenseDate(scanner, expenseDateValidator);
                String expenseDescription = requestExpenseDescription(scanner, expenseDescriptionValidator);

                expense.setAmount(expenseAmount);
                expenseCategory.setName(expenseCategoryName);
                expense.setCategory(expenseCategory);
                expense.setDate(expenseDate);
                expense.setDescription(expenseDescription);

                expenseService.add(expense);

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
                System.out.print("\nIngrese el monto del gasto: ");
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

    private static void requestExpenseIdToSearchForIt(Scanner scanner, Service<Expense> expenseService) {
        System.out.print("\n¿Desea buscar un gasto en específico? TRUE / FALSE: ");
        try {

            boolean continueSearch = scanner.nextBoolean();
            scanner.nextLine();

            while (continueSearch) {

                System.out.print("\nIngrese el id del gasto: ");
                try {
                    int expenseId = scanner.nextInt();
                    scanner.nextLine();

                    if (expenseId <= 0) {
                        NotificationUtils.showError("El id del gasto debe ser un número entero mayor a 0.");
                    } else {
                        Expense expenseToReturn = expenseService.getById(expenseId);

                        if (expenseToReturn.getId() != null) {
                            System.out.println("\nGasto encontrado: " + expenseToReturn);
                        } else {
                            System.err.println("Gasto no encontrado.\n");
                        }
                    }
                } catch (InputMismatchException e) {
                    NotificationUtils.showError("El valor ingresado no es un número válido.");
                    scanner.nextLine();
                }

                System.out.print("\n¿Desea buscar otro gasto? TRUE / FALSE: ");
                try {
                    continueSearch = scanner.nextBoolean();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    NotificationUtils.showError("El valor a ingresar debe ser 'true' o 'false'.");
                }

            }

        } catch (InputMismatchException e) {
            NotificationUtils.showError("El valor a ingresar debe ser 'true' o 'false'.");
        }
    }
}