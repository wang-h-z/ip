package exceptions;

/**
 * Represents a custom exception used in the chatbot for handling errors and invalid operations.
 * A FridayException is thrown when there is an issue with user input or task processing.
 */
public class FridayException extends Exception {

    public FridayException(String message) {
        super(message);
    }
}
