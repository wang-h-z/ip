package commands;

import exceptions.FridayException;
import exceptions.InputException;
import common.TaskList;

import storage.Storage;

import tasks.Task;

import ui.Ui;

public class DeleteCommand extends Command {
    private final String input;

    public DeleteCommand(String input){
        this.input = input;
    }

    /**
     * Separates the input string to identify an index to delete an item from the list. If the index is valid, the item
     * is deleted from the list. Storage updates the .txt file with the new list. The Ui then outputs a message to
     * notify the user this has been done.
     * <p>
     * Throws InputException when there is no index in the input. Throws FridayExceptions when attempting to delete an
     * item from an empty TaskList and when attempting to delete an item from an index which is out of bounds.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        String i = input.substring("delete".length()).trim();
        if (i.isEmpty()) {
            throw new InputException("Please give a valid item for me to delete. Try again.");
        }
        int idx = Integer.parseInt(i) - 1;
        if (list.isEmpty()) {
            throw new FridayException("Attempting to delete item from an empty list. Please add tasks first.");
        }
        if (idx >= list.size() || idx < 0) {
            throw new FridayException("Attempting to delete item which is not in the list. Please ensure the number is correct.");
        }
        Task task = list.remove(idx);
        storage.saveTasks(list);
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

    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        String i = input.substring("delete".length()).trim();
        if (i.isEmpty()) {
            throw new InputException("Please give a valid item for me to delete. Try again.");
        }
        int idx = Integer.parseInt(i) - 1;
        if (list.isEmpty()) {
            throw new FridayException("Attempting to delete item from an empty list. Please add tasks first.");
        }
        if (idx >= list.size() || idx < 0) {
            throw new FridayException("Attempting to delete item which is not in the list. Please ensure the number is correct.");
        }
        Task task = list.remove(idx);
        storage.saveTasks(list);
        return String.format(""" 
                        Got it. I've removed this task:
                          %s
                        Now you have %d tasks in the list.""", task, list.size());
    }

}
