public class Friday {
    private final BotMessage header = new BotMessage("""
             Hello! I'm Friday
             What can I do for you?""");

    private final BotMessage ending = new BotMessage("""
             Bye. Hope to see you again soon!""");

    private final UserInputHandler handler;

    public Friday() {
        this.handler = new UserInputHandler();
    }

    public void start() {
        System.out.println(header);
        while (true) {
            String input = this.handler.getInput();
            if (input.equals("bye")) {
                System.out.println(this.ending);
                break;
            }
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
