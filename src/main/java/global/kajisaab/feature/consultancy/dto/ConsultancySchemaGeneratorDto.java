package global.kajisaab.feature.consultancy.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Serdeable
@Introspected
@RecordBuilder
public record ConsultancySchemaGeneratorDto(String makerName, String makerEmail, String password, String hashedPassword, String generatedSalt) {
}
