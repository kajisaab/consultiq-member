package global.kajisaab.common.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Serdeable
@RecordBuilder
public record LabelValuePair(String label, String value) {
}
