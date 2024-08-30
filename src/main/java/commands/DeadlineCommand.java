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

    @Override
    public boolean isExit() {
        return false;
    }
}
