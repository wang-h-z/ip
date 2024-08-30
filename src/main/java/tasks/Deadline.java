package tasks;

import common.DateTimeParser;
import java.time.LocalDateTime;

public class Deadline extends Task {

    private String by;
    private LocalDateTime byDate;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        try {
            this.byDate = DateTimeParser.parse(by);
        } catch (IllegalArgumentException e){
            this.byDate = null;
            //No correct format, deadline only recorded as a String
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
        int byIndex = str.indexOf(" (by: ");
        String description = str.substring(7, byIndex);
        String by = str.substring(byIndex + 6, str.length() - 1);
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }
}
