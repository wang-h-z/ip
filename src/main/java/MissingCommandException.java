public class MissingCommandException extends FridayException {
    public MissingCommandException(String cmd) {
        super(String.format("You are using this task without the necessary %s command. Try again.", cmd));
    }
}
