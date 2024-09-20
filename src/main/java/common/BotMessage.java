package common;

public class BotMessage {
    private final String SPACING = "____________________________________________________________";
    private final String description;

    public BotMessage(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return '\t' + SPACING + '\n' + this.description + '\n' + '\t' + SPACING + "\n";
    }
}
