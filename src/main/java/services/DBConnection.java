package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DBConnection {

    public Optional<Connection> getOptConnection() {
        String dbname = System.getenv("PG_DBNAME");
        String host = System.getenv("PG_HOST");
        String port = System.getenv("PG_PORT");
        String user = System.getenv("PG_USER");
        String password = System.getenv("PG_DBNAME");

        String url = "jdbc:postgresql://" + host + ":"+port+"/"+dbname;
        
        try {
            Class.forName("org.postgresql.Driver"); // Explicitly load the driver
            Connection connection = DriverManager.getConnection(url, user, password);
            return Optional.ofNullable(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Connection getConnection() {
        String dbname = System.getenv("PG_DBNAME");
        String host = System.getenv("PG_HOST");
        String port = System.getenv("PG_PORT");
        String user = System.getenv("PG_USER");
        String password = System.getenv("PG_DBNAME");

        String url = "jdbc:postgresql://" + host + ":"+port+"/"+dbname;
        
        try {
            Class.forName("org.postgresql.Driver"); // Explicitly load the driver
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
