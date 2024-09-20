package ui;

import commands.Command;
import common.BotMessage;
import common.TaskList;
import exceptions.FridayException;
import parser.Parser;
import storage.Storage;

/**
 * The Friday class represents the main entry point for the chatbot application.
 * It handles interactions between the user, the task list, and the storage system,
 * and processes commands from both the terminal and the GUI.
 */
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

    /**
     * Constructs the Friday chatbot, initializing the user interface, parser, task list, and storage.
     * The chatbot reads from and writes to a storage file specified by the file path.
     *
     * @param filepath The path to the storage file where tasks will be loaded and saved.
     */
    public Friday(String filepath) {
        this.ui = new Ui();
        this.parser = new Parser();
        this.list = new TaskList();
        this.storage = new Storage(filepath);
    }

    /**
     * Starts the chatbot in a terminal environment.
     * Continuously reads user input, processes commands, and determines whether to exit based on the commands issued.
     * Catches and displays a FridayException when an error occurs during the bot's execution.
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

    /**
     * Initializes the chatbot in a GUI environment by loading tasks from storage.
     */
    public void guiStart() {
        this.storage.loadTasks(list);
    }

    /**
     * Processes a user command from the GUI and returns the bot's response.
     * Parses the input, executes the command, and returns the appropriate response message.
     *
     * @param input The user input from the GUI.
     * @return The bot's response message based on the input command.
     */
    public String getResponse(String input) {
        try {
            Command c = this.parser.parse(input, list, storage);
            return c.guiResponse(list, storage);
        } catch (FridayException e) {
            return e.getMessage();
        }
    }

    /**
     * The main method to start the chatbot.
     * Initializes the bot with a specified storage file and starts the terminal interaction.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Friday bot = new Friday("data/Friday.txt");
        bot.start();
    }

}
