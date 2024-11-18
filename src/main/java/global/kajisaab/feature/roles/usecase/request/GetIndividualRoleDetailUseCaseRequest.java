package global.kajisaab.feature.roles.usecase.request;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
public record GetIndividualRoleDetailUseCaseRequest (
        String id
) implements UseCaseRequest {
}
