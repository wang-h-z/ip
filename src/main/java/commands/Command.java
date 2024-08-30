package commands;

import exceptions.*;
import common.*;
import storage.*;
import ui.*;

public abstract class Command {

    /**
     * A method to accomplish certain tasks based on a command given by the user.
     * <p>
     * This abstract method is expected to be implemented by all subclass of the Command class.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException Thrown when encountering various exceptions in Command subclasses.
     */
    public abstract void execute(TaskList list, Ui ui, Storage storage) throws FridayException;

    /**
     * A method to determine if a Command should stop the chatbot.
     * @return A boolean to determine if the chatbot should stop.
     */
    public abstract boolean isExit();
}
