package global.kajisaab.core.multitenancy;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.runtime.multitenancy.SchemaTenantResolver;
import io.micronaut.http.MediaType;
import io.micronaut.http.context.ServerRequestContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.Serializable;

@Singleton
public class SchemaBasedDataSourceResolver implements SchemaTenantResolver {

    private final TokenBasedMultiTenancyHandler tokenBasedMultiTenancyHandler;

    private final SchemaExistenceService schemaExistenceService;

    @Inject
    public SchemaBasedDataSourceResolver(TokenBasedMultiTenancyHandler tokenBasedMultiTenancyHandler, SchemaExistenceService schemaExistenceService) {
        this.tokenBasedMultiTenancyHandler = tokenBasedMultiTenancyHandler;
        this.schemaExistenceService = schemaExistenceService;
    }


    @Override
    public @Nullable String resolveTenantSchemaName() {
        Serializable tenantId = tokenBasedMultiTenancyHandler.resolveTenantIdentifier();

        if (tenantId == null) {
            throw new BadRequestException("Tenant ID is required but not found.");
        }

        // Check if the schema exists for the given tenantId
        boolean schemaExists = schemaExistenceService.doesSchemaExist(tenantId.toString());
        if (!schemaExists) {
            throw new BadRequestException("Invalid Consultancy Code");
        }

        return "consultancy_" + tenantId;
    }

    public boolean isSseRequest() {
        return ServerRequestContext.currentRequest()
                .map(request -> request.getHeaders().accept())
                .map(mediaTypes -> mediaTypes.contains(MediaType.TEXT_EVENT_STREAM_TYPE))
                .orElse(false);
    }
}