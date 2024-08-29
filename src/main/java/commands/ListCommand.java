package commands;

import common.*;
import storage.*;
import ui.*;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        ui.listOutput(list);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
