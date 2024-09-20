package commands;

import common.TaskList;
import exceptions.FridayException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command to find tasks in the TaskList that match a given keyword.
 * The FindCommand allows the user to search for tasks containing a specific keyword,
 * displaying the results in both the CLI and GUI.
 */
public class FindCommand extends Command {

    private final String input;

    public FindCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the find command by searching the TaskList for tasks that match the provided keyword.
     * Displays the matching tasks to the user via the CLI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     * @throws FridayException If no keyword is provided for the search.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        TaskList filtered = findTasksByKeyword(list);
        ui.findOutput(filtered);
    }

    /**
     * Indicates whether the command causes the chatbot to exit.
     * FindCommand does not stop the chatbot, so this always returns false.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the FindCommand and returns the response message for the GUI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The response message for the GUI, showing matching tasks.
     * @throws FridayException If no keyword is provided in the input.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        TaskList filtered = findTasksByKeyword(list);
        return "Here are the matching tasks in your list:" + "\n" + new Ui().printList(filtered);
    }

    /**
     * Extracts the keyword from the input and searches the TaskList for tasks containing that keyword.
     *
     * @param list The TaskList to search through.
     * @return A TaskList containing the tasks that match the keyword.
     * @throws FridayException If no keyword is provided.
     */
    private TaskList findTasksByKeyword(TaskList list) throws FridayException {
        if (this.input.trim().length() == "find".length() || this.input.substring(5).trim().isEmpty()) {
            throw new FridayException("Please give me something to find");
        }
        String keyword = this.input.substring(5).trim();
        return list.findTasks(keyword);
    }
}
