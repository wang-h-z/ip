import java.util.ArrayList;
import java.util.Scanner;

public class UserInputHandler {

    private final Scanner scanner;

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() {
        return this.scanner.nextLine();
    }
    public String handleInput(Task task, ArrayList<Task> list) {
        return String.format(""" 
       \t Got it. I've added this task:
       \t  %s
       \t Now you have %d tasks in the list.""", task, list.size());
    }
}
