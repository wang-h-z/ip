package exceptions;

/**
 * Represents an exception that is thrown when a task is missing a required deadline.
 * This exception extends the {@link FridayException} and provides a detailed message
 * indicating which task is missing the deadline.
 */
public class DeadlineException extends FridayException {

    public DeadlineException(String task) {
        super(String.format("There is no deadline for this %s task. Please try again.", task));
    }
}
