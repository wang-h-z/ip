package tasks;

import java.time.LocalDateTime;

import commands.PriorityCommand;
import common.DateTimeParser;

/**
 * Represents a Deadline task in the chatbot.
 * A Deadline has a description and a deadline date (by).
 * It can also have a priority level and a completion status.
 */
public class Deadline extends Task {

    private String by;
    private LocalDateTime byDate;

    /**
     * Constructs a Deadline task with the given description and deadline date.
     * If the deadline date is in an unrecognized format, it is stored as a string.
     *
     * @param description The description of the deadline task.
     * @param by The deadline date as a string.
     */
    public Deadline(String description, String by) {
        super(description);
        assert !description.isEmpty() : "Your description should not be empty.";
        this.by = by;
        assert !by.isEmpty() : "Your deadline should not be empty.";
        try {
            this.byDate = DateTimeParser.parse(by);
        } catch (IllegalArgumentException e) {
            this.byDate = null; //No correct format, deadline only recorded as a String
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeParser.format(byDate, by) + ")";
    }

    /**
     * Returns the deadline date of the task as a LocalDateTime object.
     *
     * @return The deadline date, or null if it is stored as a string.
     */
    public LocalDateTime getDate() {
        return this.byDate;
    }

    /**
     * Creates a Deadline object from a string representation of a Deadline task.
     * Parses the task's completion status, priority level, description, and deadline date.
     *
     * @param str The string representing the Deadline task.
     * @return The created Deadline object.
     */
    public static Deadline fromString(String str) {
        boolean isDone = str.contains("[X]");
        String level = extractPriorityLevel(str);
        String description = extractDescription(str);
        String by = extractByDate(str);

        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        deadline.setPriority(PriorityCommand.priorityString(level));
        return deadline;
    }

    /**
     * Extracts the priority level from the task string.
     *
     * @param str The task string.
     * @return The priority level as a string.
     */
    private static String extractPriorityLevel(String str) {
        return str.substring(4, 5);
    }

    /**
     * Extracts the description from the task string.
     *
     * @param str The task string.
     * @return The task description.
     */
    private static String extractDescription(String str) {
        int byIndex = str.indexOf(" (by: ");
        return str.substring(9, byIndex).trim();
    }

    /**
     * Extracts the 'by' date string from the task string.
     *
     * @param str The task string.
     * @return The 'by' date string.
     */
    private static String extractByDate(String str) {
        int byIndex = str.indexOf(" (by: ");
        return str.substring(byIndex + 6, str.length() - 1).trim();
    }
}

