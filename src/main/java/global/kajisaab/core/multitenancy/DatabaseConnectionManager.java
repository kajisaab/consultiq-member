package global.kajisaab.core.multitenancy;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DatabaseConnectionManager {

    private final DbConfig dbConfig;
    private final String url;
    private final String username;
    private final String password;

    @Inject
    public DatabaseConnectionManager(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
        // Convert r2dbc URL to JDBC URL
        // From: r2dbc:postgresql://localhost:5432/consultiq
        // To:   jdbc:postgresql://localhost:5432/consultiq
        this.url = dbConfig.getDbUrl().replace("r2dbc:", "jdbc:");
        this.username = dbConfig.getUsername();
        this.password = dbConfig.getDbPassword();

        // Load the PostgreSQL JDBC driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}