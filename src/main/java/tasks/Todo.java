package tasks;

import commands.PriorityCommand;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
        assert !description.isEmpty() : "The description should not be empty.";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a Todo Task given a String that represents a Todo Task.
     *
     * @param str The String that represents the Todo Task.
     * @return
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
     * @return The priority level.
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
        return str.substring(9).trim(); // Assuming "[T][H][X] description" format
    }

}

