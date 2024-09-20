package commands;

import exceptions.DescriptionException;
import exceptions.FridayException;
import exceptions.MissingCommandException;

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
     * Separates the input into a description string, a from string, and a to string. If these strings are valid, an
     * event object is created. The Event object is then added to the TaskList. Storage saves the event object
     * into the local .txt file. The Ui outputs a message to the terminal to notify the user this has been done.
     * Throws a MissingCommandException if either /from or /to commands are missing.
     * Throws IllegalArgumentException if /from comes after /to.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException if there are issues with the input or task processing.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
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

    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        Task event = createEventFromInput();
        list.add(event);
        storage.saveTasks(list);
        return String.format("""
                        Got it. I've added this task:
                          %s
                        Now you have %d tasks in the list.""", event, list.size());
    }

    /**
     * Creates an Event object by parsing the input, ensuring the proper order and presence of the /from and /to
     * keywords, and validating the description and date fields.
     *
     * @return An Event object created from the input.
     * @throws MissingCommandException If either /from or /to is missing.
     * @throws DescriptionException If the description is empty.
     * @throws FridayException If the from date is empty.
     * @throws FridayException If the to date is empty.
     */
    private Task createEventFromInput() throws FridayException {
        validateInput();

        int fromIndex = this.input.indexOf("/from");
        int toIndex = this.input.indexOf("/to");

        // Ensure /from comes before /to
        if (fromIndex > toIndex) {
            throw new FridayException("/from must come before /to");
        }

        String eventDescription = extractDescription(fromIndex);
        String from = extractFromDate(fromIndex, toIndex);
        String to = extractToDate(toIndex);

        return new Event(eventDescription, from, to);
    }

    /**
     * Validates that the input contains both /from and /to commands.
     *
     * @throws MissingCommandException if either /from or /to is missing.
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


