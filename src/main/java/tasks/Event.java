package tasks;

import java.time.LocalDateTime;

import commands.PriorityCommand;
import common.DateTimeParser;


/**
 * Represents an Event task in the chatbot. An Event has a description,
 * a start date (/from), and an end date (/to). The Event task also supports
 * priority levels and task completion status.
 */
public class Event extends Task {
    private String from;
    private String to;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    /**
     * Constructs an Event with the given description, start date (/from), and end date (/to).
     * The dates are parsed into LocalDateTime objects, and validation is done to ensure
     * that the start date is not after the end date.
     *
     * @param description The description of the event.
     * @param from The start date of the event as a string.
     * @param to The end date of the event as a string.
     * @throws IllegalArgumentException If the description is empty or if the /from date is after the /to date.
     */
    public Event(String description, String from, String to) {
        super(description);
        assert !description.isEmpty() : "Your description should not be empty.";
        this.from = from;
        assert !from.isEmpty() : "The /from String should not be empty.";
        this.to = to;
        assert !to.isEmpty() : "The /to String should not be empty.";
        try {
            this.fromDate = DateTimeParser.parse(from);
        } catch (IllegalArgumentException e) {
            //No correct format, deadline only recorded as a String
            this.fromDate = null;
        }
        try {
            this.toDate = DateTimeParser.parse(to);
        } catch (IllegalArgumentException e) {
            //No correct format, deadline only recorded as a String
            this.toDate = null;
        }

        if (this.fromDate != null && this.toDate != null) {
            if (this.fromDate.isAfter(this.toDate)) {
                throw new IllegalArgumentException("The /from date cannot be after the /to date.");
            }
        }
    }

    /**
     * Returns the end date of the event as a LocalDateTime object.
     *
     * @return The end date, or null if it is stored as a string.
     */
    public LocalDateTime getDate() {
        return this.toDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeParser.format(fromDate, from) + " to: "
                + DateTimeParser.format(toDate, to) + ")";
    }

    /**
     * Creates an Event object from a string representation of an Event task.
     * Parses the task's completion status, priority level, description, start date, and end date.
     *
     * @param str The string representing the Event task.
     * @return The created Event object.
     */
    public static Event fromString(String str) {
        boolean isDone = str.contains("[X]");
        String level = extractPriorityLevel(str);
        String description = extractDescription(str);
        String from = extractFromDate(str);
        String to = extractToDate(str);

        Event event = new Event(description, from, to);
        if (isDone) {
            event.markAsDone();
        }
        event.setPriority(PriorityCommand.priorityString(level));
        return event;
    }

    /**
     * Extracts the priority level from the event string.
     *
     * @param str The event string.
     * @return The priority level.
     */
    private static String extractPriorityLevel(String str) {
        return str.substring(4, 5);
    }

    /**
     * Extracts the description from the event string.
     *
     * @param str The event string.
     * @return The event description.
     */
    private static String extractDescription(String str) {
        int fromIndex = str.indexOf(" (from: ");
        return str.substring(9, fromIndex).trim();
    }

    /**
     * Extracts the 'from' date string from the event string.
     *
     * @param str The event string.
     * @return The 'from' date string.
     */
    private static String extractFromDate(String str) {
        int fromIndex = str.indexOf(" (from: ");
        int toIndex = str.indexOf(" to: ", fromIndex);
        return str.substring(fromIndex + 8, toIndex).trim();
    }

    /**
     * Extracts the 'to' date string from the event string.
     *
     * @param str The event string.
     * @return The 'to' date string.
     */
    private static String extractToDate(String str) {
        int toIndex = str.indexOf(" to: ");
        return str.substring(toIndex + 5, str.length() - 1).trim();
    }

}
