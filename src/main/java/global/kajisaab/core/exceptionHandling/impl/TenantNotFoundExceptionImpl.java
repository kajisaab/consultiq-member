package global.kajisaab.core.exceptionHandling.impl;

import global.kajisaab.core.exceptionHandling.dto.GenerateMessageObject;
import global.kajisaab.core.responseHandler.ResponseHandler;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.multitenancy.exceptions.TenantNotFoundException;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {TenantNotFoundException.class, ExceptionHandler.class})
public class TenantNotFoundExceptionImpl implements ExceptionHandler<TenantNotFoundException, HttpResponse<?>> {
    @Override
    public HttpResponse<?> handle(HttpRequest request, TenantNotFoundException exception) {
        HttpStatus badRequest = HttpStatus.UNAUTHORIZED;

        Object responseMessageObject = new GenerateMessageObject(exception.getMessage());

        return ResponseHandler.responseBuilder("Unauthorized", badRequest, responseMessageObject, "-1");
    }
}
