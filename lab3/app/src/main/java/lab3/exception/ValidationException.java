package lab3.exception;

public class ValidationException extends RuntimeException {
    private String field;
    private String message;

    public ValidationException(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return String.format("ValidationException: Field '%s' %s", field, message);
    }
}
