package global.kajisaab.common.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public enum PermissionConstant {
    LOGIN("/auth/login", "POST", Collections.emptyList()),
    REFRESH_TOKEN("/api/v1/auth/refresh-token", "GET", Collections.emptyList()),
    DASHBOARD_CARD("/api/v1/dashboard/cards", "GET", Collections.singletonList("dashboard:cards")),
    INDIVIDUAL_ROLE_DETAIL("/api/v1/roles/detail/:id", "GET", Collections.singletonList("roles:detail")),
    ROLE_LIST("/api/v1/role/list", "POST", Collections.singletonList("roles:list"));
//    DASHBOARD_TRANSACTIONS_BAR_COUNT("/dashboard-reports/transactions-bar/count/:period", "GET", Collections.singletonList("dashboard:transactionGraph")),

    private final String endpoint;
    private final String method;
    private final List<String> permissions;

    public String getEndpoint() {
        return endpoint;
    }

    public String getMethod() {
        return method;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    PermissionConstant(String endpoint, String method, List<String> permissions) {
        this.endpoint = endpoint;
        this.method = method;
        this.permissions = permissions;
    }

    public static PermissionConstant getByEndpoint(String endpoint) {
        return Arrays.stream(values())
                .filter(permission -> permission.endpoint.equals(endpoint))
                .findFirst()
                .orElse(null);
    }

    public static List<String> getPermissionsByEndpointAndMethod(String endpoint, String method) {
        return Arrays.stream(values())
                .filter(permission -> checkEndpoint(permission.endpoint, endpoint) && permission.method.equalsIgnoreCase(method))
                .map(PermissionConstant::getPermissions)
                .findFirst()
                .orElse(null);
    }

    public static boolean isValidPermissionForEndpointAndMethod(String endpoint, String method) {
        return Arrays.stream(values())
                .anyMatch(permission -> checkEndpoint(permission.endpoint, endpoint)
                        && permission.method.equalsIgnoreCase(method));
    };

    private static boolean checkEndpoint(String patternUrl, String originalUrl) {
        String regex = "^" + patternUrl.replaceAll("(:\\w+)", "[^/]+") + "$";
        return Pattern.compile(regex).matcher(originalUrl).matches();
    }
}
