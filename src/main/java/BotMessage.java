public class BotMessage {
    private final String spacing = "____________________________________________________________";
    private final String description;

    public BotMessage(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.spacing + '\n' + this.description + '\n' + this.spacing;
    }
}
