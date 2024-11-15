package global.kajisaab;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Value;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.time.Instant;

/**
 * Defines a security scheme named BearerAuth for bearer token authentication.
 * This security scheme uses the Authorization header with a Bearer token.
 * <p>
 * The token format expected is JWT (JSON Web Token).
 */

@SecurityScheme(name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
/**
 * This configuration ensures that endpoints in the Consult IQ Operator API
 * require the BearerAuth security scheme to access.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "consultiq-member",
                version = "0.1",
                description = "Consult IQ Operator portal API",
                contact = @Contact(
                        name = "Aman Kaji Khadka",
                        email = "amankajikhadka@gmail.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        security = @SecurityRequirement(name = "BearerAuth"),
        servers = {
                @Server(
                        url = "http://localhost:9090",
                        description = "Consult IQ Operator API server"
                )
        }
)
public class Application {


    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Value("${micronaut.server.port}")
    public String port;

    /**
     * Main method to start the application.
     *
     * @param args The command-line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        ApplicationContext context = Micronaut.run(Application.class, args);

        Application application = context.getBean(Application.class);
        application.startupLog();
    }

    private void startupLog() {
        MDC.put("startupTime", Instant.now().toString());
        MDC.put("env", "dev");

        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();

        LOG.info("The application is starting at http://localhost:{}...\nTotal heap size: {} bytes\nMaximum heap size: {} bytes\nFree memory: {} bytes",
                port, totalMemory, maxMemory, freeMemory);
    }
}