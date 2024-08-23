import java.util.ArrayList;

public class Friday {
    private final BotMessage header = new BotMessage("""
             \t  Hello! I'm Friday
             \t  What can I do for you?""");

    private final BotMessage ending = new BotMessage("""
             \t  Bye. Hope to see you again soon!""");

    private final UserInputHandler handler;
    private ArrayList<Task> list;

    public Friday() {
        this.handler = new UserInputHandler();
        this.list = new ArrayList<>();
    }

    public String printList() {
        String items = "";
        if (this.list.isEmpty()) {
            return "\t  There are currently no items in your list.";
        }
        for (int i = 0; i < this.list.size(); i++) {
            String d = String.format("\t  %s.%s", i + 1, this.list.get(i));
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
            try {
                String input = this.handler.getInput();
                if (input.equals("bye")) {
                    System.out.println(this.ending);
                    break;
                }
                if (input.equals("list")) {
                    String description = "\t Here are the tasks in your list:" + "\n" + this.printList();
                    BotMessage output = new BotMessage(description);
                    System.out.println(output);
                    continue;
                }
                if (input.startsWith("mark")) {
                    int idx = Integer.parseInt(input.substring(5));
                    this.list.get(idx - 1).markAsDone();
                    String description = "\t Nice! I've marked this task as done:" + "\n" + this.list.get(idx - 1);
                    BotMessage output = new BotMessage(description);
                    System.out.println(output);
                    continue;
                }
                if (input.startsWith("unmark")) {
                    int idx = Integer.parseInt(input.substring(7));
                    this.list.get(idx - 1).markAsUndone();
                    String description = "\t OK, I've marked this task as not done yet:" + "\n" + this.list.get(idx - 1);
                    BotMessage output = new BotMessage(description);
                    System.out.println(output);
                    continue;
                }

                // Any other input
                String response = this.handler.handleInput(input, list);
                BotMessage output = new BotMessage(response);
                System.out.println(output);

            } catch (TodoException e) {
                BotMessage message = new BotMessage(e.getMessage());
                System.out.println(message);
            } catch (FridayException e) {
                BotMessage message = new BotMessage(e.getMessage());
                System.out.println(message);
            }
        } // end of loop
    }
    public static void main(String[] args) {
        Friday bot = new Friday();
        bot.start();
    }
}
