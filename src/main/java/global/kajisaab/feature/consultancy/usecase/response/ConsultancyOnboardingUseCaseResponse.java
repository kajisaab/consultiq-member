package global.kajisaab.feature.consultancy.usecase.response;

import global.kajisaab.core.usecase.UseCaseResponse;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Serdeable
@RecordBuilder
public record ConsultancyOnboardingUseCaseResponse(String message) implements UseCaseResponse {
}
