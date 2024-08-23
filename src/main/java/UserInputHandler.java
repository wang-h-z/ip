import java.util.*;

public class UserInputHandler {

    private final Scanner scanner;
    private final Set<String> validCommands = new HashSet<>(Arrays.asList("bye", "list", "mark", "unmark", "todo", "deadline", "event", "delete"));
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

        //Logic for Todo task
        if (input.startsWith("todo")) {
            if (input.trim().length() == 4 || input.substring(5).trim().isEmpty()) {
                throw new DescriptionException("todo");
            }
            task = new Todo(input.substring(5).trim());
            list.add(task);
        }

        //Logic for Deadline task
        if (input.startsWith("deadline")) {
            int end = input.indexOf("/by");
            if (!input.contains("/by")) {
                throw new MissingCommandException("/by");
            }
            String description = input.substring(input.indexOf("deadline") + "deadline".length(), end).trim();
            if (description.isEmpty()) {
                throw new DescriptionException("deadline");
            }
            String deadline = input.substring(end + 3).trim();
            if (deadline.isEmpty()) {
                throw new DeadlineException("deadline");
            }
            task = new Deadline(description, deadline);
            list.add(task);
        }

        //Logic for Event task
        if (input.startsWith("event")) {
            if (!input.contains("/from")) {
                throw new MissingCommandException("/from");
            }
            if (!input.contains("/to")) {
                throw new MissingCommandException("/to");
            }
            int eventIndex = input.indexOf("event") + "event".length();
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to");
            String description = input.substring(eventIndex, fromIndex).trim();
            if (description.isEmpty()) {
                throw new DescriptionException("event");
            }
            String from = input.substring(fromIndex + "/from".length(), toIndex).trim();
            if (from.isEmpty()) {
                throw new FromException();
            }
            String to = input.substring(toIndex + "/to".length()).trim();
            if (to.isEmpty()) {
                throw new ToException();
            }
            task = new Event(description, from, to);
            list.add(task);
        }

        //Logic for Delete command
        if (input.startsWith("delete")) {
            int idx = Integer.parseInt(input.substring("delete".length()).trim()) - 1;
            Task r = list.remove(idx);
            return String.format(""" 
            \t Got it. I've removed this task:
            \t  %s
            \t Now you have %d tasks in the list.""", r, list.size());
        }

        return String.format(""" 
            \t Got it. I've added this task:
            \t  %s
            \t Now you have %d tasks in the list.""", task, list.size());
    }

}