package tasks;

import java.time.LocalDateTime;

import commands.PriorityCommand;

import common.DateTimeParser;

public class Deadline extends Task {

    private String by;
    private LocalDateTime byDate;

    public Deadline(String description, String by) {
        super(description);
        assert !description.isEmpty() : "Your description should not be empty.";
        this.by = by;
        assert !by.isEmpty() : "Your deadline should not be empty.";
        try {
            this.byDate = DateTimeParser.parse(by);
        } catch (IllegalArgumentException e){
            this.byDate = null; //No correct format, deadline only recorded as a String
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeParser.format(byDate, by) + ")";
    }

    public LocalDateTime getDate() {
        return this.byDate;
    }

    /**
     * Returns a Deadline object given a String that represents a Deadline task.
     *
     * @param str The String that represents the Deadline task.
     * @return
     */
    public static Deadline fromString(String str) {
        boolean isDone = str.contains("[X]");
        String level = str.substring(4, 5);
        int byIndex = str.indexOf(" (by: ");
        String description = str.substring(9, byIndex).trim();
        String by = str.substring(byIndex + 6, str.length() - 1).trim();
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        deadline.setPriority(PriorityCommand.priorityString(level));
        return deadline;
    }
}
