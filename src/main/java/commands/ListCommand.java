package commands;

import common.TaskList;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command to list all tasks in the chatbot.
 * The ListCommand displays the current tasks stored in the TaskList using both the CLI and GUI.
 */
public class ListCommand extends Command {

    /**
     * Calls the Ui to handle the printing of the list, and outputs a CLI message to show the contents of the
     * TaskList.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        ui.listOutput(list);
    }

    /**
     * Indicates whether the command causes the chatbot to exit.
     * ListCommand does not stop the chatbot, so this always returns false.
     *
     * @return false, indicating that the chatbot should not exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the ListCommand and returns the response message for the GUI.
     * Displays the list of tasks in the TaskList.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The response message for the GUI showing the tasks in the list.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) {
        Ui ui = new Ui();
        return "Here are the tasks in your list:" + "\n" + ui.printList(list);
    }
}
