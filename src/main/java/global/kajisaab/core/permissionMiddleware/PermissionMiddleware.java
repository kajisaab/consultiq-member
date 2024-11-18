package global.kajisaab.core.permissionMiddleware;

import global.kajisaab.core.exceptionHandling.PermissionDeniedException;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import io.micronaut.http.HttpRequest;
import reactor.core.publisher.Mono;

public interface PermissionMiddleware {

    Mono<Void> validateAuthorization(UserDetailsEntity user, String route, HttpRequest<?> currentRequest, String tenantId) throws PermissionDeniedException;
}
