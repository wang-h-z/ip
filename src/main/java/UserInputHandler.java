import java.util.Scanner;

public class UserInputHandler {

    private final Scanner scanner;

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() {
        return this.scanner.nextLine();
    }
    public String handleInput(String input) {
        String s = "added: " + input;
        return s;
    }
}
