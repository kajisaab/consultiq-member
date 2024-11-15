package global.kajisaab.core.usecase;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import reactor.core.publisher.Mono;

public interface UseCase<I extends UseCaseRequest, U extends UseCaseResponse> {
    Mono<U> execute(I request) throws BadRequestException;
}
