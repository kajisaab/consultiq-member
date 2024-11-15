package global.kajisaab.core.emailService;

import freemarker.template.Configuration;
import global.kajisaab.core.exceptionHandling.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.StringWriter;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractEmailService implements EmailService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final Validator validator;
    private final Configuration freemarkerConfiguration;

    public AbstractEmailService(Validator validator, Configuration freemarkerConfiguration) {
        this.validator = validator;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Override
    public Mono<Void> sendEmail(EmailRequest emailRequest) {
        var violations = validator.validate(emailRequest);

        if (!violations.isEmpty()) {
            return throwOnViolations(emailRequest, violations);
        }

        LOG.info("Sending email to {} having cc - [{}] and bcc - [{}]",
                emailRequest.receiverEmail(),
                emailRequest.cc(),
                emailRequest.bcc()
        );

        return sendEmailInternal(emailRequest)
                .doOnSuccess(logOnSuccess(emailRequest))
                .doOnError(logOnError(emailRequest));
    }

    protected abstract Mono<Void> sendEmailInternal(EmailRequest emailRequest);

    protected String getTemplateParsedEmailBody(EmailRequest emailRequest) {
        var body = emailRequest.body();
        var templateModel = emailRequest.templateModel();
        if (Objects.nonNull(templateModel) && !templateModel.isEmpty()) {
            try {
                this.freemarkerConfiguration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
                var template = freemarkerConfiguration.getTemplate(body + ".ftl");
                var stringWriter = new StringWriter();

                template.process(templateModel, stringWriter);

                return stringWriter.toString();
            } catch (Exception e) {
                LOG.error("Template processing failed: {}", e.getMessage());
                LOG.trace("Stack trace for {} - ", e.getMessage(), e.getCause());

                return body;
            }
        }

        return body;
    }

    private Consumer<? super Void> logOnSuccess(EmailRequest emailRequest) {
        return aVoid -> LOG.info("Successfully sent email to {} having cc - [{}] and bcc - [{}]",
                emailRequest.receiverEmail(),
                emailRequest.cc(),
                emailRequest.bcc());
    }

    private Consumer<? super Throwable> logOnError(EmailRequest emailRequest) {
        return error -> {
            LOG.error("Failed to send email to {} having cc - [{}] and bcc - [{}]: {}",
                    emailRequest.receiverEmail(),
                    emailRequest.cc(),
                    emailRequest.bcc(),
                    error.getMessage()
            );

            LOG.trace("Stack trace for {} - ", error.getMessage(), error.getCause());
        };
    }

    private Mono<Void> throwOnViolations(
            EmailRequest emailRequest, Set<ConstraintViolation<EmailRequest>> violations
    ) {
        LOG.error("Could not send email to [{}], due to violations", emailRequest.receiverEmail());
        for (var violation : violations) {
            LOG.error("[{}]: {}", violation.getInvalidValue(), violation.getMessage());
        }

        return Mono.error(() ->
                new BadRequestException("Bad Request: Check logs for violations"));

    }
}
