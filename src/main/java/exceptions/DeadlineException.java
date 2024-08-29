package exceptions;

public class DeadlineException extends FridayException {

    public DeadlineException(String task) {
        super(String.format("There is no deadline for this %s task. Please try again.", task));
    }
}
