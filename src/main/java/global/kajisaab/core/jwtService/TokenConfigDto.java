package global.kajisaab.core.jwtService;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class TokenConfigDto{

    @Value("${micronaut.security.token.jwt.signatures.secret.generator.secret}")
    private String signature;

    @Value("${micronaut.security.token.jwt.signatures.secret.generator.expiration}")
    private Long accessTokenExpirationTime;

    @Value("${micronaut.security.token.jwt.signatures.secret.validator.expiration}")
    private int refreshTokenExpirationTime;

    public String getSignature() {
        return signature;
    }

    public Long getAccessTokenExpirationTime() {
        return accessTokenExpirationTime;
    }

    public int getRefreshTokenExpirationTime() {
        return refreshTokenExpirationTime;
    }


}
