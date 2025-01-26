package global.kajisaab.feature.roles.usecase.request;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public record UpdateRoleUseCaseRequest(

        String id,

        String name,

        List<String> roles
) implements UseCaseRequest {
}
