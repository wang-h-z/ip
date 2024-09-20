package commands;

import common.TaskList;
import exceptions.FridayException;
import storage.Storage;
import tasks.Task;
import ui.Ui;

/**
 * Represents a command to delete a task from the task list in the chatbot.
 * The DeleteCommand parses the user's input to determine the index of the task to delete,
 * removes the task from the TaskList, and updates the storage.
 */
public class DeleteCommand extends Command {
    private final String input;

    public DeleteCommand(String input) {
        this.input = input;
    }

    /**
     * Deletes a task from the task list based on the index provided in the user input.
     * Updates the storage after the task is deleted.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage Storage object that manages saving and loading tasks.
     * @return The Task object that was deleted.
     * @throws FridayException If the input index is invalid, or if the task list is empty.
     */
    private Task deleteTask(TaskList list, Storage storage) throws FridayException {
        String i = input.substring("delete".length()).trim();
        if (i.isEmpty()) {
            throw new FridayException("Please give a valid task for me to delete. Try again.");
        }

        int idx = Integer.parseInt(i) - 1;
        if (list.isEmpty()) {
            throw new FridayException("Attempting to delete task from an empty list. Please add tasks first.");
        }
        if (idx >= list.size() || idx < 0) {
            throw new FridayException("Attempting to delete task which is not in the list. "
                    + "Please ensure the number is correct.");
        }

        Task task = list.remove(idx);
        storage.saveTasks(list);
        return task;
    }

    /**
     * Executes the DeleteCommand by deleting the task and showing output in the terminal.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     * @throws FridayException If an error occurs while deleting the task (e.g., invalid index).
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        Task task = deleteTask(list, storage);
        ui.deleteOutput(task, list);
    }

    /**
     * Returns false as a DeleteCommand does not stop the chatbot.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the DeleteCommand and returns the response for the GUI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The response message for the GUI after the task is deleted.
     * @throws FridayException If an error occurs while deleting the task (e.g., invalid index).
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        Task task = deleteTask(list, storage);
        return String.format("""
                        Got it. I've removed this task:
                          %s
                        Now you have %d tasks in the list.""", task, list.size());
    }
}

