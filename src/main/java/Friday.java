import java.util.ArrayList;

public class Friday {
    private final BotMessage header = new BotMessage("""
             Hello! I'm Friday
             What can I do for you?""");

    private final BotMessage ending = new BotMessage("""
             Bye. Hope to see you again soon!""");

    private final UserInputHandler handler;
    private ArrayList<Task> list;

    public Friday() {
        this.handler = new UserInputHandler();
        this.list = new ArrayList<>();
    }

    public String printList() {
        String items = "";
        for (int i = 0; i < this.list.size(); i++) {
            String d = String.format("%s. %s", i + 1, this.list.get(i));
            if (i == this.list.size() - 1) {
                items = items.concat(d);
            } else {
                items = items.concat(d + "\n");
            }
        }
        return items;
    }
    public void start() {
        System.out.println(header);
        while (true) {
            String input = this.handler.getInput();
            if (input.equals("bye")) {
                System.out.println(this.ending);
                break;
            }
            if (input.equals("list")) {
                BotMessage output = new BotMessage(this.printList());
                System.out.println(output);
                continue;
            }
            if (input.startsWith("mark")) {
                int idx = Integer.parseInt(input.substring(5));
                this.list.get(idx - 1).markAsDone();
                String description = "Nice! I've marked this task as done:" + "\n" + this.list.get(idx - 1);
                BotMessage output = new BotMessage(description);
                System.out.println(output);
                continue;
            }
            if (input.startsWith("unmark")) {
                int idx = Integer.parseInt(input.substring(7));
                this.list.get(idx - 1).markAsUndone();
                String description = "OK, I've marked this task as not done yet:" + "\n" + this.list.get(idx - 1);
                BotMessage output = new BotMessage(description);
                System.out.println(output);
                continue;
            }
            if (input.startsWith("todo")) {
                Todo todo = new Todo(input.substring(5).trim());
                this.list.add(todo);
                String response = this.handler.handleInput(todo, this.list);
                BotMessage output = new BotMessage(response);
                System.out.println(output);
                continue;
            }
            if (input.startsWith("deadline")) {
                int end = input.indexOf("/by");
                Deadline deadline = new Deadline(input.substring(9, end).trim(), input.substring(end + 3).trim());
                this.list.add(deadline);
                String response = this.handler.handleInput(deadline, this.list);
                BotMessage output = new BotMessage(response);
                System.out.println(output);
                continue;
            }
            if (input.startsWith("event")) {
                int eventIndex = input.indexOf("event") + "event".length();
                int fromIndex = input.indexOf("/from");
                int toIndex = input.indexOf("/to");
                Event event = new Event(input.substring(eventIndex, fromIndex).trim(),
                                        input.substring(fromIndex + "/from".length(), toIndex).trim(),
                                        input.substring(toIndex + "/to".length()).trim());
                this.list.add(event);
                String response = this.handler.handleInput(event, this.list);
                BotMessage output = new BotMessage(response);
                System.out.println(output);
                continue;
            }
        }
    }
    public static void main(String[] args) {
        Friday bot = new Friday();
        bot.start();
    }
}
