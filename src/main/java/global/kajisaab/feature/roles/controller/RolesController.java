package global.kajisaab.feature.roles.controller;

import global.kajisaab.common.dto.GlobalTableListFilterPageableRequest;
import global.kajisaab.common.dto.GlobalTableListFilterPageableResponse;
import global.kajisaab.core.responseHandler.ResponseHandler;
import global.kajisaab.feature.roles.dto.RolesTableListDto;
import global.kajisaab.feature.roles.usecase.GetIndividualRoleDetailUseCase;
import global.kajisaab.feature.roles.usecase.GetTableRolesListUseCase;
import global.kajisaab.feature.roles.usecase.request.GetIndividualRoleDetailUseCaseRequest;
import global.kajisaab.feature.roles.usecase.response.GetIndividualRoleDetailUseCaseResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

@Controller("/role")
@Tag(name = "Roles", description = "Handles all the roles related apis")
public class RolesController {

    private final GetTableRolesListUseCase getTableRolesListUseCase;

    private final GetIndividualRoleDetailUseCase getIndividualRoleDetailUseCase;

    @Inject
    public RolesController(GetTableRolesListUseCase getTableRolesListUseCase, GetIndividualRoleDetailUseCase getIndividualRoleDetailUseCase) {
        this.getTableRolesListUseCase = getTableRolesListUseCase;
        this.getIndividualRoleDetailUseCase = getIndividualRoleDetailUseCase;
    }

    @Post("/list")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public Mono<HttpResponse<GlobalTableListFilterPageableResponse<RolesTableListDto>>> getRolesTableList(@Body() GlobalTableListFilterPageableRequest request){
        return this.getTableRolesListUseCase.execute(request).map(ResponseHandler::responseBuilder);
    }

    @Get("/detail/{id}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public Mono<HttpResponse<GetIndividualRoleDetailUseCaseResponse>> getIndividualRoleDetail(@PathVariable("id") String id){
        return getIndividualRoleDetailUseCase.execute(new GetIndividualRoleDetailUseCaseRequest(id)).map(ResponseHandler::responseBuilder);
    }

}
