package global.kajisaab.feature.roles.usecase;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.roles.repository.RolesRepository;
import global.kajisaab.feature.roles.usecase.request.DeleteRoleUseCaseRequest;
import global.kajisaab.feature.roles.usecase.response.DeleteRoleUseCaseResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class DeleteRoleUseCase implements UseCase<DeleteRoleUseCaseRequest, DeleteRoleUseCaseResponse> {

    private final RolesRepository rolesRepository;

    @Inject
    public DeleteRoleUseCase(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Mono<DeleteRoleUseCaseResponse> execute(DeleteRoleUseCaseRequest request) throws BadRequestException {

        return this.rolesRepository.findById(request.id())
                .switchIfEmpty(Mono.error(new BadRequestException("Role not found")))
                .flatMap(role -> {
                    role.setDeleted(true);
                    return this.rolesRepository.save(role);
                })
                .map(response -> new DeleteRoleUseCaseResponse("Successfully deleted role"));
    }
}
