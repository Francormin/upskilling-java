package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase responsable de configurar la conexión JDBC con la base de datos H2.
 * Provee los detalles de conexión y el método para obtener una conexión activa a la base de datos.
 */
public class JdbcConfiguration {

    /**
     * Driver de la base de datos H2 que será utilizado para establecer la conexión.
     */
    public static final String DB_DRIVER = "org.h2.Driver";

    /**
     * URL de conexión a la base de datos H2, con el archivo de almacenamiento localizado en el directorio de usuario.
     */
    public static final String DB_CONNECTION = "jdbc:h2:~/cards";

    /**
     * Usuario de la base de datos H2. En este caso, es el usuario por defecto 'sa'.
     */
    public static final String DB_USER = "sa";

    /**
     * Contraseña de la base de datos H2. Se deja vacía para este entorno.
     */
    public static final String DB_PASSWORD = "";

    /**
     * Establece y retorna la conexión a la base de datos H2 utilizando JDBC.
     *
     * @return Objeto Connection que representa la conexión activa a la base de datos.
     */
    public static Connection getDBConnection() {
        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}