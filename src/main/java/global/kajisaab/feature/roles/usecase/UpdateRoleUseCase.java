package global.kajisaab.feature.roles.usecase;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.roles.repository.RolesRepository;
import global.kajisaab.feature.roles.usecase.request.UpdateRoleUseCaseRequest;
import global.kajisaab.feature.roles.usecase.response.UpdateRoleUseCaseResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class UpdateRoleUseCase implements UseCase<UpdateRoleUseCaseRequest, UpdateRoleUseCaseResponse> {

    private final RolesRepository rolesRepository;

    @Inject
    public UpdateRoleUseCase(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Mono<UpdateRoleUseCaseResponse> execute(UpdateRoleUseCaseRequest request) throws BadRequestException {

        return this.rolesRepository.findById(request.id())
                .switchIfEmpty(Mono.error(new BadRequestException("Role not found")))
                .map(role -> {
                    role.setTitle(request.name());
                    role.setPermissions(request.roles());
                    return role;
                })
                .flatMap(this.rolesRepository::save)
                .map(response -> new UpdateRoleUseCaseResponse("Successfully updated role"));
    }
}
