package global.kajisaab.core.customAuthenticationProvider;

import global.kajisaab.common.utils.DateUtils;
import global.kajisaab.common.constants.TokenTypeEnum;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.exceptionHandling.PermissionDeniedException;
import global.kajisaab.core.exceptionHandling.UnauthorizedException;
import global.kajisaab.core.jwtService.JwtService;
import global.kajisaab.core.multitenancy.TenantContext;
import global.kajisaab.core.permissionMiddleware.PermissionMiddleware;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import global.kajisaab.feature.auth.repository.UserDetailsRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.micronaut.security.token.validator.TokenValidator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
class CustomAuthenticationProvider<B> implements TokenValidator<B> {

    private final JwtService jwtService;

    private final PermissionMiddleware permissionMiddleware;

    private final UserDetailsRepository userDetailsRepository;

    @Inject
    CustomAuthenticationProvider(JwtService jwtService, PermissionMiddleware permissionMiddleware, UserDetailsRepository userDetailsRepository) {
        this.jwtService = jwtService;
        this.permissionMiddleware = permissionMiddleware;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public @NonNull Publisher<Authentication> validateToken(@NonNull String token, @Nullable B request) {

        HttpRequest<?> requestDetail = (HttpRequest<?>) request;

        try {

            Map<String, Object> claims = this.jwtService.parseToken(token);

            TenantContext.setCurrentTenant((String) claims.get("tenantId"));

            if (claims.get("tokenType").equals(TokenTypeEnum.REFRESH_TOKEN.name())) {

                return validateRefreshToken(claims,requestDetail);
            }

            return validateAccessToken(claims,requestDetail);

        } catch (Exception e) {

            throw new BadRequestException("Invalid Token " + e.getMessage());
        }
    }

    ;

    private Mono<Authentication> validateAccessToken(Map<String, Object> claims, HttpRequest<?> currentRequest) {
        // Defer to run the permission validation asynchronously
        String email = (String) claims.get("userEmail");
        return userDetailsRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User not found")))
                .flatMap(userDetails -> permissionMiddleware.validateAuthorization(userDetails, "", currentRequest,(String) claims.get("tenantId"))
                        .onErrorMap(PermissionDeniedException.class, e -> new UnauthorizedException("Permission Denied"))
                        .then(Mono.defer(() -> {
                            // This will only run after permission validation succeeds
                            if (DateUtils.isBeforeNow((Long) claims.get("exp"))) {
                                return Mono.error(new UnauthorizedException("Token Expired"));
                            }
                            return Mono.just(prepareAuthenticationResponse(claims, userDetails));
                        })));

    }

    private Mono<Authentication> validateRefreshToken(Map<String, Object> claims, HttpRequest<?> currentRequest) throws UnauthorizedException {

        String email = (String) claims.get("userEmail");

        return userDetailsRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User not found")))
                .flatMap((userDetails) -> {
                    if (DateUtils.isBeforeNow((Long) claims.get("exp"))) {
                        return Mono.error(new UnauthorizedException("Refresh Token Expired"));
                    }
                    return Mono.just(prepareAuthenticationResponse(claims, userDetails));
                });
    }

    private Authentication prepareAuthenticationResponse(Map<String, Object> claims, UserDetailsEntity userDetails) {

        List<String> roles = new ArrayList<>();

        Map<String, Object> parsedTokenClaims = new HashMap<>();

        parsedTokenClaims.put("token", claims.get("tokenType"));
        parsedTokenClaims.put("userDetails", userDetails);
        parsedTokenClaims.put("tenantId", claims.get("tenantId"));

        userDetails.getRoles().forEach(role -> roles.add(role.label()));

        return Authentication.build(userDetails.getEmail(), roles, parsedTokenClaims);
    }
}
