//package global.kajisaab.core.customAuthenticationProvider;
//
//import global.kajisaab.core.jwtService.JwtService;
//import io.micronaut.test.annotation.MockBean;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static org.mockito.Mockito.*;
//
//class CustomAuthenticationProviderTest{
//
//    private final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProviderTest.class);
//
//    @Inject
//    @SuppressWarnings("rawtypes")
//    private CustomAuthenticationProvider customAuthenticationProvider;
//
//    @MockBean
//    JwtService mockedJwtService(){
//        return mock(JwtService.class);
//    }
//
//    @BeforeEach
//    void setUp(){}
//
//    @Test
//    void testValidationAccessToken_Success(){}
//
//    @Test
//    void testValidateToken_InvalidToken_ThrowsBadRequestException(){}
//
//    @Test
//    void testValidateRefreshToken_TokenExpired_ThrowsUnauthorizedException(){}
//
//    @Test
//    void testValidateAccessToken_UserNotFound_ThrowsUnauthorizedException(){}
//
//    @Test
//    void testValidateAccessToken_PermissionDenied_ThrowsUnauthorizedException(){}
//}