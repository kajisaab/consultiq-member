package global.kajisaab.feature.roles.usecase.response;

import global.kajisaab.core.usecase.UseCaseResponse;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AddRoleUseCaseResponse(String message) implements UseCaseResponse {
}
