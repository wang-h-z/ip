package commands;

import common.*;
import storage.*;
import ui.*;

public class ListCommand extends Command {

    /**
     * Calls the Ui to handle the printing of the list, and outputs a terminal message to show the contents of the
     * TaskList.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        ui.listOutput(list);
    }

    /**
     * Returns false as a ListCommand does not stop the chatbot.
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
