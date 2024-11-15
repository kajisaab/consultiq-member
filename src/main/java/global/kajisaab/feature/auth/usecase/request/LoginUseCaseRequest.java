package global.kajisaab.feature.auth.usecase.request;

import global.kajisaab.core.usecase.UseCaseRequest;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;

@Serdeable
public record LoginUseCaseRequest(

        String email,

        String password,

        String consultancyCode

) implements UseCaseRequest {
}
