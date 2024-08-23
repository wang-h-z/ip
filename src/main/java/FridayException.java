public class FridayException extends Exception {

    public FridayException(String message) {
        super('\t' + message);
    }
}
