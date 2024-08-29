package commands;

import exceptions.*;
import common.*;
import storage.*;
import tasks.*;
import ui.*;

public class EventCommand extends Command {

    private final String input;
    public EventCommand(String input) {
        this.input = input;
    }

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

    @Override
    public boolean isExit() {
        return false;
    }
}
