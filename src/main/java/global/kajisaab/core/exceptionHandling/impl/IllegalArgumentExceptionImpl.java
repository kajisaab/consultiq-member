package global.kajisaab.core.exceptionHandling.impl;

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
@Requires(classes = {IllegalArgumentException.class, ExceptionHandler.class})
public class IllegalArgumentExceptionImpl implements ExceptionHandler<IllegalArgumentException, HttpResponse<?>> {
    @Override
    public HttpResponse<?> handle(HttpRequest request, IllegalArgumentException exception) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        Object responseMessageObject = new GenerateMessageObject(exception.getMessage());

        return ResponseHandler.responseBuilder("Illegal Argument", badRequest, responseMessageObject, "-1");
    }
}
