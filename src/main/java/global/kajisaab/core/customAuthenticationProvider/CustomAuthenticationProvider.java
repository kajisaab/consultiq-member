package global.kajisaab.core.customAuthenticationProvider;

import global.kajisaab.common.utils.DateUtils;
import global.kajisaab.common.constants.TokenTypeEnum;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.exceptionHandling.PermissionDeniedException;
import global.kajisaab.core.exceptionHandling.UnauthorizedException;
import global.kajisaab.core.jwtService.JwtService;
import global.kajisaab.core.permissionMiddleware.PermissionMiddleware;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import global.kajisaab.feature.auth.repository.UserDetailsRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
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
        try {

            Map<String, Object> claims = this.jwtService.parseToken(token);

            if (claims.get("tokenType").equals(TokenTypeEnum.REFRESH_TOKEN.name())) {

                return validateRefreshToken(claims);
            }

            return validateAccessToken(claims);

        } catch (Exception e) {

            throw new BadRequestException("Invalid Token " + e.getMessage());
        }
    }

    ;

    private Mono<Authentication> validateAccessToken(Map<String, Object> claims) {
        // Defer to run the permission validation asynchronously
        String email = (String) claims.get("userEmail");
        return userDetailsRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User not found")))
                .flatMap(userDetails -> permissionMiddleware.validateAuthorization(userDetails, "")
                        .onErrorMap(PermissionDeniedException.class, e -> new UnauthorizedException("Permission Denied"))
                        .then(Mono.defer(() -> {
                            // This will only run after permission validation succeeds
                            if (DateUtils.isBeforeNow((Long) claims.get("exp"))) {
                                return Mono.error(new UnauthorizedException("Token Expired"));
                            }
                            return Mono.just(prepareAuthenticationResponse(claims, userDetails));
                        })));

    }

    private Mono<Authentication> validateRefreshToken(Map<String, Object> claims) throws UnauthorizedException {

        String email = (String) claims.get("userEmail");

        return userDetailsRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User not found")))
                .handle((userDetails, sink) -> {
                    if (DateUtils.isBeforeNow((Long) claims.get("exp"))) {
                        sink.error(new UnauthorizedException("Token Expired"));
                        return;
                    }
                    sink.next(prepareAuthenticationResponse(claims, userDetails));
                });
    }

    private Authentication prepareAuthenticationResponse(Map<String, Object> claims, UserDetailsEntity userDetails) {

        List<String> roles = new ArrayList<>();

        Map<String, Object> parsedTokenClaims = new HashMap<>();

        parsedTokenClaims.put("token", claims.get("tokenType"));
        parsedTokenClaims.put("userDetails", userDetails);

        userDetails.getRoles().forEach(role -> roles.add(role.label()));

        return Authentication.build(userDetails.toString(), roles, parsedTokenClaims);
    }
}
