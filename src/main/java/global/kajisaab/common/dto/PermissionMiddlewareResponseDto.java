package global.kajisaab.common.dto;

import global.kajisaab.feature.auth.entity.UserDetailsEntity;

public record PermissionMiddlewareResponseDto(UserDetailsEntity userDetails, boolean isPermitted) {
}
