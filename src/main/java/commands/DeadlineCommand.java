package commands;

import common.TaskList;
import exceptions.DeadlineException;
import exceptions.DescriptionException;
import exceptions.FridayException;
import exceptions.MissingCommandException;
import storage.Storage;
import tasks.Deadline;
import tasks.Task;
import ui.Ui;

/**
 * Represents a command to create a Deadline task in the chatbot.
 * The DeadlineCommand parses user input, creates a Deadline task, adds it to the TaskList,
 * and saves it in Storage. It also handles interaction with the user through the Ui object or GUI.
 */
public class DeadlineCommand extends Command {

    private final String input;

    public DeadlineCommand(String input) {
        this.input = input;
    }

    /**
     * This method creates a Deadline task from the given user input. If the Deadline object
     * is successfully created, the method adds the Deadline to the TaskList, saves it in Storage and
     * outputs a response to the CLI.
     * A FridayException is thrown if the Deadline task was not created successfully.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
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
     * This method creates a Deadline task from the given user input. If the Deadline object
     * is successfully created, the method adds the Deadline to the TaskList, saves it in Storage and
     * outputs a response to the GUI.
     * A FridayException is thrown if the Deadline task was not created successfully.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
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
    private Deadline createDeadlineTask() throws FridayException {
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

