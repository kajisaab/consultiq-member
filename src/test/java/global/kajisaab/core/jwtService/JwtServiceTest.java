package global.kajisaab.core.jwtService;

import global.kajisaab.common.constants.TokenTypeEnum;
import global.kajisaab.core.exceptionHandling.UnauthorizedException;
import global.kajisaab.core.multitenancy.SchemaBasedDataSourceResolver;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import io.micronaut.context.annotation.Requires;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
@Requires(env = "test")
class JwtServiceTest {

    private final Logger LOG = LoggerFactory.getLogger(JwtServiceTest.class);

    @MockBean(SchemaBasedDataSourceResolver.class)
    SchemaBasedDataSourceResolver mockDataSourceResolver() {
        SchemaBasedDataSourceResolver mockDataSourceResolver = mock(SchemaBasedDataSourceResolver.class);
        when(mockDataSourceResolver.resolveTenantSchemaName()).thenReturn("consultancy_000");
        return mockDataSourceResolver;
    }

    @Inject
    private JwtService jwtService;

    String tenantId = "000";

    @MockBean(TokenConfigDto.class)
    TokenConfigDto mockTokenConfigDto() {
        TokenConfigDto mockConfigDto = mock(TokenConfigDto.class);
        when(mockConfigDto.getSignature()).thenReturn("df3d25e6b2209fe4867e4061b3a6797dfebf8425ed7da69bbc3c15035ee1e85a");
        when(mockConfigDto.getAccessTokenExpirationTime()).thenReturn(600L);
        when(mockConfigDto.getRefreshTokenExpirationTime()).thenReturn(3600);
        return mockConfigDto;
    }

    @MockBean(UserDetailsEntity.class)
    UserDetailsEntity mockUserDetailsEntity() {
        UserDetailsEntity mockUserDetailsEntity = mock(UserDetailsEntity.class);
        when(mockUserDetailsEntity.getId()).thenReturn(UUID.randomUUID().toString());
        when(mockUserDetailsEntity.getEmail()).thenReturn("amankhadka101@gmail.com");
        return mockUserDetailsEntity;
    }

    @Test
    void test_successful_access_token_generation(){

        UserDetailsEntity userDetails = mockUserDetailsEntity();

        String accessToken = this.jwtService.generateAccessToken(userDetails, tenantId);

        assertNotNull(accessToken);

        Map<String, Object> parsedToken = this.jwtService.parseToken(accessToken);

        String tokenType = (String) parsedToken.get("tokenType");

        assertEquals(tokenType, TokenTypeEnum.ACCESS_TOKEN.name());
    }

    @Test
    void test_successful_refresh_token_generation(){

        UserDetailsEntity userDetails = mockUserDetailsEntity();

        String refreshToken = this.jwtService.generateRefreshToken(userDetails, tenantId);

        assertNotNull(refreshToken);

        Map<String, Object> parsedToken = this.jwtService.parseToken(refreshToken);

        String tokenType = (String) parsedToken.get("tokenType");

        assertEquals(tokenType, TokenTypeEnum.REFRESH_TOKEN.name());
    }

    @Test
    void test_failed_token_parse(){

        String invalidToken = "invalid_token";

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> this.jwtService.parseToken(invalidToken));

        assertEquals("Token is invalid", exception.getMessage());
    }
}