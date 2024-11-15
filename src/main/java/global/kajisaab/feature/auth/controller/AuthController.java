package global.kajisaab.feature.auth.controller;

import global.kajisaab.core.responseHandler.ResponseHandler;
import global.kajisaab.feature.auth.dto.LoginUserCaseRequestDto;
import global.kajisaab.feature.auth.usecase.LoginUseCase;
import global.kajisaab.feature.auth.usecase.request.LoginUseCaseRequest;
import global.kajisaab.feature.auth.usecase.response.LoginUseCaseResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
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

    @Inject
    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Mono<HttpResponse<LoginUseCaseResponse>> login(@Valid @Body() LoginUserCaseRequestDto loginRequest, @Header("consultancyCode") String consultancyCode) {
        LoginUseCaseRequest request = new LoginUseCaseRequest(loginRequest.getEmail(), loginRequest.getPassword(), consultancyCode);
        return this.loginUseCase.execute(request).map(ResponseHandler::responseBuilder);
    }
}
