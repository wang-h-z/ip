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
     * This abstract method is expected to be implemented by all subclasses of the Command class.
     * Returns a formatted response for the CLI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     * @throws FridayException If the input is invalid or if there are issues with the task processing.
     */
    public abstract void execute(TaskList list, Ui ui, Storage storage) throws FridayException;

    /**
     * A method to determine if a Command should stop the chatbot.
     * @return A boolean to determine if the chatbot should stop.
     */
    public abstract boolean isExit();

    /**
     * A method to accomplish certain tasks based on a command given by the user.
     * This abstract method is expected to be implemented by all subclass of the Command class.
     * Returns a formatted response for the GUI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The response message to be displayed in the GUI.
     * @throws FridayException If there are missing parameters or invalid input.
     */
    public abstract String guiResponse(TaskList list, Storage storage) throws FridayException;
}
