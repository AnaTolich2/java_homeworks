package expression.exceptions;

public class DivisionByZeroException extends RuntimeException { // :NOTE:
    public DivisionByZeroException() {
        super("division by zero");
    }
}
