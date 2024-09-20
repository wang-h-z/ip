package commands;

import common.TaskList;

import exceptions.FridayException;
import exceptions.InputException;
import storage.Storage;

import tasks.Task;

import ui.Ui;

public class UnmarkCommand extends Command {

    private final String input;

    public UnmarkCommand(String input) {
        this.input = input;
    }

    /**
     * Unmarks the task at the specified index in the TaskList and saves the updated list to storage.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return The message confirming the task is unmarked as done.
     */
    private String unmarkTask(TaskList list, Storage storage) throws FridayException {
        String i = input.substring("unmark".length()).trim();
        if (i.isEmpty()) {
            throw new InputException("Please give a valid task for me to mark. Try again.");
        }
        int idx = Integer.parseInt(i) - 1;
        if (list.isEmpty()) {
            throw new FridayException("There are no tasks currently. Please add tasks first.");
        }
        if (idx >= list.size() || idx < 0) {
            throw new FridayException("Attempting to mark task which is not in the list. Please ensure the number is correct.");
        }
        list.get(idx).markAsUndone();
        storage.saveTasks(list);
        return "Nice! I've unmarked this task as done:" + "\n\t  " + list.get(idx);
    }

    /**
     * Executes the UnmarkCommand by unmarking the task and showing output in the terminal.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
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
     * Executes the UnmarkCommand and returns the response for the GUI.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return The GUI response message.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        return unmarkTask(list, storage);
    }
}

