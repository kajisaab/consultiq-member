package global.kajisaab.core.permissionMiddleware.impl;

import global.kajisaab.core.permissionMiddleware.PermissionMiddlewareService;
import global.kajisaab.feature.roles.repository.RolesRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class PermissionMiddlewareServiceImpl implements PermissionMiddlewareService {

    private final RolesRepository rolesRepository;

    @Inject
    public PermissionMiddlewareServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Mono<Boolean> validatePermission(List<String> userRoles, List<String> urlPermissions) {

        if (userRoles.isEmpty()) {
            return Mono.just(false);
        }

        return rolesRepository.findAllByIdIn(userRoles)
                .collectList()
                .flatMap(roles -> {
                    System.out.println("Torles roles");
                    Set<String> allowedPermissions = roles.stream()
                            .filter(rolePermission -> rolePermission.isDeleted() && rolePermission.getActive())
                            .flatMap(rolePermission -> rolePermission.getPermissions().stream())
                            .collect(Collectors.toSet());

                    return Mono.just(urlPermissions.stream().anyMatch(allowedPermissions::contains));
                })
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Mono<List<String>> getPermissions(List<String> userRoles) {

        return rolesRepository.findAllByIdIn(userRoles)
                .collectList()
                .flatMap(roles -> Mono.just(roles.stream()
                        .filter(rolePermission -> rolePermission.isDeleted() && rolePermission.getActive())
                        .flatMap(rolePermission -> rolePermission.getPermissions().stream())
                        .distinct()
                        .collect(Collectors.toList()))
                );
    }
}
