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
     * Executes the command by parsing the input and adding a Deadline task to the list.
     *
     * @param list    List which stores all Tasks in the chatbot.
     * @param ui      The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException If there are missing parameters or invalid input.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        Task task = createDeadlineTask();
        list.add(task);
        storage.saveTasks(list);
        ui.addTaskToListOutput(task, list);
    }

    /**
     * Returns a formatted response for the GUI by parsing the input and adding a Deadline task to the list.
     *
     * @param list    List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return The response message to be displayed in the GUI.
     * @throws FridayException If there are missing parameters or invalid input.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        Task task = createDeadlineTask();
        list.add(task);
        storage.saveTasks(list);
        return String.format(""" 
                        Got it. I've added this task:
                          %s
                        Now you have %d tasks in the list.""", task, list.size());
    }

    /**
     * Helper method to create a Deadline task by parsing and validating the input.
     *
     * @return A new Deadline task.
     * @throws FridayException If there are missing parameters or invalid input.
     */
    private Task createDeadlineTask() throws FridayException {
        if (!input.contains("/by")) {
            throw new MissingCommandException("/by");
        }

        int end = input.indexOf("/by");
        String deadlineDescription = input.substring(input.indexOf("deadline") + "deadline".length(), end).trim();
        if (deadlineDescription.isEmpty()) {
            throw new DescriptionException("deadline");
        }

        String deadline = input.substring(end + 3).trim();
        if (deadline.isEmpty()) {
            throw new DeadlineException("deadline");
        }

        return new Deadline(deadlineDescription, deadline);
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

