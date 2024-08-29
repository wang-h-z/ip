import java.util.Scanner;

public class Ui {

    Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }
    private final String header = """
             \t  Hello! I'm Friday
             \t  What can I do for you?""";

    private final String ending = """
             \t  Bye. Hope to see you again soon!""";

    public void showWelcome() {
        BotMessage welcome = new BotMessage(header);
        System.out.println(welcome);
    }

    public void showEnd() {
        BotMessage end = new BotMessage(ending);
        System.out.println(end);
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }

}
