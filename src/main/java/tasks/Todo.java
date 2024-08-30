package tasks;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
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
        String description = str.substring(7); // Assuming "[T][X] description" format
        Todo todo = new Todo(description);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }
}
