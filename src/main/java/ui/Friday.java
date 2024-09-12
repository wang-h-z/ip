package ui;

import commands.Command;

import common.BotMessage;
import common.TaskList;

import exceptions.FridayException;

import parser.Parser;

import storage.Storage;

public class Friday {
    private final BotMessage header = new BotMessage("""
             \t  Hello! I'm ui.ui.Friday
             \t  What can I do for you?""");

    private final BotMessage ending = new BotMessage("""
             \t  Bye. Hope to see you again soon!""");

    private final Parser parser;
    private TaskList list;
    private Storage storage;
    private Ui ui;

    public Friday(String filepath) {
        this.ui = new Ui();
        this.parser = new Parser();
        this.list = new TaskList();
        this.storage = new Storage(filepath);
    }

    /**
     * A method to run the chatbot. The chatbot continuously receieves user input through the readCommand() method and
     * is parsed through the Parser object and a Command object is returned. The Command is always executed and
     * determines whether the chatbot should be stopped using isExit().
     *
     * A FridayException is caught when any point in the bot logic throws a FridayException or a subclass of the
     * Exception. 
     */
    public void start() {
        boolean isExit = false;
        this.ui.showWelcome();
        storage.loadTasks(list);
        while (!isExit) {
            try {
                String input = this.ui.readCommand();
                Command c = this.parser.parse(input, list, storage);
                c.execute(list, ui, storage);
                isExit = c.isExit();
            } catch (FridayException e) {
                ui.showOutput("\t" + e.getMessage());
            }
        } // end of loop
    }

    public void guiStart() {
        this.storage.loadTasks(list);
    }

    public String getResponse(String input) {
        try {
            Command c = this.parser.parse(input, list, storage);
            return c.guiResponse(list, storage);
        } catch (FridayException e) {
            return e.getMessage();
        }
    }

    public static void main(String args[]) {
        Friday bot = new Friday("data/Friday.txt");
        bot.start();
    }

}
