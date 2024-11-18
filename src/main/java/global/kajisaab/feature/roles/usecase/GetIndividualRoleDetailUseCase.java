package global.kajisaab.feature.roles.usecase;

import global.kajisaab.common.constants.StatusEnum;
import global.kajisaab.common.utils.DateUtils;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.roles.repository.RolesRepository;
import global.kajisaab.feature.roles.usecase.request.GetIndividualRoleDetailUseCaseRequest;
import global.kajisaab.feature.roles.usecase.response.GetIndividualRoleDetailUseCaseResponse;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class GetIndividualRoleDetailUseCase implements UseCase<GetIndividualRoleDetailUseCaseRequest, GetIndividualRoleDetailUseCaseResponse> {

    private final RolesRepository rolesRepository;

    @Inject
    public GetIndividualRoleDetailUseCase(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Mono<GetIndividualRoleDetailUseCaseResponse> execute(GetIndividualRoleDetailUseCaseRequest request) throws BadRequestException {
        return rolesRepository.findById(request.id())
                .switchIfEmpty(Mono.error(new BadRequestException("Role not found")))
                .map(role -> new GetIndividualRoleDetailUseCaseResponse(
                        role.getId(),
                        role.getTitle(),
                        Objects.requireNonNull(DateUtils.convertToUtcDate(role.getCreatedOn().toString())).toString(),
                        role.getCreatedBy().label(),
                        role.getLastModifiedOn() != null ? Objects.requireNonNull(DateUtils.convertToUtcDate(role.getLastModifiedOn().toString())).toString() : "",
                        StatusEnum.getFromName(role.getStatus()).getDisplayName(),
                        role.getPermissions()
                ));
    }
}
