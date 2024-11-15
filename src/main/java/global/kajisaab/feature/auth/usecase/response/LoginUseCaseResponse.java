package global.kajisaab.feature.auth.usecase.response;

import global.kajisaab.core.usecase.UseCaseResponse;
import global.kajisaab.feature.auth.dto.LoginResponseUserDetails;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.annotation.Nullable;

import java.util.List;

@Serdeable
@RecordBuilder
public record LoginUseCaseResponse(
        String accessToken,
        String refreshToken,
        LoginResponseUserDetails userDetails,
        List<String> permission) implements UseCaseResponse {
}
