package global.kajisaab.feature.roles.usecase.response;

import global.kajisaab.core.usecase.UseCaseResponse;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public record GetIndividualRoleDetailUseCaseResponse(
        String id,
        String title,
        String createdOn,
        String createdBy,
        String lastModifiedOn,
        String status,
        List<String> permission
) implements UseCaseResponse {
}
