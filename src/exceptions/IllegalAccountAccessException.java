package exceptions;

public class IllegalAccountAccessException extends Exception {
    public IllegalAccountAccessException() {
        super("You do not own this account and as such cannot perform transactions on it ");
    }
}
