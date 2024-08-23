public class TodoException extends InputException {

    public TodoException() {
        super("The description of a todo cannot be empty. Please try again.");
    }
}
