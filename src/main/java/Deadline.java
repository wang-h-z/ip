public class Deadline extends Task {

    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

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
