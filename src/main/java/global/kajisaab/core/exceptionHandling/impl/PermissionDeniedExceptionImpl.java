package global.kajisaab.core.exceptionHandling.impl;

import global.kajisaab.core.exceptionHandling.BadRequestException;
import global.kajisaab.core.exceptionHandling.PermissionDeniedException;
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
@Requires(classes = {PermissionDeniedException.class, ExceptionHandler.class})
public class PermissionDeniedExceptionImpl implements ExceptionHandler<PermissionDeniedException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, PermissionDeniedException exception) {
        HttpStatus badRequest = HttpStatus.FORBIDDEN;

        Object responseMessageObject = new GenerateMessageObject("Sorry you don't have permission to access this resource!");

        return ResponseHandler.responseBuilder("Forbidden", badRequest, responseMessageObject, "-1");
    }
}
