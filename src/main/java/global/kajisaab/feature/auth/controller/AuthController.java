package global.kajisaab.feature.auth.controller;

import global.kajisaab.core.responseHandler.ResponseHandler;
import global.kajisaab.feature.auth.dto.LoginUserCaseRequestDto;
import global.kajisaab.feature.auth.usecase.LoginUseCase;
import global.kajisaab.feature.auth.usecase.LogoutUseCase;
import global.kajisaab.feature.auth.usecase.RefreshTokenUseCase;
import global.kajisaab.feature.auth.usecase.request.LoginUseCaseRequest;
import global.kajisaab.feature.auth.usecase.request.LogoutUseCaseRequest;
import global.kajisaab.feature.auth.usecase.request.RefreshTokenUseCaseRequest;
import global.kajisaab.feature.auth.usecase.response.LoginUseCaseResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Controller("/auth")
@Tag(name = "Authentications", description = "Handles all the authentication related apis")
public class AuthController {

    private final LoginUseCase loginUseCase;

    private final RefreshTokenUseCase refreshTokenUseCase;

    private final LogoutUseCase logoutUseCase;

    @Inject
    public AuthController(LoginUseCase loginUseCase, RefreshTokenUseCase refreshTokenUseCase, LogoutUseCase logoutUseCase) {
        this.loginUseCase = loginUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Mono<HttpResponse<LoginUseCaseResponse>> login(@Valid @Body() LoginUserCaseRequestDto loginRequest, @Header("consultancyCode") String consultancyCode) {

        LoginUseCaseRequest request = new LoginUseCaseRequest(loginRequest.getEmail(), loginRequest.getPassword(), consultancyCode);

        return this.loginUseCase.execute(request).map(ResponseHandler::responseBuilder);
    }

    @Get("/refresh-token")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public Mono<HttpResponse<?>> getRefreshToken(){
        return this.refreshTokenUseCase.execute(new RefreshTokenUseCaseRequest()).map(ResponseHandler::responseBuilder);
    }

    @Get("/logout")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public Mono<HttpResponse<?>> logout(){
        return this.logoutUseCase.execute(new LogoutUseCaseRequest()).map(ResponseHandler::responseBuilder);
    }
}
