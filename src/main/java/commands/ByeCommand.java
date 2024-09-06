package commands;

import common.TaskList;
import storage.Storage;
import tasks.Task;
import ui.Ui;

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

    @Override
    public String guiResponse(TaskList list, Storage storage) {
        return "Bye. Hope to see you again soon!";
    }

}
