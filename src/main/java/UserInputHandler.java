import java.util.*;

public class UserInputHandler {

    private final Scanner scanner;
    private final Set<String> validCommands = new HashSet<>(Arrays.asList("bye", "list", "unmark", "unmark", "todo", "deadline", "event"));
    private final String ending = """
             \t  Bye. Hope to see you again soon!""";

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() throws InputException {
        String input = this.scanner.nextLine();
        for (String command : validCommands) {
            if (input.startsWith(command)) {
                return input;
            }
        }
        throw new InputException("Sorry, I have honestly 0 clue what you are saying... T.T");
    }

    public String handleInput(String input, ArrayList<Task> list)  throws FridayException {
        Task task = null;

        if (input.startsWith("todo")) {
            if (input.trim().length() == 4 || input.substring(5).trim().isEmpty()) {
                throw new TodoException();
            }
            task = new Todo(input.substring(5).trim());
            list.add(task);
        }
        if (input.startsWith("deadline")) {
            int end = input.indexOf("/by");
            task = new Deadline(input.substring(9, end).trim(), input.substring(end + 3).trim());
            list.add(task);
        }
        if (input.startsWith("event")) {
            int eventIndex = input.indexOf("event") + "event".length();
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to");
            task = new Event(input.substring(eventIndex, fromIndex).trim(),
                    input.substring(fromIndex + "/from".length(), toIndex).trim(),
                    input.substring(toIndex + "/to".length()).trim());
            list.add(task);
        }
        return String.format(""" 
            \t Got it. I've added this task:
            \t  %s
            \t Now you have %d tasks in the list.""", task, list.size());
    }

}