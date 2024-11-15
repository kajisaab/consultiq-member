package global.kajisaab.core.multitenancy;


import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DatabaseConnectionManager {

    private final String url = "jdbc:postgresql://localhost:5432/your_database"; // Database URL
    private final String username = "your_username";
    private final String password = "your_password";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}