package global.kajisaab.feature.consultancy.controller;

import global.kajisaab.core.responseHandler.ResponseHandler;
import global.kajisaab.feature.consultancy.usecase.ConsultancyOnboardingUseCase;
import global.kajisaab.feature.consultancy.usecase.request.ConsultancyOnboardingUseCaseRequest;
import global.kajisaab.feature.consultancy.usecase.response.ConsultancyOnboardingUseCaseResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Controller("/consultancy")
@Tag(name = "Consultancy", description = "Handles all the consultancy related apis")
public class ConsultancyController {

    private final ConsultancyOnboardingUseCase consultancyOnboardingUseCase;

    @Inject
    public ConsultancyController(ConsultancyOnboardingUseCase consultancyOnboardingUseCase) {
        this.consultancyOnboardingUseCase = consultancyOnboardingUseCase;
    }

    @Post("/onboarding")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Mono<HttpResponse<ConsultancyOnboardingUseCaseResponse>> consultancyOnboarding(@Valid @Body ConsultancyOnboardingUseCaseRequest request){
     return this.consultancyOnboardingUseCase.execute(request).map(ResponseHandler::responseBuilder);
    }
}
