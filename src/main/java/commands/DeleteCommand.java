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

    @Override
    public boolean isExit() {
        return false;
    }

}
