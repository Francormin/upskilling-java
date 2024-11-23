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
import interfaces.ExpenseProcessor;
import interfaces.impl.ExpenseAmountValidatorImpl;
import interfaces.impl.ExpenseDateValidatorImpl;
import interfaces.impl.ExpenseDescriptionValidatorImpl;
import services.Service;
import services.impl.ExpenseService;
import utils.NotificationUtils;
import utils.Utilities;
import utils.ValidationUtils;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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

            // Primero se muestran los gastos que ya se encuentran guardados en la base de datos
            List<Expense> expensesStoredInDatabase = expenseService.getAll();
            if (!expensesStoredInDatabase.isEmpty()) {
                System.out.println("GASTOS GUARDADOS:");
                Utilities.printElements(expensesStoredInDatabase);
            } else {
                System.out.println("\nNO HAY GASTOS GUARDADOS.");
            }

            // Luego se le pide al usuario que cargue nuevos gastos al sistema si así lo desea
            uploadExpenses(
                scanner,
                expenseAmountValidator,
                expenseDateValidator,
                expenseDescriptionValidator,
                expenseService);

            // Luego se le pide al usuario que ingrese un ID para buscar un gasto en particular si así lo desea
            requestExpenseIdToSearchForIt(scanner, expenseService);

            // Luego se le pide al usuario que ingrese un ID para actualizar un gasto si así lo desea
            requestExpenseIdToUpdateIt(
                scanner,
                expenseAmountValidator,
                expenseDateValidator,
                expenseDescriptionValidator,
                expenseService
            );

            // Luego se le pide al usuario que ingrese un ID para eliminar un gasto si así lo desea
            requestExpenseIdToDeleteIt(scanner, expenseService);

            // Resultados finales que se muestran después de todas las operaciones que el usuario haya realizado
            List<Expense> expensesStoredInDatabaseAfterAll = expenseService.getAll();

            // Contar y mostrar las categorías de los gastos del usuario
            Map<String, Integer> categoryCounter = countExpenseCategories(expensesStoredInDatabaseAfterAll);
            displayCategoryCounter(categoryCounter);

            // Implementaciones directas de la interfaz funcional ExpenseProcessor
            // a través de la utilización de expresiones lambda

            // Calcular y mostrar la suma total de los gastos ingresados por el usuario
            double totalSpent = calculateTotalExpenses(expensesStoredInDatabaseAfterAll);
            System.out.println("\nTotal de gastos: " + totalSpent);

            // Calcular y mostrar el promedio de los gastos ingresados por el usuario
            double averageSpent = calculateAverageExpense(expensesStoredInDatabaseAfterAll);
            System.out.println("Promedio de gastos: " + averageSpent);

            // Se vuelven a mostrar los gastos guardados luego de las operaciones que haya realizado el usuario
            if (!expensesStoredInDatabase.isEmpty()) {
                System.out.println("\nGASTOS GUARDADOS:");
                Utilities.printElements(expensesStoredInDatabaseAfterAll);
            } else {
                System.out.println("\nNO HAY GASTOS GUARDADOS.");
            }

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
        System.out.print("\n¿Desea cargar un gasto? TRUE / FALSE: ");

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
            throw new InvalidCutLogicVarException("El valor a ingresar debe ser 'true' o 'false'.");
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
                NotificationUtils.showOnError("El valor ingresado no es un número válido.");
                scanner.next();
            } catch (InvalidExpenseAmountException e) {
                NotificationUtils.showOnError(e.getMessage());
            }
        }

        scanner.nextLine();
        return expenseAmount;
    }

    private static String requestExpenseCategory(Scanner scanner) {
        String expenseCategoryName;

        while (true) {
            System.out.print("\nIngrese la categoría del gasto: ");
            expenseCategoryName = scanner.nextLine().toLowerCase().trim();

            if (ValidationUtils.isBlank(expenseCategoryName)) {
                NotificationUtils.showOnError("El nombre de la categoría no puede estar vacío.");
            } else if (!ValidationUtils.isValidExpenseCategoryName(expenseCategoryName)) {
                NotificationUtils.showOnError("El nombre de la categoría solo puede contener letras.");
            } else if (ValidationUtils.isShort(expenseCategoryName)) {
                NotificationUtils.showOnError("El nombre de la categoría debe tener al menos tres letras.");
            } else {
                return expenseCategoryName;
            }
        }
    }

    private static String requestExpenseDate(Scanner scanner, ExpenseDateValidator validator) {
        String expenseDate;

        while (true) {
            System.out.print("\nIngrese la fecha del gasto (dd/MM/yyyy): ");
            expenseDate = scanner.nextLine().trim();

            if (ValidationUtils.isBlank(expenseDate)) {
                NotificationUtils.showOnError("La fecha no puede estar vacía.");
                continue;
            }

            try {
                validator.validateDate(expenseDate);
                return expenseDate;
            } catch (InvalidExpenseDateException e) {
                NotificationUtils.showOnError(e.getMessage());
            }
        }
    }

    private static String requestExpenseDescription(Scanner scanner, ExpenseDescriptionValidator validator) {
        String expenseDescription;

        while (true) {
            System.out.print("\nIngrese la descripción del gasto: ");
            expenseDescription = scanner.nextLine().trim().toLowerCase();

            if (ValidationUtils.isBlank(expenseDescription)) {
                NotificationUtils.showOnError("La descripción no puede estar vacía.");
                continue;
            }

            try {
                validator.validateDescription(expenseDescription);
                return expenseDescription;
            } catch (InvalidExpenseDescriptionException e) {
                NotificationUtils.showOnError(e.getMessage());
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
        categoryCounter.entrySet().stream()
            .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
            .forEach(entry -> System.out.println("Categoría: " + entry.getKey() + ", Contador: " + entry.getValue()));
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

    private static void requestExpenseIdToSearchForIt(Scanner scanner, Service<Expense> expenseService) {
        boolean continueSearch;

        do {

            System.out.print("\n¿Desea buscar un gasto en específico? TRUE / FALSE: ");
            try {
                continueSearch = scanner.nextBoolean();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                NotificationUtils.showOnError("El valor a ingresar debe ser 'true' o 'false'.");
                scanner.nextLine();
                continue;
            }

            while (continueSearch) {

                System.out.print("\nIngrese el id del gasto: ");
                try {
                    int expenseId = scanner.nextInt();
                    scanner.nextLine();

                    if (expenseId <= 0) {
                        NotificationUtils.showOnError("El id del gasto debe ser un número entero mayor a 0.");
                    } else {
                        Expense expenseToReturn = expenseService.getById(expenseId);

                        if (expenseToReturn.getId() != null) {
                            NotificationUtils.showOnSuccess("Gasto encontrado:");
                            System.out.println(expenseToReturn);
                        } else {
                            NotificationUtils.showOnError("Gasto no encontrado.");
                        }
                    }
                } catch (InputMismatchException e) {
                    NotificationUtils.showOnError("El valor ingresado no es un número válido.");
                    scanner.nextLine();
                }

                System.out.print("\n¿Desea buscar otro gasto? TRUE / FALSE: ");
                try {
                    continueSearch = scanner.nextBoolean();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    NotificationUtils.showOnError("El valor a ingresar debe ser 'true' o 'false'.");
                    scanner.nextLine();
                    continueSearch = false;
                }

            }

        } while (askToRetry(scanner));
    }

    private static boolean askToRetry(Scanner scanner) {
        while (true) {
            System.out.print("\n¿Desea intentarlo nuevamente? TRUE / FALSE: ");
            try {
                boolean retry = scanner.nextBoolean();
                scanner.nextLine();
                return retry;
            } catch (InputMismatchException e) {
                NotificationUtils.showOnError("El valor a ingresar debe ser 'true' o 'false'.");
                scanner.nextLine();
            }
        }
    }

    private static void requestExpenseIdToUpdateIt(
        Scanner scanner,
        ExpenseAmountValidator amountValidator,
        ExpenseDateValidator dateValidator,
        ExpenseDescriptionValidator descriptionValidator,
        Service<Expense> expenseService) {

        boolean continueUpdate;

        do {

            System.out.print("\n¿Desea actualizar un gasto? TRUE / FALSE: ");
            try {
                continueUpdate = scanner.nextBoolean();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                NotificationUtils.showOnError("El valor a ingresar debe ser 'true' o 'false'.");
                scanner.nextLine();
                continue;
            }

            while (continueUpdate) {

                System.out.print("\nIngrese el id del gasto: ");
                try {
                    int expenseId = scanner.nextInt();
                    scanner.nextLine();

                    if (expenseId <= 0) {
                        NotificationUtils.showOnError("El id del gasto debe ser un número entero mayor a 0.");
                    } else {
                        Expense expenseToUpdate = expenseService.getById(expenseId);

                        if (expenseToUpdate.getId() != null) {
                            Expense newExpense = new Expense();
                            ExpenseCategory expenseCategory = new ExpenseCategory();

                            double newExpenseAmount = requestExpenseAmount(scanner, amountValidator);
                            String newExpenseCategory = requestExpenseCategory(scanner);
                            String newExpenseDate = requestExpenseDate(scanner, dateValidator);
                            String newExpenseDescription = requestExpenseDescription(scanner, descriptionValidator);

                            newExpense.setAmount(newExpenseAmount);
                            expenseCategory.setName(newExpenseCategory);
                            newExpense.setCategory(expenseCategory);
                            newExpense.setDate(newExpenseDate);
                            newExpense.setDescription(newExpenseDescription);
                            newExpense.setId(expenseToUpdate.getId());

                            expenseService.update(expenseToUpdate.getId(), newExpense);
                        } else {
                            NotificationUtils.showOnError("Gasto no encontrado.");
                        }
                    }
                } catch (InputMismatchException e) {
                    NotificationUtils.showOnError("El valor ingresado no es un número válido.");
                    scanner.nextLine();
                }

                System.out.print("\n¿Desea actualizar otro gasto? TRUE / FALSE: ");
                try {
                    continueUpdate = scanner.nextBoolean();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    NotificationUtils.showOnError("El valor a ingresar debe ser 'true' o 'false'.");
                    scanner.nextLine();
                    continueUpdate = false;
                }

            }

        } while (askToRetry(scanner));

    }

    private static void requestExpenseIdToDeleteIt(Scanner scanner, Service<Expense> expenseService) {
        boolean continueDelete;

        do {

            System.out.print("\n¿Desea eliminar un gasto? TRUE / FALSE: ");
            try {
                continueDelete = scanner.nextBoolean();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                NotificationUtils.showOnError("El valor a ingresar debe ser 'true' o 'false'.");
                scanner.nextLine();
                continue;
            }

            while (continueDelete) {

                System.out.print("\nIngrese el id del gasto: ");
                try {
                    int expenseId = scanner.nextInt();
                    scanner.nextLine();

                    if (expenseId <= 0) {
                        NotificationUtils.showOnError("El id del gasto debe ser un número entero mayor a 0.");
                    } else {
                        Expense expenseToReturn = expenseService.getById(expenseId);

                        if (expenseToReturn.getId() != null) {
                            expenseService.delete(expenseToReturn.getId());
                        } else {
                            NotificationUtils.showOnError("Gasto no encontrado.");
                        }
                    }
                } catch (InputMismatchException e) {
                    NotificationUtils.showOnError("El valor ingresado no es un número válido.");
                    scanner.nextLine();
                }

                System.out.print("\n¿Desea eliminar otro gasto? TRUE / FALSE: ");
                try {
                    continueDelete = scanner.nextBoolean();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    NotificationUtils.showOnError("El valor a ingresar debe ser 'true' o 'false'.");
                    scanner.nextLine();
                    continueDelete = false;
                }

            }

        } while (askToRetry(scanner));
    }
}