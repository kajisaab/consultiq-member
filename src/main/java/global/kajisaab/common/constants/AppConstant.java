package global.kajisaab.common.constants;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
@Introspected
public class AppConstant {

    public static final List<String> PUBLIC_ROUTE = List.of("/api/v1/auth/login");

}
