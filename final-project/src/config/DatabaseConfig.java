package config;

import entities.ExpenseCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String URL = "jdbc:h2:~/expenses";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public static Connection getDatabaseConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void populateDatabase(Connection conn) {
        try {
            Statement stmt = conn.createStatement();

            String GET_TABLE = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'EXPENSES'";
            ResultSet rs = stmt.executeQuery(GET_TABLE);

            if (rs.next()) {
                stmt.close();
                rs.close();
            } else {
                String CREATE_EXPENSES_TABLE = "CREATE TABLE expenses (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        " amount DOUBLE PRECISION NOT NULL," +
                        " date VARCHAR(255)," +
                        " expense_category VARCHAR(1024)," +
                        " description VARCHAR(255)" +
                        ")";
                stmt.executeUpdate(CREATE_EXPENSES_TABLE);

                // Serializar el objeto ExpenseCategory antes de insertarlo
                ExpenseCategory groceriesCategory = new ExpenseCategory(
                        "Groceries",
                        "Expenses for groceries"
                );
                ExpenseCategory supermarketCategory = new ExpenseCategory(
                        "Supermarket",
                        "Expenses for supermarket items"
                );

                String groceriesJson = ExpenseCategorySerializer.serialize(groceriesCategory);
                String supermarketJson = ExpenseCategorySerializer.serialize(supermarketCategory);

                String INSERT_NEW_EXPENSES = "INSERT INTO expenses (amount, date, expense_category, description)" +
                        " VALUES (24.99, '20/05/2024', '" + groceriesJson + "', 'groceries')," +
                        " (19.99, '15/05/2024', '" + supermarketJson + "', 'supermarket')";
                stmt.executeUpdate(INSERT_NEW_EXPENSES);

                stmt.close();
                conn.close();
                System.out.println("Registros insertados con éxito en la tabla 'expenses'.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}