package exceptions;

/**
 * Represents an exception that is thrown when a necessary command is missing in the user's input.
 * This exception extends the {@link FridayException} and provides a detailed message indicating the missing command.
 */
public class MissingCommandException extends FridayException {
    public MissingCommandException(String cmd) {
        super(String.format("You are using this task without the necessary %s command. Try again.", cmd));
    }
}
