package global.kajisaab.core.jwtService;

import global.kajisaab.common.utils.DateUtils;
import global.kajisaab.common.constants.TokenTypeEnum;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.exceptionHandling.UnauthorizedException;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

@Singleton
public class JwtService {

    private final TokenConfigDto tokenConfigDto;

    private final JwtTokenGenerator jwtTokenGenerator;

    private final Logger LOG = LoggerFactory.getLogger(JwtService.class);

    @Inject
    public JwtService(TokenConfigDto tokenConfigDto, JwtTokenGenerator jwtTokenGenerator) {
        this.tokenConfigDto = tokenConfigDto;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public Map<String, Object> parseToken(String token) throws UnauthorizedException {
        try {
            SecretKey key = Keys.hmacShaKeyFor(tokenConfigDto.getSignature().getBytes(StandardCharsets.UTF_8));

            JwtParser parser = Jwts.parser().verifyWith(key).build();

            Jws<Claims> claims = parser.parseSignedClaims(token);

            return claims.getPayload();

        } catch (Exception e) {
            LOG.error("Invalid Token {}", e.getMessage());
            throw new UnauthorizedException("Token is invalid");
        }
    }

    public String generateAccessToken(UserDetailsEntity userDetails, String tenantId) throws BadRequestException {
        Instant instantAfter10Minutes = DateUtils.getCurrentUtcDateTime()
                .plusSeconds(tokenConfigDto.getAccessTokenExpirationTime())
                .toInstant(ZoneOffset.UTC);
        long exp = instantAfter10Minutes.getEpochSecond();

        String schema = "consultancy_" + tenantId;

        Map<String, Object> claims = Map.of(
                "userEmail", userDetails.getEmail(),
                "schema", schema,
                "tenantId", tenantId,
                "tokenType", TokenTypeEnum.ACCESS_TOKEN.name(),
                "exp", exp
        );
        try {
            // Generate the token
            Optional<String> token = this.jwtTokenGenerator.generateToken(claims);

            // Check if the token is empty and throw an exception if it is
            if (token.isEmpty()) {
                throw new BadRequestException("Error generating access token: Token is empty");
            }

            // Otherwise, assign the token value
            return token.get();  // This will throw NoSuchElementException if the Optional is empty (but we already check it above)

        } catch (Exception e) {
            // Catch any exception and log it
            LOG.error("Error generating access token {}", e.getMessage());
            throw new BadRequestException("Error generating access token");
        }
    }

    public String generateRefreshToken(UserDetailsEntity userDetails, String tenantId) throws BadRequestException {
        Instant instantAfter30Days = DateUtils.getDateTimeAfterDays(tokenConfigDto.getRefreshTokenExpirationTime());

        long exp = instantAfter30Days.getEpochSecond();
        String schema = "consultancy_" + tenantId;

        Map<String, Object> refreshTokenClaims = Map.of(
                "userEmail", userDetails.getEmail(),
                "schema", schema,
                "tenantId", tenantId,
                "tokenType", TokenTypeEnum.REFRESH_TOKEN.name(),
                "exp", exp
        );
        try {
            // Generate the token
            Optional<String> token = this.jwtTokenGenerator.generateToken(refreshTokenClaims);

            // Check if the token is empty and throw an exception if it is
            if (token.isEmpty()) {
                throw new BadRequestException("Error generating refresh token: Token is empty");
            }

            // Otherwise, assign the token value
            return token.get();  // This will throw NoSuchElementException if the Optional is empty (but we already check it above)

        } catch (Exception e) {
            // Catch any exception and log it
            LOG.error("Error generating Refresh token {}", e.getMessage());
            throw new BadRequestException("Error generating refresh token");
        }
    }
}
