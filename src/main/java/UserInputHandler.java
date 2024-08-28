import java.util.*;

public class UserInputHandler {

    private final Scanner scanner;
    private enum Command {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN;

        public static Command fromString(String input) {
            try {
                return Command.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return UNKNOWN;
            }
        }
    }
    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() throws InputException {
        return this.scanner.nextLine();
    }

    public String printList(ArrayList<Task> list) {
        String items = "";
        if (list.isEmpty()) {
            return "\t  There are currently no items in your list.";
        }
        for (int i = 0; i < list.size(); i++) {
            String d = String.format("\t  %s.%s", i + 1, list.get(i));
            if (i == list.size() - 1) {
                items = items.concat(d);
            } else {
                items = items.concat(d + "\n");
            }
        }
        return items;
    }

    public String handleInput(String input, ArrayList<Task> list, Storage storage)  throws FridayException {

        String[] parts = input.trim().split(" ", 2);
        Command command = Command.fromString(parts[0]);
        int idx;
        Task task;

        switch (command) {

            case LIST:
                return "\t Here are the tasks in your list:" + "\n" + this.printList(list);

            case MARK:
                idx = Integer.parseInt(input.substring(5).trim());
                list.get(idx - 1).markAsDone();
                storage.saveTasks(list);
                return "\t Nice! I've marked this task as done:" + "\n" + "\t  " + list.get(idx - 1);

            case UNMARK:
                idx = Integer.parseInt(input.substring(7).trim());
                list.get(idx - 1).markAsUndone();
                storage.saveTasks(list);
                return "\t OK, I've marked this task as not done yet:" + "\n" + "\t  " + list.get(idx - 1);

            case TODO:
                    if (input.trim().length() == 4 || input.substring(5).trim().isEmpty()) {
                        throw new DescriptionException("todo");
                    }
                    task = new Todo(input.substring(5).trim());
                    list.add(task);
                    storage.saveTasks(list);
                    return String.format(""" 
                        \t Got it. I've added this task:
                        \t  %s
                        \t Now you have %d tasks in the list.""", task, list.size());

            //Logic for Deadline task
            case DEADLINE:
                int end = input.indexOf("/by");
                if (!input.contains("/by")) {
                    throw new MissingCommandException("/by");
                }
                String deadlineDescription = input.substring(input.indexOf("deadline") + "deadline".length(), end).trim();
                if (deadlineDescription.isEmpty()) {
                    throw new DescriptionException("deadline");
                }
                String deadline = input.substring(end + 3).trim();
                if (deadline.isEmpty()) {
                    throw new DeadlineException("deadline");
                }
                task = new Deadline(deadlineDescription, deadline);
                list.add(task);
                storage.saveTasks(list);
                return String.format(""" 
                    \t Got it. I've added this task:
                    \t  %s
                    \t Now you have %d tasks in the list.""", task, list.size());


            //Logic for Event task
            case EVENT:
                if (!input.contains("/from")) {
                    throw new MissingCommandException("/from");
                }
                if (!input.contains("/to")) {
                    throw new MissingCommandException("/to");
                }
                int eventIndex = input.indexOf("event") + "event".length();
                int fromIndex = input.indexOf("/from");
                int toIndex = input.indexOf("/to");
                String eventDescription = input.substring(eventIndex, fromIndex).trim();
                if (eventDescription.isEmpty()) {
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
                task = new Event(eventDescription, from, to);
                list.add(task);
                storage.saveTasks(list);
                return String.format(""" 
                    \t Got it. I've added this task:
                    \t  %s
                    \t Now you have %d tasks in the list.""", task, list.size());

            //Logic for Delete command
            case DELETE:
                String i = input.substring("delete".length()).trim();
                if (i.isEmpty()) {
                    throw new InputException("Please give a valid item for me to delete. Try again.");
                }
                idx = Integer.parseInt(i) - 1;
                if (list.isEmpty()) {
                    throw new FridayException("Attempting to delete item from an empty list. Please add tasks first.");
                }
                if (idx >= list.size() || idx < 0) {
                    throw new FridayException("Attempting to delete item which is not in the list. Please ensure the number is correct.");
                }
                Task r = list.remove(idx);
                storage.saveTasks(list);
                return String.format(""" 
                        \t Got it. I've removed this task:
                        \t  %s
                        \t Now you have %d tasks in the list.""", r, list.size());

            case UNKNOWN:
            default:
                throw new InputException("Sorry, I have honestly 0 clue what you are saying... T.T");
        }
    }
}