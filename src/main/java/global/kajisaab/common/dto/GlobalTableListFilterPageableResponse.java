package global.kajisaab.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import global.kajisaab.core.usecase.UseCaseResponse;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
@Introspected
@JsonInclude(JsonInclude.Include.ALWAYS) // Ensure `list` and `pageableDto` are always serialized
public record GlobalTableListFilterPageableResponse<T>(
        List<T> list,
        PageableDto pageableDto
) implements UseCaseResponse {
}
