package commands;

import common.TaskList;
import exceptions.FridayException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command to unmark a task as not done in the chatbot.
 * The UnmarkCommand parses the user's input to determine the index of the task to unmark,
 * updates the task's status in the TaskList, and saves the updated list to Storage.
 */
public class UnmarkCommand extends Command {

    private final String input;

    public UnmarkCommand(String input) {
        this.input = input;
    }

    /**
     * Unmarks the task at the specified index in the TaskList and saves the updated list to storage.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The message confirming the task is unmarked as done.
     * @throws FridayException If the input is invalid, or if the task index is out of bounds.
     */
    private String unmarkTask(TaskList list, Storage storage) throws FridayException {
        String i = input.substring("unmark".length()).trim();
        if (i.isEmpty()) {
            throw new FridayException("Please give a valid task for me to mark. Try again.");
        }
        int idx = Integer.parseInt(i) - 1;
        if (list.isEmpty()) {
            throw new FridayException("There are no tasks currently. Please add tasks first.");
        }
        if (idx >= list.size() || idx < 0) {
            throw new FridayException("Attempting to mark task which is not in the list. "
                    + "Please ensure the number is correct.");
        }
        list.get(idx).markAsUndone();
        storage.saveTasks(list);
        return "Nice! I've unmarked this task as done:" + "\n\t  " + list.get(idx);
    }

    /**
     * Executes the UnmarkCommand by unmarking the specified task and displaying the result in the CLI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     * @throws FridayException If the input is invalid or if there are issues with the task processing.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        String output = unmarkTask(list, storage);
        ui.showOutput(output);
    }

    /**
     * Returns false as an UnmarkCommand does not stop the chatbot.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the UnmarkCommand and returns the response message for the GUI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The GUI response message confirming the task has been unmarked as done.
     * @throws FridayException If the input is invalid or if there are issues with the task processing.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        return unmarkTask(list, storage);
    }
}

