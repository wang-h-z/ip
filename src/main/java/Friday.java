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

    public void start() {
        System.out.println(header);
        while (true) {
            try {
                String input = this.handler.getInput();
                if (input.trim().equals("bye")) {
                    System.out.println(this.ending);
                    break;
                }
                String response = this.handler.handleInput(input, list);
                BotMessage output = new BotMessage(response);
                System.out.println(output);
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
