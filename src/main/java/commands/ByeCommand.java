package commands;

import common.*;
import storage.*;
import ui.*;

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
