package global.kajisaab.core.permissionMiddleware.impl;

import global.kajisaab.common.constants.AppConstant;
import global.kajisaab.common.constants.PermissionConstant;
import global.kajisaab.core.exceptionHandling.PermissionDeniedException;
import global.kajisaab.core.permissionMiddleware.PermissionMiddleware;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class PermissionMiddlewareImpl implements PermissionMiddleware {

    private final Logger LOG = LoggerFactory.getLogger(PermissionMiddlewareImpl.class);

    private final PermissionMiddlewareServiceImpl permissionMiddlewareServiceImpl;

    @Inject
    public PermissionMiddlewareImpl(PermissionMiddlewareServiceImpl permissionMiddlewareServiceImpl) {
        this.permissionMiddlewareServiceImpl = permissionMiddlewareServiceImpl;
    }

    @Override
    public Mono<Void> validateAuthorization(UserDetailsEntity user, String route) throws PermissionDeniedException {

        Optional<HttpRequest<Object>> currentRequest = ServerRequestContext.currentRequest();

        if(currentRequest.isPresent()){

            String requestUrl = currentRequest.get().getUri().toString();

            boolean isExcludedRoute = AppConstant.PUBLIC_ROUTE.stream().anyMatch(requestUrl::contains);

            if (!isExcludedRoute) {

                String method = currentRequest.get().getMethodName();
                String originalUrl = requestUrl.replaceAll("^(\\/)?v1|\\/$", "");
                List<String> urlPermissions = PermissionConstant.getPermissionsByEndpointAndMethod(originalUrl, method);

                List<String> userRoles = new ArrayList<>();

                user.getRoles().forEach(role -> {
                    userRoles.add(role.value());
                });

                boolean authorized = true;

                // Check if the endpoint and method are valid
                authorized = PermissionConstant.isValidPermissionForEndpointAndMethod(originalUrl, method);
                LOG.info("Inside the permission middleware: {}", authorized);

                // Check if URL permissions are valid
                if (urlPermissions != null && !urlPermissions.isEmpty() && authorized) {
                    try {
                        // Asynchronously validate permissions
                        return this.permissionMiddlewareServiceImpl.validatePermission(userRoles, urlPermissions)
                                .flatMap(permissionValid -> {
                                    if (!permissionValid) {
                                        return Mono.error(new PermissionDeniedException("You are not authorized to access this resource"));
                                    }
                                    return Mono.empty();
                                });
                    } catch (Exception e) {
                        LOG.error("Authorization error: {}", e.getMessage());
                        return Mono.error(new PermissionDeniedException("You are not authorized to access this resource"));
                    }
                }
            }
        }

        throw new PermissionDeniedException("Invalid Request");



    }
}
