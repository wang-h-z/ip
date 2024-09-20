package commands;

import exceptions.DescriptionException;

import common.TaskList;

import storage.Storage;

import tasks.Task;
import tasks.Todo;

import ui.Ui;

public class TodoCommand extends Command {
    private final String input;

    public TodoCommand(String input) {
        this.input = input;
    }

    /**
     * Creates a new Todo task from the user input after validating the description.
     *
     * @param list The list of tasks.
     * @param storage The storage for saving tasks.
     * @return The created Todo task.
     * @throws DescriptionException If the description is invalid.
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
     * Executes the TodoCommand by creating a new Todo task and displaying the result via the UI.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws DescriptionException If the description is invalid.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws DescriptionException {
        Task task = createTodoTask(list, storage);
        ui.addTaskToListOutput(task, list);
    }

    /**
     * Returns false as a TodoCommand does not stop the chatbot.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the TodoCommand and returns the response for the GUI.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
     * @return A string response for the GUI.
     * @throws DescriptionException If the description is invalid.
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

