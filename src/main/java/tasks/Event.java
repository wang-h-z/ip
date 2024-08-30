package tasks;

import java.time.LocalDateTime;

import common.DateTimeParser;

public class Event extends Task {
    private String from;
    private String to;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
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
        return "[E]" + super.toString() + " (from: " + DateTimeParser.format(fromDate, from) + " to: " + DateTimeParser.format(toDate, to) + ")";
    }

    public static Event fromString(String str) {
        boolean isDone = str.contains("[X]");
        int fromIndex = str.indexOf(" (from: ");
        int toIndex = str.indexOf(" to: ", fromIndex);
        String description = str.substring(7, fromIndex);
        String from = str.substring(fromIndex + 8, toIndex);
        String to = str.substring(toIndex + 5, str.length() - 1);
        Event event = new Event(description, from, to);
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }
}
