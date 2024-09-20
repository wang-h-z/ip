package commands;

import common.TaskList;
import exceptions.FridayException;
import exceptions.MissingCommandException;
import storage.Storage;
import ui.Ui;

/**
 * Represents a command that sets the priority of a specific task in the TaskList.
 * The PriorityCommand parses the user's input to determine which task to prioritize and
 * sets the priority to one of the predefined levels: HIGH, MEDIUM, or LOW.
 */
public class PriorityCommand extends Command {

    /**
     * Enum representing the three priority levels: HIGH, MEDIUM, and LOW.
     * Each level returns its corresponding abbreviation (H, M, L) when converted to a string.
     */
    public enum Priorities {
        HIGH {
            @Override
            public String toString() {
                return "H";
            }
        },
        MEDIUM {
            @Override
            public String toString() {
                return "M";
            }
        },
        LOW {
            @Override
            public String toString() {
                return "L";
            }
        }
    }

    private final String input;

    public PriorityCommand(String input) {
        this.input = input;
    }

    /**
     * Parses the task index from the user input string.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @return Task index (1-based).
     * @throws FridayException If the input is invalid or the task index is out of bounds.
     */
    private int parseTaskIndex(TaskList list) throws FridayException {
        int priorityIndex = this.input.indexOf("priority") + "priority".length();
        int lvlIndex = this.input.indexOf("/lvl");
        String stringId = this.input.substring(priorityIndex, lvlIndex).trim();
        if (stringId.isEmpty()) {
            throw new FridayException("Please give a valid task index.");
        }
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new FridayException("Please give a valid numerical task index.");
        }
        if (id - 1 < 0 || id - 1 >= list.size()) {
            throw new FridayException("The task you are looking for does not exist in the list.");
        }
        return id - 1;
    }

    /**
     * Parses the priority level from the user input string.
     *
     * @return Parsed priority level.
     * @throws FridayException If the priority level is invalid.
     */
    private Priorities parsePriorityLevel() throws FridayException {
        int lvlIndex = this.input.indexOf("/lvl");
        String level = this.input.substring(lvlIndex + "/lvl".length()).trim().toUpperCase();
        if (level.isEmpty()) {
            throw new FridayException("Please enter a priority level. i.e HIGH/MEDIUM/LOW");
        }
        for (Priorities priority : Priorities.values()) {
            if (priority.name().equals(level)) {
                return priority;
            }
        }
        throw new FridayException("Please enter a valid priority level. i.e HIGH/MEDIUM/LOW");
    }

    /**
     * Sets the priority for the specified task.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param idx The task index to update.
     * @param priority The priority level to set.
     * @param storage The Storage object that manages saving and loading tasks.
     */
    private void setTaskPriority(TaskList list, int idx, Priorities priority, Storage storage) {
        list.get(idx).setPriority(priority);
        storage.saveTasks(list);
    }

    /**
     * Executes the PriorityCommand by setting the priority of the specified task and displaying the result
     * in the terminal.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     * @throws FridayException If there is an issue with the input or task processing.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        if (!this.input.contains("/lvl")) {
            throw new MissingCommandException("/lvl");
        }
        int taskIdx = parseTaskIndex(list);
        Priorities priority = parsePriorityLevel();
        setTaskPriority(list, taskIdx, priority, storage);
        ui.showOutput("\t Nice! I've added a priority to this task:\n\t  " + list.get(taskIdx));
    }

    /**
     * Indicates whether the command causes the chatbot to exit.
     * PriorityCommand does not stop the chatbot, so this always returns false.
     *
     * @return false, indicating that the chatbot should not exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the PriorityCommand and returns the response message for the GUI.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The response message for the GUI confirming the priority change.
     * @throws FridayException If there is an issue with the input or task processing.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        if (!this.input.contains("/lvl")) {
            throw new MissingCommandException("/lvl");
        }
        int taskIdx = parseTaskIndex(list);
        Priorities priority = parsePriorityLevel();
        setTaskPriority(list, taskIdx, priority, storage);
        return "Nice! I've added a priority to this task:\n\t  " + list.get(taskIdx);
    }

    /**
     * Converts a priority string into a Priorities enum.
     *
     * @param input The priority string (H, M, L).
     * @return The corresponding priority enum.
     */
    public static Priorities priorityString(String input) {
        switch (input) {
        case "H":
            return Priorities.HIGH;
        case "M":
            return Priorities.MEDIUM;
        case "L":
            return Priorities.LOW;
        default:
            return null;
        }
    }
}

