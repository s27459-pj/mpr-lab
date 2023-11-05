package lab3.exception;

public class NotFoundException extends RuntimeException {
    private String model;

    public NotFoundException(String model) {
        this.model = model;
    }

    @Override
    public String getMessage() {
        return String.format("NotFoundException: %s", model);
    }
}
