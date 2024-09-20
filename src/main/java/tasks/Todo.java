package tasks;

import commands.PriorityCommand;

/**
 * Represents a Todo task in the chatbot.
 * A Todo task consists of a description, a completion status, and an optional priority level.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the provided description.
     *
     * @param description The description of the Todo task.
     */
    public Todo(String description) {
        super(description);
        assert !description.isEmpty() : "The description should not be empty.";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Creates a Todo task from a string representation of a Todo task.
     * Parses the task's completion status, priority level, and description from the string.
     *
     * @param str The string that represents the Todo task.
     * @return The created Todo task.
     */
    public static Todo fromString(String str) {
        boolean isDone = str.contains("[X]");
        String level = extractPriorityLevel(str);
        String description = extractDescription(str);

        Todo todo = new Todo(description);
        if (isDone) {
            todo.markAsDone();
        }
        todo.setPriority(PriorityCommand.priorityString(level));
        return todo;
    }

    /**
     * Extracts the priority level from the task string.
     *
     * @param str The task string.
     * @return The priority level as a single character (H, M, L).
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
        return str.substring(9).trim();
    }

}

