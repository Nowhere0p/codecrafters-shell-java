package Exceptions;

public class NumericArgumentRequiredException extends RuntimeException {
    public NumericArgumentRequiredException(String message) {
        super(message);
    }
    public NumericArgumentRequiredException() {
        super("numeric argument required");
    }
}
