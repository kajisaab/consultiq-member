package global.kajisaab.core.responseHandler.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ResponseHandlerDto(String code, String message, Object data) {
}
