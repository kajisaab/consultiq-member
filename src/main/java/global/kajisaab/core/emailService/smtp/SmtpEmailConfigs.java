package global.kajisaab.core.emailService.smtp;


import global.kajisaab.core.emailService.BaseEmailConfigs;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

import java.util.Map;

@ConfigurationProperties(SmtpEmailConfigs.PREFIX + SmtpEmailConfigs.PROVIDER)
@Requires(property = SmtpEmailConfigs.PREFIX + SmtpEmailConfigs.PROVIDER)
public record SmtpEmailConfigs(
        String host,
        String port,
        String username,
        String password,
        String from,
        Map<String, String> properties
) implements BaseEmailConfigs {

    static final String PROVIDER = ".smtp";
}
