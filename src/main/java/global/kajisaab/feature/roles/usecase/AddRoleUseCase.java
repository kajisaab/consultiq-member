package global.kajisaab.feature.roles.usecase;

import global.kajisaab.common.dto.LabelValuePair;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import global.kajisaab.feature.roles.entity.Roles;
import global.kajisaab.feature.roles.repository.RolesRepository;
import global.kajisaab.feature.roles.usecase.request.AddRoleUseCaseRequest;
import global.kajisaab.feature.roles.usecase.response.AddRoleUseCaseResponse;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Singleton
public class AddRoleUseCase implements UseCase<AddRoleUseCaseRequest, AddRoleUseCaseResponse> {

    private final RolesRepository rolesRepository;

    private final SecurityService securityService;

    @Inject
    public AddRoleUseCase(RolesRepository rolesRepository, SecurityService securityService) {
        this.rolesRepository = rolesRepository;
        this.securityService = securityService;
    }

    @Override
    public Mono<AddRoleUseCaseResponse> execute(AddRoleUseCaseRequest request) throws BadRequestException {
        Authentication authenticationDetail = this.securityService.getAuthentication().orElseThrow(() ->
                new RuntimeException("User not authenticated")
        );

        Map<String, Object> attributes = authenticationDetail.getAttributes();

        UserDetailsEntity userDetailsEntity = (UserDetailsEntity) attributes.get("userDetails");

        Roles roles = new Roles();

        roles.setId(UUID.randomUUID().toString());
        roles.setTitle(request.name());
        roles.setActive(request.isActive());
        roles.setStatus(request.isActive() ? "ACTIVE" : "INACTIVE");
        roles.setPermissions(request.permissions());
        roles.setCreatedBy(new LabelValuePair(userDetailsEntity.getFullName(), userDetailsEntity.getId()));
        roles.setDeleted(false);
        roles.setCreatedOn(LocalDateTime.now());

        return this.rolesRepository.save(roles)
                .map(response -> new AddRoleUseCaseResponse("Successfully created role"));
    }
}
