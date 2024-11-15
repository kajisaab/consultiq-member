package global.kajisaab.core.permissionMiddleware;

import reactor.core.publisher.Mono;

import java.util.List;

public interface PermissionMiddlewareService {

    Mono<Boolean> validatePermission(List<String> userPermissions, List<String> urlPermissions);

    Mono<List<String>> getPermissions(List<String> userPermissions);
}
