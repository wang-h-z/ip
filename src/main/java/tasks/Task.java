package tasks;

import commands.PriorityCommand;

public class Task {

    protected String description;
    protected boolean isDone;
    protected PriorityCommand.Priorities priority;

    public Task(String description) {
            this.description = description;
            assert !this.description.isEmpty() : "The description should not be empty.";
            this.isDone = false;
            this.priority = null;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getPriorityIcon() {
        if (this.priority == null) {
            return " ";
        } else {
            return this.priority.toString();
        }
    }
    public void markAsDone() {
        this.isDone = true;
    }
    public void markAsUndone() {
        this.isDone = false;
    }

    public void setPriority(PriorityCommand.Priorities priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", this.getPriorityIcon(), this.getStatusIcon(), this.description);
    }

    /**
     * Returns a Task given a String representing a Task.
     *
     * An IllegalArgumentException is thrown if the input string is not recognizable.
     *
     * @param str The String that represents a Task.
     * @return
     */
    public static Task fromString(String str) {
        // Check the task type and delegate to the appropriate subclass
        if (str.startsWith("[T]")) {
            return Todo.fromString(str);
        } else if (str.startsWith("[D]")) {
            return Deadline.fromString(str);
        } else if (str.startsWith("[E]")) {
            return Event.fromString(str);
        } else {
            throw new IllegalArgumentException("Unknown task type: " + str);
        }
    }

    public String getDescription() {
        return this.description;
    }
}
