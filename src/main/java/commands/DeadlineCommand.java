package commands;

import exceptions.MissingCommandException;
import exceptions.DescriptionException;
import exceptions.FridayException;
import exceptions.DeadlineException;

import common.TaskList;

import storage.Storage;

import tasks.Task;
import tasks.Deadline;

import ui.Ui;

public class DeadlineCommand extends Command {

    private final String input;

    public DeadlineCommand(String input) {
        this.input = input;
    }

    /**
     * Separates a valid command String into description string and a by string. If these strings are valid, a Deadline
     * object is created. Adds the Deadline object into the TaskList. Storage saves the deadline object into the local
     * .txt file. The Ui then outputs a message to the terminal to notify the user this has been done.
     * <p>
     * A MissingCommand Exception is thrown when there is no /by command. A DescriptionException is thrown when there
     * is a missing description in the input.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        int end = this.input.indexOf("/by");
        if (!input.contains("/by")) {
            throw new MissingCommandException("/by");
        }
        String deadlineDescription = input.substring(input.indexOf("deadline") + "deadline".length(), end).trim();
        if (deadlineDescription.isEmpty()) {
            throw new DescriptionException("deadline");
        }
        String deadline = input.substring(end + 3).trim();
        if (deadline.isEmpty()) {
            throw new DeadlineException("deadline");
        }
        Task task = new Deadline(deadlineDescription, deadline);
        list.add(task);
        storage.saveTasks(list);
        ui.addTaskToListOutput(task, list);
    }

    /**
     * Returns false as a DeadlineCommand does not stop the chatbot.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
