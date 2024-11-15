package global.kajisaab.core.exceptionHandling.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Serdeable
@RecordBuilder
public record ThrowableExceptionDto(String message, String debugMessage, String stackTrace) {
}
