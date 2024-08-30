package ui;

import java.util.Scanner;

import common.BotMessage;
import common.TaskList;

import tasks.Task;

public class Ui {

    Scanner scanner;

    private final String header = """
             \t  Hello! I'm Friday
             \t  What can I do for you?""";

    private final String ending = """
             \t  Bye. Hope to see you again soon!""";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

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

    public void showWelcome() {
        BotMessage welcome = new BotMessage(header);
        System.out.println(welcome);
    }

    public void showEnd() {
        BotMessage end = new BotMessage(ending);
        System.out.println(end);
    }

    public void showOutput(String str) {
        BotMessage output = new BotMessage(str);
        System.out.println(output);
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }

    public void listOutput(TaskList list) {
        BotMessage output = new BotMessage("\t Here are the tasks in your list:" + "\n" + this.printList(list));
        System.out.println(output);
    }

    public void addTaskToListOutput(Task task, TaskList list) {
        BotMessage output = new BotMessage(String.format(""" 
                        \t Got it. I've added this task:
                        \t  %s
                        \t Now you have %d tasks in the list.""", task, list.size()));
        System.out.println(output);
    }

    public void deleteOutput(Task task, TaskList list) {
        BotMessage output = new BotMessage(String.format(""" 
                        \t Got it. I've removed this task:
                        \t  %s
                        \t Now you have %d tasks in the list.""", task, list.size()));
        System.out.println(output);
    }

    public void dateOutput(String date, TaskList list) {
        BotMessage output = new BotMessage("\t Here are the tasks in that needs to be done by this date: " + date +
                "\n" + printList(list));
        System.out.println(output);
    }

}
