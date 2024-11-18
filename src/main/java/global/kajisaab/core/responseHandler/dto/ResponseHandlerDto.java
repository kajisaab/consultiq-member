package global.kajisaab.core.responseHandler.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@JsonInclude(JsonInclude.Include.ALWAYS) // Ensure `list` and `pageableDto` are always serialized
public record ResponseHandlerDto(String code, String message, Object data) {
}
