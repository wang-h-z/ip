package exceptions;

public class DescriptionException extends InputException {

    public DescriptionException(String task) {
        super(String.format("The description of a %s cannot be empty. Please try again.", task));
    }
}
