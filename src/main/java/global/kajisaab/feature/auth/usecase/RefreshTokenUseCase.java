package global.kajisaab.feature.auth.usecase;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.exceptionHandling.UnauthorizedException;
import global.kajisaab.core.jwtService.JwtService;
import global.kajisaab.core.redis.RedisCacheService;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import global.kajisaab.feature.auth.usecase.request.RefreshTokenUseCaseRequest;
import global.kajisaab.feature.auth.usecase.response.RefreshTokenUseCaseResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Singleton
public class RefreshTokenUseCase implements UseCase<RefreshTokenUseCaseRequest, RefreshTokenUseCaseResponse> {

    private final JwtService jwtService;

    private final RedisCacheService redisCacheService;

    @Inject
    public RefreshTokenUseCase(JwtService jwtService, RedisCacheService redisCacheService) {
        this.jwtService = jwtService;
        this.redisCacheService = redisCacheService;
    }

    /**
     * Execute the refresh token use case by getting the current request, extracting the user details and tenant id from the request,
     * and generating the necessary access token.
     *
     * @param request The empty refresh token request
     * @return A Mono containing the access token response.
     * @throws UnauthorizedException If the user is not authenticated.
     */
    @Override
    public Mono<RefreshTokenUseCaseResponse> execute(RefreshTokenUseCaseRequest request) throws BadRequestException {

        Optional<HttpRequest<Object>> currentRequest = ServerRequestContext.currentRequest();

        Optional<Authentication> authentication = currentRequest
                .flatMap(HttpRequest::getUserPrincipal)
                .map(Authentication.class::cast);

        if (authentication.isEmpty()) {
            return Mono.error(new UnauthorizedException("User not authenticated"));
        }

        UserDetailsEntity userDetails = (UserDetailsEntity) authentication.get().getAttributes().get("userDetails");

        String tenantId = (String) authentication.get().getAttributes().get("tenantId");

        return Mono.just(userDetails)
                .doOnNext(res -> this.redisCacheService.updateCacheExpiration(userDetails.getId(), 600))
                .map(user -> jwtService.generateAccessToken(user, tenantId))
                .map(RefreshTokenUseCaseResponse::new);

    }
}
