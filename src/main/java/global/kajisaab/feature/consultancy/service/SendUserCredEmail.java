package global.kajisaab.feature.consultancy.service;

import global.kajisaab.core.emailService.EmailRequest;
import global.kajisaab.core.emailService.EmailService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class SendUserCredEmail {

    private final Logger LOG = LoggerFactory.getLogger(SendUserCredEmail.class);

    private final EmailService emailService;

    @Inject
    public SendUserCredEmail(EmailService emailService) {
        this.emailService = emailService;
    }

    public Mono<Void> execute(EmailRequest request){

        Map<String, Object> templateModel = new HashMap<>(request.templateModel());

        EmailRequest newRequest = new EmailRequest(
                request.receiverEmail(),
                request.subject(),
                request.body(),
                request.cc(),
                request.bcc(),
                request.attachment(),
                templateModel
        );

        return emailService.sendEmail(newRequest);

    }
}
