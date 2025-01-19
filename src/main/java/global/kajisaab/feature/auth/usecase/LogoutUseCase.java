package global.kajisaab.feature.auth.usecase;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.redis.RedisCacheService;
import global.kajisaab.core.usecase.UseCase;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import global.kajisaab.feature.auth.usecase.request.LogoutUseCaseRequest;
import global.kajisaab.feature.auth.usecase.response.LogoutUseCaseResponse;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.Map;

public class LogoutUseCase implements UseCase<LogoutUseCaseRequest, LogoutUseCaseResponse> {

    private final SecurityService securityService;

    private final RedisCacheService redisCacheService;

    @Inject
    public LogoutUseCase(SecurityService securityService, RedisCacheService redisCacheService) {
        this.securityService = securityService;
        this.redisCacheService = redisCacheService;
    }

    @Override
    public Mono<LogoutUseCaseResponse> execute(LogoutUseCaseRequest request) throws BadRequestException {
        Authentication authenticationDetail = this.securityService.getAuthentication().orElseThrow(() ->
                new RuntimeException("User not authenticated")
        );

        Map<String, Object> attributes = authenticationDetail.getAttributes();

        UserDetailsEntity userDetailsEntity = (UserDetailsEntity) attributes.get("userDetails");

        if(this.redisCacheService.isUserCached(userDetailsEntity.getId())){
            this.redisCacheService.removeUser(userDetailsEntity.getId());
            return Mono.just(new LogoutUseCaseResponse("Successfully logged out"));
        } else {
            throw new BadRequestException("User is not logged in");
        }

    }
}
