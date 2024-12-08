package global.kajisaab.core.permissionMiddleware.impl;

import global.kajisaab.common.constants.StatusEnum;
import global.kajisaab.common.dto.LabelValuePair;
import global.kajisaab.core.exceptionHandling.PermissionDeniedException;
import global.kajisaab.core.multitenancy.SchemaBasedDataSourceResolver;
import global.kajisaab.core.multitenancy.TenantContext;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import global.kajisaab.feature.roles.entity.Roles;
import io.micronaut.http.HttpRequest;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MicronautTest
class PermissionMiddlewareImplTest {

    // Logger instance for logging test-related information
    private final Logger LOG = LoggerFactory.getLogger(PermissionMiddlewareImplTest.class);

    // Mock SchemaBasedDataSourceResolver to simulate tenant schema resolution
    @MockBean(SchemaBasedDataSourceResolver.class)
    SchemaBasedDataSourceResolver mockDataSourceResolver() {
        SchemaBasedDataSourceResolver mockDataSourceResolver = mock(SchemaBasedDataSourceResolver.class);
        when(mockDataSourceResolver.resolveTenantSchemaName()).thenReturn("consultancy_001");
        return mockDataSourceResolver;
    }

    // Mock UserDetailsEntity for providing user data
    @MockBean(UserDetailsEntity.class)
    UserDetailsEntity mockedUserDetailsEntity() {
        UserDetailsEntity mockedUserDetailsEntity = mock(UserDetailsEntity.class);

        when(mockedUserDetailsEntity.getRoles()).thenReturn(List.of(new LabelValuePair("Managing Director", "b6a35294-5bb4-4966-a19f-ac7c945982f0")));

        return mockedUserDetailsEntity;
    }

//    // Mock PermissionMiddlewareServiceImpl for permission handling logic
    @MockBean(PermissionMiddlewareServiceImpl.class)
    PermissionMiddlewareServiceImpl mockedPermissionMiddlewareServiceImpl() {
        return mock(PermissionMiddlewareServiceImpl.class);
    }

    @Inject
    private PermissionMiddlewareServiceImpl mockedPermissionMiddlewareServiceImpl;

    // Inject the class under test
    @Inject
    private PermissionMiddlewareImpl permissionMiddleware;

    // Initialize any necessary configurations or mock setups before each test
    @BeforeEach
    void setUp() {
    }

    // Test for successfully setting the tenant context for a public route
    @Test
    void test_set_tenantContext_for_public_route() {
        HttpRequest<?> mockedHttpRequest = mock(HttpRequest.class);

        // Simulate a public route request
        when(mockedHttpRequest.getUri()).thenReturn(java.net.URI.create("http://localhost:8080/api/v1/auth/login"));

        Mono<Void> result = permissionMiddleware.validateAuthorization(
                mockedUserDetailsEntity(),
                mockedHttpRequest,
                "000"
        );

        // Verify the reactive stream completes without errors
        StepVerifier.create(result)
                .verifyComplete();

        // Assert that the tenant context is correctly set
        String tenantId = TenantContext.getCurrentTenant();
        assertEquals("000", tenantId);
    }

    // Test case for failed authorization due to invalid URL
    @Test
    void test_failed_authorization_invalid_url() {
        HttpRequest<?> mockedHttpRequest = mock(HttpRequest.class);

        // Simulate a request to an unauthorized route
        when(mockedHttpRequest.getUri()).thenReturn(java.net.URI.create("http://localhost:8080/api/v1/profile"));
        when(mockedHttpRequest.getMethodName()).thenReturn("GET");

        Mono<Void> result = permissionMiddleware.validateAuthorization(
                mockedUserDetailsEntity(),
                mockedHttpRequest,
                "000"
        );

        // Verify the exception and its message
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof PermissionDeniedException &&
                                "You are not authorized to access this resource".equals(throwable.getMessage())
                )
                .verify();
    }

    @Test
    void test_failed_authorization_invalid_role(){
        HttpRequest<?> mockedHttpRequest = mock(HttpRequest.class);

        // Correctly stub the validatePermission method
        when(mockedPermissionMiddlewareServiceImpl.validatePermission(anyList(), anyList()))
                .thenReturn(Mono.just(false));   // Return Mono.just(false) for this stub

        // Simulate a request to an unauthorized route
        when(mockedHttpRequest.getUri()).thenReturn(java.net.URI.create("/api/v1/dashboard/cards"));
        when(mockedHttpRequest.getMethodName()).thenReturn("GET");

        Mono<Void> result = permissionMiddleware.validateAuthorization(
                mockedUserDetailsEntity(),
                mockedHttpRequest,
                "001"
        );

        // Verify the exception and its message
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof PermissionDeniedException &&
                                "You are not authorized to access this resource".equals(throwable.getMessage())
                )
                .verify();
    }

    @Test
    void test_failed_authorization_valid_role(){
        HttpRequest<?> mockedHttpRequest = mock(HttpRequest.class);

        // Correctly stub the validatePermission method
        when(mockedPermissionMiddlewareServiceImpl.validatePermission(anyList(), anyList()))
                .thenReturn(Mono.just(true));   // Return Mono.just(false) for this stub

        // Simulate a request to an unauthorized route
        when(mockedHttpRequest.getUri()).thenReturn(java.net.URI.create("/api/v1/dashboard/cards"));
        when(mockedHttpRequest.getMethodName()).thenReturn("GET");

        Mono<Void> result = permissionMiddleware.validateAuthorization(
                mockedUserDetailsEntity(),
                mockedHttpRequest,
                "001"
        );

        // Verify the exception and its message
        StepVerifier.create(result)
                .verifyComplete();
    }

    // Helper method to generate a list of roles based on the type
    private List<Roles> getRolesList(String type, List<String> userRolesIdList){

        Roles invalid_role_1 = new Roles();
        invalid_role_1.setId(UUID.randomUUID().toString());
        invalid_role_1.setTitle("Invalid Role 1");
        invalid_role_1.setPermissions(List.of("user"));
        invalid_role_1.setStatus(StatusEnum.ACTIVE.name());
        invalid_role_1.setActive(true);

        Roles valid_super_role = new Roles();
        valid_super_role.setId(userRolesIdList.getFirst());
        valid_super_role.setTitle("Managing Director");
        valid_super_role.setPermissions(List.of("dashboard:cards", "roles:list", "roles:detail"));
        valid_super_role.setStatus(StatusEnum.ACTIVE.name());
        valid_super_role.setActive(true);

        if(type.equalsIgnoreCase("valid")){
            return List.of(valid_super_role);
        }

        return List.of(invalid_role_1);

    }
}