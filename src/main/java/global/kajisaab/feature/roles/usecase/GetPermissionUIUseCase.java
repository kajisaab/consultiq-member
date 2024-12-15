package global.kajisaab.feature.roles.usecase;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.roles.repository.ModulePermissionDetailRepository;
import global.kajisaab.feature.roles.usecase.request.GetPermissionUIUseCaseRequest;
import global.kajisaab.feature.roles.usecase.response.GetPermissionUIUseCaseResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@Singleton
public class GetPermissionUIUseCase implements UseCase<GetPermissionUIUseCaseRequest, GetPermissionUIUseCaseResponse> {

    private final ModulePermissionDetailRepository modulePermissionDetailRepository;

    private final Logger LOG = LoggerFactory.getLogger(GetPermissionUIUseCase.class);

    @Inject
    public GetPermissionUIUseCase(ModulePermissionDetailRepository modulePermissionDetailRepository) {
        this.modulePermissionDetailRepository = modulePermissionDetailRepository;
    }

    @Override
    public Mono<GetPermissionUIUseCaseResponse> execute(GetPermissionUIUseCaseRequest request) throws BadRequestException {
        return this.modulePermissionDetailRepository.getRolesDetailsUI(request.tenantId())
                .map(GetPermissionUIUseCaseResponse::new);
    }
}
