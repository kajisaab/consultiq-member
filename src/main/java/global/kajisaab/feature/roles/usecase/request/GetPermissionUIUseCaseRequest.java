package global.kajisaab.feature.roles.usecase.request;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Serdeable
@RecordBuilder
public record GetPermissionUIUseCaseRequest(String tenantId) implements UseCaseRequest {
}
