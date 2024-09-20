package commands;

import common.TaskList;
import exceptions.DescriptionException;
import storage.Storage;
import tasks.Task;
import tasks.Todo;
import ui.Ui;

/**
 * Represents a command to create a new Todo task in the chatbot.
 * The TodoCommand validates the user input, creates a new Todo task,
 * adds it to the TaskList, and saves it in Storage.
 */
public class TodoCommand extends Command {
    private final String input;

    public TodoCommand(String input) {
        this.input = input;
    }

    /**
     * Creates a new Todo task from the user input after validating the description.
     * Adds the new task to the TaskList and saves it in the storage.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The created Todo task.
     * @throws DescriptionException If the description is missing or invalid.
     */
    private Task createTodoTask(TaskList list, Storage storage) throws DescriptionException {
        if (this.input.trim().length() == "todo".length() || this.input.substring(5).trim().isEmpty()) {
            throw new DescriptionException("todo");
        }
        Task task = new Todo(this.input.substring(5).trim());
        list.add(task);
        storage.saveTasks(list);
        return task;
    }

    /**
     * Executes the TodoCommand by creating a new Todo task, updating the TaskList,
     * saving it to storage, and displaying the result to the user via the UI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     * @throws DescriptionException If the description is missing or invalid.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws DescriptionException {
        Task task = createTodoTask(list, storage);
        ui.addTaskToListOutput(task, list);
    }

    /**
     * Indicates that a TodoCommand does not stop the chatbot.
     * This method always returns false.
     *
     * @return false, indicating that the chatbot should not exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the TodoCommand and returns a response message for the GUI,
     * showing the newly added task and the updated task count.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return A string response for the GUI, confirming the addition of the Todo task.
     * @throws DescriptionException If the description is missing or invalid.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws DescriptionException {
        Task task = createTodoTask(list, storage);
        return String.format("""
                        Got it. I've added this task:
                          %s
                        Now you have %d tasks in the list.""", task, list.size());
    }
}

