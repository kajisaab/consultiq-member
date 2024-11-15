package global.kajisaab.core.exceptionHandling.impl;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.exceptionHandling.dto.GenerateMessageObject;
import global.kajisaab.core.responseHandler.ResponseHandler;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {BadRequestException.class, ExceptionHandler.class})
public class BadRequestExceptionImpl implements ExceptionHandler<BadRequestException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, BadRequestException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        Object responseMessageObject = new GenerateMessageObject(exception.getMessage());

        return ResponseHandler.responseBuilder("Bad Request", badRequest, responseMessageObject, "-1");
    }
}
