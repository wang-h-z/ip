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
     * Retrieves a description string from the user input. If the description is valid, a Todo object is created. The
     * Todo object is then added into the TaskList. Storage saves the new list. The Ui then outputs a terminal
     * message that this has happened.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws DescriptionException
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws DescriptionException {
        if (this.input.trim().length() == 4 || this.input.substring(5).trim().isEmpty()) {
            throw new DescriptionException("todo");
        }
        Task task = new Todo(this.input.substring(5).trim());
        list.add(task);
        storage.saveTasks(list);
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
}
