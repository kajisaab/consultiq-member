package global.kajisaab.feature.auth.dto;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.util.List;

@Serdeable
@RecordBuilder
public record LoginResponseUserDetails(
        String fullName,
        List<String> roles,
        String position,
        @Nullable String image
) {
}
