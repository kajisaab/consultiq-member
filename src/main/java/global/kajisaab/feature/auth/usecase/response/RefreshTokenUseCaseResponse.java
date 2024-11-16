package global.kajisaab.feature.auth.usecase.response;

import global.kajisaab.core.usecase.UseCaseResponse;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record RefreshTokenUseCaseResponse(String accessToken) implements UseCaseResponse {
}
