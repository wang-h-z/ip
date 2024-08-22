public class BotMessage {
    private final String spacing = "____________________________________________________________";
    private final String description;

    public BotMessage(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return '\t' + this.spacing + '\n' + this.description + '\n' + '\t' + this.spacing + "\n";
    }
}
