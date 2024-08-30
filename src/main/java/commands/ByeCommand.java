package commands;

import common.*;
import storage.*;
import ui.*;

public class ByeCommand extends Command {

    /**
     * The user interface will show an ending message when this method is called.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
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
}
