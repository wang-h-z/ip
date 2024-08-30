package commands;

import common.TaskList;
import storage.Storage;
import ui.Ui;

public class ByeCommand extends Command {

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        ui.showEnd();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
