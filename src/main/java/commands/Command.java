package commands;

import exceptions.*;
import common.*;
import storage.*;
import ui.*;

public abstract class Command {
    public abstract void execute(TaskList list, Ui ui, Storage storage) throws FridayException;
    public abstract boolean isExit();
}
