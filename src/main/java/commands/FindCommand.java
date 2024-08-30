package commands;

import common.TaskList;

import exceptions.FridayException;
import storage.Storage;

import ui.Ui;

public class FindCommand extends Command {

    private final String input;

    public FindCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException  {
        if (this.input.trim().length() == "find".length() || this.input.substring(5).trim().isEmpty()) {
            throw new FridayException("Please give me something to find");
        }
        String keyword = this.input.substring(5).trim();
        TaskList filtered = list.findTasks(keyword);
        ui.findOutput(filtered);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
