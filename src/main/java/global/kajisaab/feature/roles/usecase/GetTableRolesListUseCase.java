package global.kajisaab.feature.roles.usecase;

import global.kajisaab.common.constants.StatusEnum;
import global.kajisaab.common.dto.GlobalTableListFilterPageableRequest;
import global.kajisaab.common.dto.GlobalTableListFilterPageableResponse;
import global.kajisaab.common.dto.PageableDto;
import global.kajisaab.common.utils.DateUtils;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.roles.dto.RolesTableListDto;
import global.kajisaab.feature.roles.repository.RolesRepository;
import global.kajisaab.feature.roles.specification.RolesFilterSpecification;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Singleton
@SuppressWarnings("rawtypes")
public class GetTableRolesListUseCase implements UseCase<GlobalTableListFilterPageableRequest, GlobalTableListFilterPageableResponse> {

    private final RolesRepository rolesRepository;

    @Inject
    public GetTableRolesListUseCase(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Mono<GlobalTableListFilterPageableResponse> execute(GlobalTableListFilterPageableRequest request) throws BadRequestException {

        var rolesListSpec = RolesFilterSpecification.of()
                .globalSearchParam(request.getSearchParam())
                .buildSpecification();

        var pageable = Pageable.from(
                request.getPage() - 1,
                request.getPageSize(),
                Sort.of(request.getSortOrder().equals("asc") ? Sort.Order.asc(request.getSortBy()) : Sort.Order.desc(request.getSortBy())
                ));

        return rolesRepository.findAll(rolesListSpec, pageable)
                .map(roles -> {

                    List<RolesTableListDto> list = roles.getContent().stream()
                            .map(detail -> new RolesTableListDto(
                                    detail.getId(),
                                    detail.getTitle(),
                                    Objects.requireNonNull(DateUtils.convertToUtcDate(detail.getCreatedOn().toString())).toString(),
                                    detail.getLastModifiedOn() != null
                                            ? Objects.requireNonNull(DateUtils.convertToUtcDate(detail.getLastModifiedOn().toString())).toString()
                                            : "-", // Default value for null case
                                    StatusEnum.getFromName(detail.getStatus()).getDisplayName()))
                            .toList();

                    // Ensure `list` is always present
                    if (list.isEmpty()) {
                        list = List.of(); // Return an empty list when there's no data
                    }

                    PageableDto pageableDto = new PageableDto(
                            roles.getPageNumber() + 1,
                            roles.getSize(),
                            roles.getTotalSize()
                    );

                    return new GlobalTableListFilterPageableResponse<>(list, pageableDto);

                });
    }
}
