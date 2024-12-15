package global.kajisaab.feature.roles.usecase.response;

import global.kajisaab.core.usecase.UseCaseResponse;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;
import java.util.Map;

@Serdeable
public record GetPermissionUIUseCaseResponse(List<Map<String, Object>> list) implements UseCaseResponse {
}
