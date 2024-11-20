import config.DatabaseConfig;
import dao.impl.ExpenseDAO;
import entities.Expense;
import services.Service;
import services.impl.ExpenseService;
import utils.NotificationUtils;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerAppV4 {
    public static void main(String[] args) {
        Connection conn = DatabaseConfig.getDatabaseConnection();
        DatabaseConfig.populateDatabase(conn);

        Scanner scanner = new Scanner(System.in);
        Service<Expense> expenseService = new ExpenseService(new ExpenseDAO());

        List<Expense> expensesStoredInDatabase = expenseService.getAll();
        System.out.println("Gastos guardados en la BD:");
        expensesStoredInDatabase.forEach(System.out::println);

        Expense expense = requestExpenseIdToSearchForIt(scanner, expenseService);
        System.out.println("\nExpense:");
        System.out.println(expense);
    }

    private static Expense requestExpenseIdToSearchForIt(Scanner scanner, Service<Expense> expenseService) {
        int expenseId;

        while (true) {
            try {
                System.out.print("\nIngrese el id del gasto: ");
                expenseId = scanner.nextInt();

                if (expenseId <= 0) {
                    NotificationUtils.showError("El id del gasto debe ser un número entero mayor a 0.");
                } else {
                    return expenseService.getById(expenseId);
                }
            } catch (InputMismatchException e) {
                NotificationUtils.showError("El valor ingresado no es un número válido.");
                scanner.next();
            }
        }
    }
}