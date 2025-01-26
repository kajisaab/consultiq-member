package global.kajisaab.feature.roles.usecase.request;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record DeleteRoleUseCaseRequest(String id) implements UseCaseRequest {
}
