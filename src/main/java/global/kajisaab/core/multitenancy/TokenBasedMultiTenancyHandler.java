package global.kajisaab.core.multitenancy;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requirements;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.runtime.multitenancy.TenantResolver;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.multitenancy.exceptions.TenantNotFoundException;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Singleton;

import java.io.Serializable;
import java.util.Optional;

@Singleton
@Primary
@Requirements(@Requires(
        property = "micronaut.multitenancy.tenantresolver.token.enabled",
        value = "true",
        defaultValue = "false"
))
public class TokenBasedMultiTenancyHandler implements TenantResolver {

    @Override
    public @NonNull Serializable resolveTenantIdentifier() throws TenantNotFoundException {
        // Get the current HTTP request
        Optional<HttpRequest<Object>> currentRequest = ServerRequestContext.currentRequest();

        // Check if the current request is for the /login endpoint
        if (currentRequest.isPresent() && currentRequest.get().getPath().contains("/login")) {
            // Fetch the tenantId (consultancyCode) from the headers
            String tenantId = currentRequest.get().getHeaders().get("consultancyCode");

            if (tenantId != null) {
                return tenantId; // Return the tenantId from the header
            }

            throw new TenantNotFoundException("Tenant ID (consultancyCode) not found in the header for /login");
        }

        // Fallback: Retrieve tenant ID from the Authentication token
        Optional<Authentication> authentication = currentRequest
                .flatMap(HttpRequest::getUserPrincipal)
                .map(Authentication.class::cast);

        if (authentication.isPresent()) {
            Object tenantId = authentication.get().getAttributes().get("schema");
            if (tenantId != null) {
                return tenantId.toString();
            }
        }

        throw new TenantNotFoundException("Tenant ID not found in authentication or TenantContext");
    }
}
