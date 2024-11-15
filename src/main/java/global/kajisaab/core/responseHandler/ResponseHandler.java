package global.kajisaab.core.responseHandler;

import global.kajisaab.core.responseHandler.dto.ResponseHandlerDto;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.HttpResponse;


public class ResponseHandler<O> {

    public static <T> HttpResponse<T> responseBuilder(Object responseObject){
        return responseBuilder("SUCCESS", HttpStatus.OK, responseObject, "0");
    }

    public static <T> HttpResponse<T> responseBuilder(String message, HttpStatus httpStatus, Object responseObject){
        ResponseHandlerDto response = new ResponseHandlerDto("0", message, responseObject);
        return responseBuilder("SUCCESS", httpStatus, responseObject, "0");
    }

    public static<T> HttpResponse<T> responseBuilder(String message, HttpStatus httpStatus, Object responseObject, String code){
        ResponseHandlerDto response = new ResponseHandlerDto(code, message, responseObject);
        return HttpResponse.status(httpStatus).body((T) response);
    }

}
