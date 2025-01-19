package global.kajisaab.feature.auth.usecase.request;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record LogoutUseCaseRequest () implements UseCaseRequest {
}
