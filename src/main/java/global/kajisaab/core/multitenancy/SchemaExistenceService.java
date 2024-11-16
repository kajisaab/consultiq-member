package global.kajisaab.core.multitenancy;

import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton
public class SchemaExistenceService {

    // Placeholder database connection manager
    private final DatabaseConnectionManager connectionManager;

    private final Logger LOG = LoggerFactory.getLogger(SchemaExistenceService.class);

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
            LOG.error("Error checking schema existence: {}", e.getMessage());
            return false;
        }
    }

    // Method to check if the schema exists in the database
    private boolean databaseSchemaCheck(Connection connection, String tenantId) throws SQLException {
        String schemaName = "consultancy_" + tenantId;
        // Modified query to be more explicit
        String schemaCheckQuery =
                "SELECT EXISTS (SELECT 1 FROM information_schema.schemata WHERE schema_name = ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(schemaCheckQuery)) {
            preparedStatement.setString(1, schemaName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // The EXISTS query will always return one row with a boolean value
                return resultSet.next() && resultSet.getBoolean(1);
            }
        }
    }
}