package commands;

import common.TaskList;
import exceptions.FridayException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command to terminate the chatbot session.
 * The ByeCommand is used when the user wants to exit the chatbot, displaying a goodbye message.
 */
public class ByeCommand extends Command {

    /**
     * Friday CLI will show an ending message when this method is called.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        ui.showEnd();
    }

    /**
     * The ByeCommand signifies that the user would like to stop the chatbot.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * Friday GUI will show an ending message when this method is called.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The response message to be displayed in the GUI.
     * @throws FridayException If there are missing parameters or invalid input.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        return "Bye. Hope to see you again soon!";
    }

}
