package commands;

import exceptions.DescriptionException;
import exceptions.FridayException;
import exceptions.FromException;
import exceptions.MissingCommandException;
import exceptions.ToException;

import common.TaskList;

import storage.Storage;

import tasks.Event;
import tasks.Task;

import ui.Ui;

public class EventCommand extends Command {

    private final String input;

    public EventCommand(String input) {
        this.input = input;
    }

    /**
     * Separates the input into a description string, a from string and a to string. If these strings are valid, an
     * event object is created. The Event object is then added into the TaskList. Storage saves the event object
     * into the local .txt file. The Ui outputs a message to the terminal to notify the user this has been done.
     * <p>
     * Throws a MissingCommandException if either /from or /to commands are missing from
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        if (!this.input.contains("/from")) {
            throw new MissingCommandException("/from");
        }
        if (!this.input.contains("/to")) {
            throw new MissingCommandException("/to");
        }
        int eventIndex = this.input.indexOf("event") + "event".length();
        int fromIndex = this.input.indexOf("/from");
        int toIndex = this.input.indexOf("/to");
        String eventDescription = this.input.substring(eventIndex, fromIndex).trim();
        if (eventDescription.isEmpty()) {
            throw new DescriptionException("event");
        }
        String from = this.input.substring(fromIndex + "/from".length(), toIndex).trim();
        if (from.isEmpty()) {
            throw new FromException();
        }
        String to = this.input.substring(toIndex + "/to".length()).trim();
        if (to.isEmpty()) {
            throw new ToException();
        }
        Task task = new Event(eventDescription, from, to);
        list.add(task);
        storage.saveTasks(list);
        ui.addTaskToListOutput(task, list);
    }

    /**
     * Returns false as an EventCommand does not stop the chatbot.
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
