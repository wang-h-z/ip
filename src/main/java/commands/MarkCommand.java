package commands;

import common.TaskList;

import exceptions.FridayException;
import storage.Storage;

import ui.Ui;

public class MarkCommand extends Command {

    private final String input;

    public MarkCommand(String input) {
        this.input = input;
    }

    /**
     * Marks the Task at the given index in the TaskList as done and returns a message.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return The message confirming the task is marked as done.
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
            throw new FridayException("Attempting to mark task which is not in the list. Please ensure the number is correct.");
        }
        list.get(idx).markAsDone();
        storage.saveTasks(list);
        return "Nice! I've marked this task as done:" + "\n\t  " + list.get(idx);
    }

    /**
     * Executes the MarkCommand by marking the task and showing output in the terminal.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
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
     * @param list List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return The GUI response message.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        return markTask(list, storage);
    }
}

