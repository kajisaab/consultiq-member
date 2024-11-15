package global.kajisaab.core.emailService.smtp;


import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import global.kajisaab.core.emailService.AbstractEmailService;
import global.kajisaab.core.emailService.EmailRequest;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static jakarta.mail.Message.RecipientType.*;

@Singleton
@Named("smtp")
@Requires(beans = SmtpEmailConfigs.class)
public class SmtpEmailService extends AbstractEmailService {

    private final SmtpEmailConfigs emailConfigs;

    public SmtpEmailService(
            Validator validator,
            Configuration freemarkerConfig,
            SmtpEmailConfigs emailConfigs
    ) {
        super(validator, freemarkerConfig);
        this.emailConfigs = emailConfigs;
    }

    @Override
    protected Mono<Void> sendEmailInternal(EmailRequest emailRequest) {
        return Mono
                .fromCallable(() -> {
                    var props = buildEmailProps();
                    var session = configureAndGetSessionInstance(props);

                    var message = buildEmailMessage(emailRequest, session);

                    Transport.send(message);
                    return Mono.empty();
                })
                .onErrorResume(Exception.class, Mono::error)
                .then();
    }

    private Properties buildEmailProps() {
        var props = new Properties();

        props.put("email", emailConfigs.username());
        props.put("fromEmail", emailConfigs.from());
        props.put("password", emailConfigs.password());

        props.put("mail.smtp.host", emailConfigs.host());
        props.put("mail.smtp.port", emailConfigs.port());

        props.put("mail.smtp.auth", emailConfigs.properties().get("auth"));
        props.put("mail.smtp.ehlo", emailConfigs.properties().get("ehlo"));
        props.put("mail.smtp.ssl.enable", emailConfigs.properties().get("ssl-enable"));
        props.put("mail.smtp.starttls.enable", emailConfigs.properties().get("starttls-enable"));

        return props;
    }

    private Session configureAndGetSessionInstance(Properties props) {
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfigs.username(), emailConfigs.password());
            }
        });
    }

    private MimeMessage buildEmailMessage(
            EmailRequest emailRequest, Session session
    ) throws MessagingException, TemplateException, IOException {
        var message = new MimeMessage(session);

        message.setFrom(new InternetAddress(emailConfigs.from()));
        message.setSentDate(Date.valueOf(LocalDate.now()));
        message.setSubject(emailRequest.subject());

        setMessageRecipients(emailRequest, message);
        setMessageContent(emailRequest, message);

        return message;
    }

    private void setMessageRecipients(
            EmailRequest emailRequest, MimeMessage message
    ) throws MessagingException {
        var to = emailRequest.receiverEmail();
        var cc = Optional.ofNullable(emailRequest.cc())
                .map(ccs -> String.join(",", ccs))
                .orElse("");
        var bcc = Optional.ofNullable(emailRequest.bcc())
                .map(bccs -> String.join(",", bccs))
                .orElse("");

        message.setRecipients(TO,   InternetAddress.parse(to));
        message.setRecipients(CC,   InternetAddress.parse(cc));
        message.setRecipients(BCC,  InternetAddress.parse(bcc));
    }

    private void setMessageContent(
            EmailRequest emailRequest, MimeMessage message
    ) throws MessagingException, TemplateException, IOException {
        var multipart = new MimeMultipart();

        var messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(
                super.getTemplateParsedEmailBody(emailRequest),
                "text/html; charset=utf-8"
        );

        multipart.addBodyPart(messageBodyPart);

        addAttachmentIfAvailable(emailRequest, multipart);

        message.setContent(multipart);
    }

    private static void addAttachmentIfAvailable(
            EmailRequest emailRequest, MimeMultipart multipart
    ) throws MessagingException {
        if (Objects.nonNull(emailRequest.attachment())) {
            var attachmentPart = new MimeBodyPart();
            attachmentPart.setFileName(emailRequest.attachment().getFilename());
            attachmentPart.setContent(
                    emailRequest.attachment().getContent(),
                    emailRequest.attachment().getContentType()
            );

            multipart.addBodyPart(attachmentPart);
        }
    }

}
