package global.kajisaab.core.permissionMiddleware.impl;

import global.kajisaab.common.constants.StatusEnum;
import global.kajisaab.core.multitenancy.SchemaBasedDataSourceResolver;
import global.kajisaab.feature.roles.entity.Roles;
import global.kajisaab.feature.roles.repository.RolesRepository;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@MicronautTest
class PermissionMiddlewareServiceImplTest {

    @MockBean(SchemaBasedDataSourceResolver.class)
    SchemaBasedDataSourceResolver mockDataSourceResolver() {
        SchemaBasedDataSourceResolver mockDataSourceResolver = mock(SchemaBasedDataSourceResolver.class);
        when(mockDataSourceResolver.resolveTenantSchemaName()).thenReturn("consultancy_000");
        return mockDataSourceResolver;
    }

    @MockBean(RolesRepository.class)
    RolesRepository mockedRolesRepository(){
        return mock(RolesRepository.class);
    }

    @Inject
    private RolesRepository mockedRolesRepository;

    @Inject
    private PermissionMiddlewareServiceImpl permissionMiddlewareServiceImpl;

    private final List<String> userRolesId = List.of(UUID.randomUUID().toString());

    @BeforeEach
    void setUp() {

    }

    @Test
    void validatePermission() {

        when(mockedRolesRepository.findAllByIdIn(any())).thenReturn(Flux.fromIterable(getRolesList("valid", userRolesId)));

         permissionMiddlewareServiceImpl.validatePermission(userRolesId, getPermissionsList())
                .subscribe(Assertions::assertTrue);

    }

    @Test
    void test_return_false_when_user_roles_is_wrong(){

        when(mockedRolesRepository.findAllByIdIn(any())).thenReturn(Flux.fromIterable(getRolesList("invalid", userRolesId)));

        permissionMiddlewareServiceImpl.validatePermission(userRolesId, getPermissionsList())
                .subscribe(Assertions::assertFalse);
    }

    @Test
    void test_return_false_when_user_roles_is_empty(){

        permissionMiddlewareServiceImpl.validatePermission(List.of(), getPermissionsList())
                .subscribe(Assertions::assertFalse);

    }

    private List<String> getPermissionsList(){
        return List.of("dashboard:cards", "roles:list", "roles:detail");
    }

    private List<String> getUserRolesList(){
        return userRolesId;
    }

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