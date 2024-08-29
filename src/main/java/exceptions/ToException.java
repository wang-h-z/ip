package exceptions;

public class ToException extends FridayException {

    public ToException() {
        super("An event must have a deadline. Please try again with /to");
    }
}
