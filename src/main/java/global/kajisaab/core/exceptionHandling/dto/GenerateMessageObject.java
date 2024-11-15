package global.kajisaab.core.exceptionHandling.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
@Serdeable
public record GenerateMessageObject(String message) {
}
