package tasks;

import java.time.LocalDateTime;

import commands.PriorityCommand;

import common.DateTimeParser;

public class Event extends Task {
    private String from;
    private String to;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public Event(String description, String from, String to) {
        super(description);
        assert !description.isEmpty() : "Your description should not be empty.";
        this.from = from;
        assert !from.isEmpty(): "The /from String should not be empty.";
        this.to = to;
        assert !to.isEmpty(): "The /to String should not be empty.";
        try {
            this.fromDate = DateTimeParser.parse(from);
        } catch (IllegalArgumentException e) {
            //No correct format, deadline only recorded as a String
            this.fromDate = null;
        } try {
            this.toDate = DateTimeParser.parse(to);
        } catch (IllegalArgumentException e) {
            //No correct format, deadline only recorded as a String
            this.toDate = null;
        }
    }

    public LocalDateTime getDate() {
        return this.toDate;
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeParser.format(fromDate, from) + " to: "
                + DateTimeParser.format(toDate, to) + ")";
    }

    /**
     * Returns an Event given a String that represents the Event task.
     *
     * @param str The String that represents the Event Task.
     * @return
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
