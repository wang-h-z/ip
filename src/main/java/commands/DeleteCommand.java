package commands;

import exceptions.FridayException;
import exceptions.InputException;
import common.TaskList;

import storage.Storage;

import tasks.Task;

import ui.Ui;

public class DeleteCommand extends Command {
    private final String input;

    public DeleteCommand(String input) {
        this.input = input;
    }

    /**
     * Handles the common logic of deleting a task from the list based on the index provided in the input.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return The Task object that was deleted.
     * @throws FridayException When the index is invalid or the list is empty.
     */
    private Task deleteTask(TaskList list, Storage storage) throws FridayException {
        String i = input.substring("delete".length()).trim();
        if (i.isEmpty()) {
            throw new InputException("Please give a valid task for me to delete. Try again.");
        }

        int idx = Integer.parseInt(i) - 1;
        if (list.isEmpty()) {
            throw new FridayException("Attempting to delete task from an empty list. Please add tasks first.");
        }
        if (idx >= list.size() || idx < 0) {
            throw new FridayException("Attempting to delete task which is not in the list. Please ensure the number is correct.");
        }

        Task task = list.remove(idx);
        storage.saveTasks(list);
        return task;
    }

    /**
     * Executes the DeleteCommand by deleting the task and showing output in the terminal.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException
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
     * @param list List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return The GUI response message.
     * @throws FridayException
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

