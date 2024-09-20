package commands;

import exceptions.FridayException;

import common.TaskList;

import storage.Storage;

import tasks.Task;

import ui.Friday;
import ui.Ui;

public abstract class Command {

    /**
     * A method to accomplish certain tasks based on a command given by the user.
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

    /**
     * Returns a formatted response for the GUI.
     *
     * @param list    List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return The response message to be displayed in the GUI.
     * @throws FridayException If there are missing parameters or invalid input.
     */
    public abstract String guiResponse(TaskList list, Storage storage) throws FridayException;
}
