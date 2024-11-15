package global.kajisaab.core.permissionMiddleware;

import global.kajisaab.core.exceptionHandling.PermissionDeniedException;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import reactor.core.publisher.Mono;

public interface PermissionMiddleware {

    Mono<Void> validateAuthorization(UserDetailsEntity user, String route) throws PermissionDeniedException;
}
