package global.kajisaab.core.exceptionHandling;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String data){
        super(data);
    }
}
