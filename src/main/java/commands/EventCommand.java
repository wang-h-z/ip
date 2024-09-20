package commands;

import common.TaskList;
import exceptions.DescriptionException;
import exceptions.FridayException;
import exceptions.MissingCommandException;
import storage.Storage;
import tasks.Event;
import tasks.Task;
import ui.Ui;

/**
 * Represents a command to create an Event task in the chatbot.
 * The EventCommand parses user input, creates an Event object with a description, start (/from), and end (/to) dates,
 * adds it to the TaskList, and saves it in Storage.
 */
public class EventCommand extends Command {

    private final String input;

    public EventCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the EventCommand by parsing the input, creating an Event object,
     * and adding it to the task list.
     * It also updates the storage and informs the user through the CLI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     * @throws FridayException if there are issues with the input or task processing.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        validateInput();
        Task event = createEventFromInput();
        list.add(event);
        storage.saveTasks(list);
        ui.addTaskToListOutput(event, list);
    }

    /**
     * Returns false as an EventCommand does not stop the chatbot.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the EventCommand by parsing the input, creating an Event object,
     * and adding it to the task list.
     * It also updates the storage and informs the user through the GUI.
     *
     * @param list The TaskList object that stores all tasks.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The response message for the GUI after the task is added.
     * @throws FridayException If there are issues with the input or task processing.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        validateInput();
        Event event = createEventFromInput();
        list.add(event);
        storage.saveTasks(list);
        return String.format("""
                        Got it. I've added this task:
                          %s
                        Now you have %d tasks in the list.""", event, list.size());
    }

    /**
     * Parses the input to create an Event object by extracting the event description,
     * start (/from), and end (/to) dates.
     * Validates that the /from and /to commands are correctly ordered and present.
     *
     * @return An Event object created from the input.
     * @throws MissingCommandException If either /from or /to is missing.
     * @throws DescriptionException If the event description is empty.
     * @throws FridayException If the from or to date is empty, or if /from comes after /to.
     */
    private Event createEventFromInput() throws FridayException {
        int fromIndex = this.input.indexOf("/from");
        int toIndex = this.input.indexOf("/to");

        if (fromIndex > toIndex) {
            throw new FridayException("Seems like you have used /to before /from. Use /from before /to.");
        }

        String eventDescription = extractDescription(fromIndex);
        String from = extractFromDate(fromIndex, toIndex);
        String to = extractToDate(toIndex);

        try {
            return new Event(eventDescription, from, to);
        } catch (IllegalArgumentException e) {
            throw new FridayException("Looks like your start date is later than your end date. Please try again");
        }
    }

    /**
     * Validates that the input contains both /from and /to commands.
     *
     * @throws MissingCommandException If either /from or /to is missing from the input.
     */
    private void validateInput() throws MissingCommandException {
        if (!this.input.contains("/from")) {
            throw new MissingCommandException("/from");
        }
        if (!this.input.contains("/to")) {
            throw new MissingCommandException("/to");
        }
    }

    /**
     * Extracts the event description from the input.
     *
     * @param fromIndex The index of the /from command.
     * @return The event description string.
     * @throws DescriptionException if the description is empty.
     */
    private String extractDescription(int fromIndex) throws DescriptionException {
        int eventIndex = this.input.indexOf("event") + "event".length();
        String eventDescription = this.input.substring(eventIndex, fromIndex).trim();
        if (eventDescription.isEmpty()) {
            throw new DescriptionException("event");
        }
        return eventDescription;
    }

    /**
     * Extracts the from date from the input.
     *
     * @param fromIndex The index of the /from command.
     * @param toIndex   The index of the /to command.
     * @return The from date string.
     * @throws FridayException if the from date is empty.
     */
    private String extractFromDate(int fromIndex, int toIndex) throws FridayException {
        String from = this.input.substring(fromIndex + "/from".length(), toIndex).trim();
        if (from.isEmpty()) {
            throw new FridayException("An event must have a starting date. Please try again using /from.");
        }
        return from;
    }

    /**
     * Extracts the to date from the input.
     *
     * @param toIndex The index of the /to command.
     * @return The to date string.
     * @throws FridayException if the to date is empty.
     */
    private String extractToDate(int toIndex) throws FridayException {
        String to = this.input.substring(toIndex + "/to".length()).trim();
        if (to.isEmpty()) {
            throw new FridayException("An event must have a deadline. Please try again with /to");
        }
        return to;
    }
}


