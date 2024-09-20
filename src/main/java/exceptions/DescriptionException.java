package exceptions;

/**
 * Represents an exception that is thrown when the description of a task is missing or empty.
 * This exception extends the {@link FridayException} and provides a detailed message
 * indicating which task's description is missing.
 */
public class DescriptionException extends FridayException {

    public DescriptionException(String task) {
        super(String.format("The description of a %s cannot be empty. Please try again.", task));
    }
}
