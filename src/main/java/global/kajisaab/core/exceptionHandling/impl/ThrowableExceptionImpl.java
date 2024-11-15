package global.kajisaab.core.exceptionHandling.impl;

import global.kajisaab.core.exceptionHandling.dto.ThrowableExceptionDto;
import global.kajisaab.core.exceptionHandling.dto.ThrowableExceptionDtoBuilder;
import global.kajisaab.core.responseHandler.ResponseHandler;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces
@Singleton
@Requires(classes = {Throwable.class})
public class ThrowableExceptionImpl implements ExceptionHandler<Throwable, HttpResponse<?>> {

    private static final Logger LOG = LoggerFactory.getLogger(ThrowableExceptionImpl.class);

    @Override
    public HttpResponse<?> handle(HttpRequest request, Throwable exception) {

        LOG.error("Unhandled Exception occurred: ", exception);

        ThrowableExceptionDto exceptionResponse = ThrowableExceptionDtoBuilder.builder()
                .message("Sorry! an unexpected error occurred")
                .debugMessage(exception.getMessage())
                .stackTrace(this.getStackTrace(exception))
                .build();

        return ResponseHandler.responseBuilder("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, exceptionResponse, "-1");
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            stackTrace.append(element.toString()).append("\n");
        }
        return stackTrace.toString();
    }
}
