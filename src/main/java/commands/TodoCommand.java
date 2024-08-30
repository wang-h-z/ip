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

    @Override
    public boolean isExit() {
        return false;
    }
}
