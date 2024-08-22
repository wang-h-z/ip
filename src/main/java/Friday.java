import java.util.ArrayList;

public class Friday {
    private final BotMessage header = new BotMessage("""
             Hello! I'm Friday
             What can I do for you?""");

    private final BotMessage ending = new BotMessage("""
             Bye. Hope to see you again soon!""");

    private final UserInputHandler handler;

    private ArrayList<String> list;

    public Friday() {
        this.handler = new UserInputHandler();
        this.list = new ArrayList<String>();
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
            this.list.add(input);
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
