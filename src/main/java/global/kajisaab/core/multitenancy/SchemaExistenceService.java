package global.kajisaab.core.multitenancy;

import jakarta.inject.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton
public class SchemaExistenceService {

    // Placeholder database connection manager
    private final DatabaseConnectionManager connectionManager;

    public SchemaExistenceService(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    // Method to check if the schema exists for the given tenantId
    public boolean doesSchemaExist(String tenantId) {
        // Perform the database check for the schema existence
        try (Connection connection = connectionManager.getConnection()) {
            return databaseSchemaCheck(connection, tenantId);
        } catch (SQLException e) {
            // Log the exception (for debugging and error tracking)
            System.err.println("Error checking schema existence: " + e.getMessage());
            return false;
        }
    }

    // Method to check if the schema exists in the database
    private boolean databaseSchemaCheck(Connection connection, String tenantId) throws SQLException {
        String schemaCheckQuery = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(schemaCheckQuery)) {
            preparedStatement.setString(1, "consultancy_" + tenantId); // The tenant-specific schema name
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If a row is returned, the schema exists
            }
        }
    }
}