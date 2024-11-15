package global.kajisaab.core.emailService;

import reactor.core.publisher.Mono;

public interface EmailService {

    Mono<Void> sendEmail(EmailRequest emailRequest);
}
