public class FromException extends FridayException {

    public FromException() {
        super("An event must have a starting date. Please try again using /from.");
    }
}
