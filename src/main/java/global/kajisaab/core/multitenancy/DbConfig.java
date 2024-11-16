package global.kajisaab.core.multitenancy;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class DbConfig {

    @Value("${r2dbc.datasources.default.url}")
    private String dbUrl;

    @Value("${r2dbc.datasources.default.username}")
    private String username;

    @Value("${r2dbc.datasources.default.password}")
    private String dbPassword;

    public String getDbUrl(){
        return dbUrl;
    }

    public String getUsername(){
        return username;
    }

    public String getDbPassword(){
        return dbPassword;
    }
}
