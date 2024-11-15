package global.kajisaab.core.emailService;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.email.Attachment;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;
import java.util.Set;

@Introspected
@RecordBuilder
public record EmailRequest(

        @Email
        String receiverEmail,

        @NotEmpty
        String subject,

        @NotEmpty
        String body,

        Set<String> cc,
        Set<String> bcc,

        Attachment attachment,
        Map<String, Object> templateModel
) {
}
