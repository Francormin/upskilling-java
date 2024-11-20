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
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void populateDatabase(Connection conn) {
        try {
            Statement statement = conn.createStatement();

            String GET_DATA = "SELECT * FROM expenses";
            ResultSet resultSet = statement.executeQuery(GET_DATA);

            if (resultSet.next()) {
                return;
            } else {
                String CREATE_EXPENSES_TABLE = "CREATE TABLE IF NOT EXISTS expenses (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    " amount DOUBLE PRECISION NOT NULL," +
                    " date VARCHAR(255)," +
                    " expense_category VARCHAR(1024)," +
                    " description VARCHAR(255)" +
                    ")";
                statement.executeUpdate(CREATE_EXPENSES_TABLE);

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
                statement.executeUpdate(INSERT_NEW_EXPENSES);

                statement.close();
                conn.close();
                System.out.println("Registros insertados con éxito en la tabla 'expenses'.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
