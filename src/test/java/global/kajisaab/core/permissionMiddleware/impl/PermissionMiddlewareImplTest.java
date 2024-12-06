package global.kajisaab.core.permissionMiddleware.impl;

import global.kajisaab.common.constants.StatusEnum;
import global.kajisaab.common.dto.LabelValuePair;
import global.kajisaab.core.exceptionHandling.PermissionDeniedException;
import global.kajisaab.core.multitenancy.SchemaBasedDataSourceResolver;
import global.kajisaab.core.multitenancy.TenantContext;
import global.kajisaab.feature.auth.entity.UserDetailsEntity;
import global.kajisaab.feature.roles.entity.Roles;
import global.kajisaab.feature.roles.repository.RolesRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MicronautTest
class PermissionMiddlewareImplTest {

    private final Logger LOG = LoggerFactory.getLogger(PermissionMiddlewareImplTest.class);

    @MockBean(SchemaBasedDataSourceResolver.class)
    SchemaBasedDataSourceResolver mockDataSourceResolver() {
        SchemaBasedDataSourceResolver mockDataSourceResolver = mock(SchemaBasedDataSourceResolver.class);
        when(mockDataSourceResolver.resolveTenantSchemaName()).thenReturn("consultancy_000");
        return mockDataSourceResolver;
    }

    @MockBean
    UserDetailsEntity mockedUserDetailsEntity(){
        return mock(UserDetailsEntity.class);
    }

    @MockBean
    PermissionMiddlewareServiceImpl mockedPermissionMiddlewareServiceImpl(){
        return mock(PermissionMiddlewareServiceImpl.class);
    }

    @MockBean
    RolesRepository mockedRolesRepository(){
        return mock(RolesRepository.class);
    }

    @Inject
    private PermissionMiddlewareImpl permissionMiddleware;

    @BeforeEach
    void setUp() {
    }

    @Test
    void test_set_tenantContext_for_public_route(){

        HttpRequest<?> mockedHttpRequest = mock(HttpRequest.class);

        when(mockedHttpRequest.getUri()).thenReturn(java.net.URI.create("http://localhost:8080/api/v1/auth/login"));

        Mono<Void> result = permissionMiddleware.validateAuthorization(
                mockedUserDetailsEntity(),
                mockedHttpRequest,
                "000"
        );

        StepVerifier.create(result)
                .verifyComplete();

        String tenantId = TenantContext.getCurrentTenant();

        assertEquals("000", tenantId);
    }

    @Test
    void test_failed_authorization_invalid_url(){

        HttpRequest<?> mockedHttpRequest = mock(HttpRequest.class);

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
    void test_failed_authorization_wrong_permission(){
        HttpRequest<?> mockedHttpRequest = mock(HttpRequest.class);

        when(mockedHttpRequest.getUri()).thenReturn(java.net.URI.create("http://localhost:8080/api/v1/route/list"));
        when(mockedHttpRequest.getMethodName()).thenReturn("GET");

        List<LabelValuePair> userRoleList = List.of(new LabelValuePair("Wrong Role", "wrong-role"));

        when(mockedUserDetailsEntity().getRoles()).thenReturn(userRoleList);

        when(mockedRolesRepository().findAllByIdIn(any())).thenReturn(Flux.fromIterable(getRolesList("invalid")));

        Mono<Void> result = permissionMiddleware.validateAuthorization(
                mockedUserDetailsEntity(),
                mockedHttpRequest,
                "000"
        );

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof PermissionDeniedException &&
                                "You are not authorized to access this resource".equals(throwable.getMessage())
                )
                .verify();

    }


    private List<Roles> getRolesList(String type){

        Roles invalid_role_1 = new Roles();
        invalid_role_1.setTitle("Invalid Role 1");
        invalid_role_1.setPermissions(List.of("user"));
        invalid_role_1.setStatus(StatusEnum.ACTIVE.name());
        invalid_role_1.setActive(true);

        Roles valid_super_role = new Roles();
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