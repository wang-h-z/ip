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
        this.list = new ArrayList<Task>();
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
            this.list.add(new Task(input));
            String response = this.handler.handleInput(input);
            BotMessage output = new BotMessage(response);
            System.out.println(output);
        }
    }
    public static void main(String[] args) {
        Friday bot = new Friday();
        bot.start();
    }
}
