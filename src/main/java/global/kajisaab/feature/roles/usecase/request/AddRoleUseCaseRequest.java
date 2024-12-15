package global.kajisaab.feature.roles.usecase.request;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Serdeable
public record AddRoleUseCaseRequest(

        @NotBlank
        String name,

        @NotEmpty
        List<String> permissions,

        boolean isActive
) implements UseCaseRequest {
}
