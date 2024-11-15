package global.kajisaab;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

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
                        url = "http://localhost:8080/api/v1",
                        description = "Consult IQ Operator API server"
                )
        }
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}