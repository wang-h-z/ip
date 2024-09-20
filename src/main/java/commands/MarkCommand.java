package commands;

import common.TaskList;
import exceptions.FridayException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command to mark a task as done in the chatbot.
 * The MarkCommand parses the user's input to determine the index of the task to mark as done,
 * updates the task's status in the TaskList, and saves the updated list to storage.
 */
public class MarkCommand extends Command {

    private final String input;

    public MarkCommand(String input) {
        this.input = input;
    }

    /**
     * Marks the task at the given index in the TaskList as done and saves the updated list in storage.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return A message confirming that the task is marked as done.
     * @throws FridayException If the input is invalid, or the task index is out of bounds.
     */
    private String markTask(TaskList list, Storage storage) throws FridayException {
        String i = input.substring("mark".length()).trim();
        if (i.isEmpty()) {
            throw new FridayException("Please give a valid task for me to mark. Try again.");
        }
        int idx = Integer.parseInt(i) - 1;
        if (list.isEmpty()) {
            throw new FridayException("There are no tasks currently. Please add tasks first.");
        }
        if (idx >= list.size() || idx < 0) {
            throw new FridayException("Attempting to mark task which is not in the list."
                    + " Please ensure the number is correct.");
        }
        list.get(idx).markAsDone();
        storage.saveTasks(list);
        return "Nice! I've marked this task as done:" + "\n\t  " + list.get(idx);
    }

    /**
     * Executes the MarkCommand by marking the task and showing output in the CLI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        String output = markTask(list, storage);
        ui.showOutput(output);
    }

    /**
     * Returns false as a MarkCommand does not stop the chatbot.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the MarkCommand and returns the response for the GUI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The GUI response message.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        return markTask(list, storage);
    }
}

