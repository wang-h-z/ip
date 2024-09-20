package common;

/**
 * Represents a formatted message to be displayed by the chatbot.
 * Each message is wrapped with a line of spacing for consistent visual structure in the chatbot's output.
 */
public class BotMessage {
    private final String spacing = "____________________________________________________________";
    private final String description;

    public BotMessage(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return '\t' + spacing + '\n' + this.description + '\n' + '\t' + spacing + "\n";
    }
}
