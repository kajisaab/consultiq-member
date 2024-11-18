package global.kajisaab.common.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Serdeable
@Introspected
@RecordBuilder
public record PageableDto(
        Integer currentPage,
        Integer pageSize,
        Long totalPages
) {

}
