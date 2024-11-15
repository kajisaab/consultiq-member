package global.kajisaab.core.exceptionHandling.impl;

import global.kajisaab.core.exceptionHandling.dto.GenerateMessageObject;
import global.kajisaab.core.responseHandler.dto.ResponseHandlerDto;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import io.micronaut.security.authentication.AuthorizationException;
import io.micronaut.security.authentication.DefaultAuthorizationExceptionHandler;
import io.micronaut.security.config.RedirectConfiguration;
import io.micronaut.security.config.RedirectService;
import io.micronaut.security.errors.PriorToLoginPersistence;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Replaces(DefaultAuthorizationExceptionHandler.class)
public class JwtAuthenticationExceptionImpl extends DefaultAuthorizationExceptionHandler {

    /**
     * @param errorResponseProcessor  ErrorResponse processor API
     * @param redirectConfiguration   Redirect configuration
     * @param redirectService         Redirection Service
     * @param priorToLoginPersistence Persistence mechanism to redirect to prior login url
     */
    public JwtAuthenticationExceptionImpl(ErrorResponseProcessor<?> errorResponseProcessor, RedirectConfiguration redirectConfiguration, RedirectService redirectService, @Nullable PriorToLoginPersistence priorToLoginPersistence) {
        super(errorResponseProcessor, redirectConfiguration, redirectService, priorToLoginPersistence);
    }

    @Override
    public MutableHttpResponse<?> handle(HttpRequest request, AuthorizationException exception) {
        //Let the DefaultAuthorizationExceptionHandler create the initial response
        //then add a header
        // Let the DefaultAuthorizationExceptionHandler create the initial response
        MutableHttpResponse<?> response = super.handle(request, exception);

        // Add custom header
        response.header("X-Reason", "Unauthorized access");

        GenerateMessageObject messageObject = new GenerateMessageObject("Access is denied due to invalid credentials");

        ResponseHandlerDto responseBody = new ResponseHandlerDto("-1", "Unauthorized", messageObject);

        if(exception.isForbidden()){
            messageObject = new GenerateMessageObject("Sorry, you are not authorized to access this resource");

            responseBody = new ResponseHandlerDto("-1", "Forbidden", messageObject);
        }

        // Set the response body with the custom error message structure
        response.body(responseBody);

        // Return the modified response
        return response;
    }

}
