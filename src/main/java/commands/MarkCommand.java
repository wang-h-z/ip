package commands;

import common.TaskList;

import storage.Storage;

import ui.Ui;

public class MarkCommand extends Command {

    private final String input;
    public MarkCommand (String input) {
        this.input = input;
    }
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        int idx = Integer.parseInt(this.input.substring(5).trim());
        list.get(idx - 1).markAsDone();
        storage.saveTasks(list);
        String output = "\t Nice! I've marked this task as done:" + "\n" + "\t  " + list.get(idx - 1);
        ui.showOutput(output);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
