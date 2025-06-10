package Exceptions;

public class TooManyArgumentsException extends RuntimeException {
    public TooManyArgumentsException(String message) {
        super(message);
    }
    public TooManyArgumentsException() {
        super("too many arguments");
    }
}
