package ui;

import java.util.Scanner;

import common.BotMessage;
import common.TaskList;
import tasks.Task;

/**
 * The Ui class handles all user interactions in the chatbot.
 * It manages displaying messages, lists, and tasks in the terminal, and it reads user input.
 */
public class Ui {

    private Scanner scanner;

    private final String header = """
             \t  Hello! I'm Friday
             \t  What can I do for you?""";

    private final String ending = """
             \t  Bye. Hope to see you again soon!""";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns a string representing the current tasks in the TaskList.
     * If the list is empty, an appropriate message is returned.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @return A formatted string representing the list of tasks.
     */
    public String printList(TaskList list) {
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

    /**
     * Displays the welcome message when the chatbot starts.
     */
    public void showWelcome() {
        BotMessage welcome = new BotMessage(header);
        System.out.println(welcome);
    }

    /**
     * Displays the goodbye message when the chatbot ends.
     */
    public void showEnd() {
        BotMessage end = new BotMessage(ending);
        System.out.println(end);
    }

    /**
     * Displays a generic output message.
     *
     * @param str The string to display as output.
     */
    public void showOutput(String str) {
        BotMessage output = new BotMessage(str);
        System.out.println(output);
    }

    /**
     * Reads and returns the user input command.
     *
     * @return The user input command as a string.
     */
    public String readCommand() {
        return this.scanner.nextLine();
    }

    /**
     * Displays the list of tasks in the TaskList.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     */
    public void listOutput(TaskList list) {
        BotMessage output = new BotMessage("\t Here are the tasks in your list:" + "\n" + this.printList(list));
        System.out.println(output);
    }

    /**
     * Displays a message when a task is added to the TaskList.
     *
     * @param task The task that was added.
     * @param list The TaskList object which stores all Tasks in the chatbot.
     */
    public void addTaskToListOutput(Task task, TaskList list) {
        BotMessage output = new BotMessage(String.format("""
                        \t Got it. I've added this task:
                        \t  %s
                        \t Now you have %d tasks in the list.""", task, list.size()));
        System.out.println(output);
    }

    /**
     * Displays a message when a task is deleted from the TaskList.
     *
     * @param task The task that was deleted.
     * @param list The TaskList object which stores all Tasks in the chatbot.
     */
    public void deleteOutput(Task task, TaskList list) {
        BotMessage output = new BotMessage(String.format("""
                        \t Got it. I've removed this task:
                        \t  %s
                        \t Now you have %d tasks in the list.""", task, list.size()));
        System.out.println(output);
    }

    /**
     * Displays tasks that need to be done by a specified date.
     *
     * @param date The date by which the tasks need to be done.
     * @param list The TaskList object which stores all Tasks in the chatbot.
     */
    public void dateOutput(String date, TaskList list) {
        BotMessage output = new BotMessage("\t Here are the tasks in that needs to be done by this date: "
                + date + "\n" + printList(list));
        System.out.println(output);
    }

    /**
     * Displays tasks that match a given keyword or search criteria.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     */
    public void findOutput(TaskList list) {
        BotMessage output = new BotMessage("\t Here are the matching tasks in your list:"
                + "\n" + this.printList(list));
        System.out.println(output);
    }

}
