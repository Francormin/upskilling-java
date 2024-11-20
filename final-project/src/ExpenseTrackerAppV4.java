import config.DatabaseConfig;

import java.sql.Connection;

public class ExpenseTrackerAppV4 {
    public static void main(String[] args) {
        Connection conn = DatabaseConfig.getDatabaseConnection();
        DatabaseConfig.populateDatabase(conn);
    }
}
