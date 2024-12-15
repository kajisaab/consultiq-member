package global.kajisaab.feature.roles.controller;

import global.kajisaab.common.dto.GlobalTableListFilterPageableRequest;
import global.kajisaab.common.dto.GlobalTableListFilterPageableResponse;
import global.kajisaab.core.responseHandler.ResponseHandler;
import global.kajisaab.feature.roles.dto.RolesTableListDto;
import global.kajisaab.feature.roles.usecase.AddRoleUseCase;
import global.kajisaab.feature.roles.usecase.GetIndividualRoleDetailUseCase;
import global.kajisaab.feature.roles.usecase.GetPermissionUIUseCase;
import global.kajisaab.feature.roles.usecase.GetTableRolesListUseCase;
import global.kajisaab.feature.roles.usecase.request.AddRoleUseCaseRequest;
import global.kajisaab.feature.roles.usecase.request.GetIndividualRoleDetailUseCaseRequest;
import global.kajisaab.feature.roles.usecase.request.GetPermissionUIUseCaseRequest;
import global.kajisaab.feature.roles.usecase.response.AddRoleUseCaseResponse;
import global.kajisaab.feature.roles.usecase.response.GetIndividualRoleDetailUseCaseResponse;
import global.kajisaab.feature.roles.usecase.response.GetPermissionUIUseCaseResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller("/role")
@Tag(name = "Roles", description = "Handles all the roles related apis")
public class RolesController {

    private final GetTableRolesListUseCase getTableRolesListUseCase;

    private final GetIndividualRoleDetailUseCase getIndividualRoleDetailUseCase;

    private final GetPermissionUIUseCase getPermissionUIUseCase;

    private final SecurityService securityService;

    private final AddRoleUseCase addRoleUseCase;

    @Inject
    public RolesController(GetTableRolesListUseCase getTableRolesListUseCase, GetIndividualRoleDetailUseCase getIndividualRoleDetailUseCase, GetPermissionUIUseCase getPermissionUIUseCase, SecurityService securityService, AddRoleUseCase addRoleUseCase) {
        this.getTableRolesListUseCase = getTableRolesListUseCase;
        this.getIndividualRoleDetailUseCase = getIndividualRoleDetailUseCase;
        this.getPermissionUIUseCase = getPermissionUIUseCase;
        this.securityService = securityService;
        this.addRoleUseCase = addRoleUseCase;
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

    @Get("/ui")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public Mono<HttpResponse<GetPermissionUIUseCaseResponse>> getIndividualRoleDetail(){
        Authentication authenticationDetail = this.securityService.getAuthentication().orElseThrow(() ->
                new RuntimeException("User not authenticated")
        );

        Map<String, Object> attributes = authenticationDetail.getAttributes();

        String tenantId = (String) attributes.get("tenantId");

        return getPermissionUIUseCase.execute(new GetPermissionUIUseCaseRequest(tenantId)).map(ResponseHandler::responseBuilder);
    }

    @Post("/add")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public Mono<HttpResponse<AddRoleUseCaseResponse>> createRole(@Valid @Body()AddRoleUseCaseRequest request){
        return this.addRoleUseCase.execute(request).map(ResponseHandler::responseBuilder);
    }

}
