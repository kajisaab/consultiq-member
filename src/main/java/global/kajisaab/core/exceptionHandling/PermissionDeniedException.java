package global.kajisaab.core.exceptionHandling;

public class PermissionDeniedException extends RuntimeException{

    public PermissionDeniedException(String data){
        super(data);
    }
}