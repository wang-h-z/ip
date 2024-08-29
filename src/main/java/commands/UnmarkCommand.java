package commands;

import common.*;
import storage.*;
import ui.*;

public class UnmarkCommand extends Command {

    private final String input;
    public UnmarkCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        int idx = Integer.parseInt(input.substring(7).trim());
        list.get(idx - 1).markAsUndone();
        storage.saveTasks(list);
        ui.showOutput("\t OK, I've marked this task as not done yet:" + "\n" + "\t  " + list.get(idx - 1));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
