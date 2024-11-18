package global.kajisaab.core.filterSpecification;

public class SimpleViolation implements Violation {

    private final String violation;
    private final String errorMessage;

    public static SimpleViolation of(String violation, String errorMessage) {
        return new SimpleViolation(violation, errorMessage);
    }

    private SimpleViolation(String violation, String errorMessage) {
        this.violation = violation;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getViolator() {
        return this.violation;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

}
