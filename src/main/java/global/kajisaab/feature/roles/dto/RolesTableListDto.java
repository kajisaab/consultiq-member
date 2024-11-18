package global.kajisaab.feature.roles.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record RolesTableListDto (
        String id,
        String title,
        String createdOn,
        String lastModifiedOn,
        String status
) {

}
