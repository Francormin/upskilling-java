package config;

import dao.TarjetaCreditoDAOImpl;
import dao.TarjetaDebitoDAOImpl;
import entities.Tarjeta;
import entities.TarjetaCredito;
import entities.TarjetaDebito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Clase encargada de inicializar y gestionar las tablas de la base de datos H2
 * relacionadas con las tarjetas. Provee métodos para crear tablas y
 * verificar/inicializar con datos de ejemplo.
 */
public class DatabaseInitializer {

    /**
     * Crea la tabla 'TarjetasCredito' si no existe.
     */
    public static void createTarjetasCreditoTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS tarjetas_credito (
                id INT PRIMARY KEY AUTO_INCREMENT,
                numero VARCHAR(16) NOT NULL,
                titular VARCHAR(255) NOT NULL
            );
        """;

        try (Connection connection = JdbcConfiguration.getDBConnection();
             Statement statement = connection.createStatement()) {

            boolean didCreate = statement.execute(createTableSQL);
            if (didCreate) {
                System.out.println("Tabla 'tarjetas_credito' creada con éxito.");
            } else {
                System.out.println("La tabla 'tarjetas_credito' ya existe.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea la tabla 'TarjetasDebito' si no existe.
     */
    public static void createTarjetasDebitoTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS tarjetas_debito (
                id INT PRIMARY KEY AUTO_INCREMENT,
                numero VARCHAR(16) NOT NULL,
                titular VARCHAR(255) NOT NULL
            );
        """;

        try (Connection connection = JdbcConfiguration.getDBConnection();
             Statement statement = connection.createStatement()) {

            boolean didCreate = statement.execute(createTableSQL);
            if (didCreate) {
                System.out.println("Tabla 'tarjetas_debito' creada con éxito.");
            } else {
                System.out.println("La tabla 'tarjetas_debito' ya existe.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica si una tabla específica está vacía.
     *
     * @param tableName Nombre de la tabla a verificar.
     * @return true si la tabla está vacía, false en caso contrario.
     */
    private static boolean isTableEmpty(String tableName) {
        String countSQL = "SELECT COUNT(*) FROM " + tableName;

        try (Connection connection = JdbcConfiguration.getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(countSQL)) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Inserta una lista de tarjetas de ejemplo en la base de datos si la tabla está vacía.
     *
     * @param tarjetas   Lista de tarjetas a insertar.
     * @param tipoTarjeta Nombre de la tabla (TarjetasCredito o TarjetasDebito).
     */
    private static <T extends Tarjeta> void insertarTarjetasDeEjemplo(List<T> tarjetas, String tipoTarjeta) {
        if (isTableEmpty(tipoTarjeta)) {
            if (tipoTarjeta.equals("tarjetas_credito")) {
                TarjetaCreditoDAOImpl tarjetaCreditoDAO = new TarjetaCreditoDAOImpl();
                tarjetas.forEach(tarjeta -> tarjetaCreditoDAO.insertarTarjeta((TarjetaCredito) tarjeta));
                System.out.println("Tarjetas de crédito insertadas correctamente.");
            } else if (tipoTarjeta.equals("tarjetas_debito")) {
                TarjetaDebitoDAOImpl tarjetaDebitoDAO = new TarjetaDebitoDAOImpl();
                tarjetas.forEach(tarjeta -> tarjetaDebitoDAO.insertarTarjeta((TarjetaDebito) tarjeta));
                System.out.println("Tarjetas de débito insertadas correctamente.");
            }
        } else {
            System.out.println("La tabla '" + tipoTarjeta + "' ya tiene datos, no se insertaron tarjetas.");
        }
    }

    /**
     * Inserta tarjetas de crédito de ejemplo en la base de datos.
     */
    private static void insertarTarjetasCreditoDeEjemplo() {
        List<TarjetaCredito> tarjetasCredito = List.of(
                new TarjetaCredito("1234567812345678", "Juan Pérez"),
                new TarjetaCredito("8765432187654321", "María Gómez"),
                new TarjetaCredito("1234876512348765", "Carlos López")
        );
        insertarTarjetasDeEjemplo(tarjetasCredito, "tarjetas_credito");
    }

    /**
     * Inserta tarjetas de débito de ejemplo en la base de datos.
     */
    private static void insertarTarjetasDebitoDeEjemplo() {
        List<TarjetaDebito> tarjetasDebito = List.of(
                new TarjetaDebito("5432109876543210", "Ana Sánchez"),
                new TarjetaDebito("0987654321098765", "Jorge Martínez"),
                new TarjetaDebito("7654321098765432", "Laura Rodríguez")
        );
        insertarTarjetasDeEjemplo(tarjetasDebito, "tarjetas_debito");
    }

    /**
     * Método principal para inicializar la base de datos.
     * Este método crea las tablas necesarias para las tarjetas de crédito y débito,
     * y las llena con datos de ejemplo si están vacías.
     *
     * <p>Se encarga de llamar a los métodos que crean las tablas y los que insertan
     * las tarjetas de ejemplo, garantizando así que la base de datos esté lista para su uso.</p>
     */
    public static void inicializarBD() {
        createTarjetasCreditoTable();
        createTarjetasDebitoTable();

        insertarTarjetasCreditoDeEjemplo();
        insertarTarjetasDebitoDeEjemplo();

        System.out.println("Inicialización de base de datos completada exitosamente.");
    }
}