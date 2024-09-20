package tasks;

import commands.PriorityCommand;

/**
 * Represents a generic task in the chatbot.
 * A task has a description, a completion status, and an optional priority level.
 * Specific types of tasks (Todo, Deadline, Event) extend this class.
 */
public class Task {

    protected String description;
    protected boolean isDone;
    protected PriorityCommand.Priorities priority;

    /**
     * Constructs a Task object with the given description.
     * By default, the task is not done and has no priority.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        assert !this.description.isEmpty() : "The description should not be empty.";
        this.isDone = false;
        this.priority = null;
    }

    /**
     * Returns the status icon representing whether the task is done or not.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the priority icon representing the task's priority level.
     *
     * @return The priority letter (H, M, L), or a blank space if no priority is set.
     */
    public String getPriorityIcon() {
        if (this.priority == null) {
            return " ";
        } else {
            return this.priority.toString();
        }
    }
    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Checks if the task is done.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the task's priority level.
     *
     * @return The task's priority level, or null if no priority is set.
     */
    public PriorityCommand.Priorities getPriority() {
        return this.priority;
    }

    /**
     * Sets the task's priority level.
     *
     * @param priority The priority level to set (HIGH, MEDIUM, LOW).
     */
    public void setPriority(PriorityCommand.Priorities priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", this.getPriorityIcon(), this.getStatusIcon(), this.description);
    }

    /**
     * Creates a Task object from a string representation of a task.
     * The method identifies the task type (Todo, Deadline, or Event) based on the string prefix and delegates
     * task creation to the respective subclass.
     *
     * @param str The string representing the task.
     * @return The created Task object.
     * @throws IllegalArgumentException If the task type is unrecognized.
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

    /**
     * Returns the task description.
     *
     * @return The task description.
     */
    public String getDescription() {
        return this.description;
    }
}
