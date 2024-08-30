package commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import common.*;
import exceptions.*;
import storage.Storage;
import ui.Ui;
import tasks.*;

public class DateCommand extends Command {

    private final String input;

    public DateCommand(String input) {
        this.input = input;
    }

    /**
     * Retrieves the relevant dates given by the user if the format of the input date matches one of the accepted
     * formats in the DateTimeParser. Finally, the Ui responds by outputting a message to the terminal.
     * <p>
     * An InputException is thrown the date is empty. An IllegalArgumentException is thrown if the format is ambiguous.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        String date = this.input.substring("date".length()).trim();
        if (date.isEmpty()) {
            throw new InputException("Please give me a date. Try again.");
        }
        LocalDateTime userTime;
        try {
            userTime = DateTimeParser.parse(date);
        } catch (IllegalArgumentException e) {
            throw new InputException("I do not understand this format. Please try in this manner: d/M/yyyy HHmm");
        }
        TaskList dateList = new TaskList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        for (Task t : list.getList()) {
            if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                if (d.getDate() != null && d.getDate().isBefore(userTime)) {
                    dateList.add(d);
                }
            }
            if (t instanceof Event) {
                Event e = (Event) t;
                if (e.getDate() != null && e.getDate().isBefore(userTime)) {
                    dateList.add(e);
                }
            }
        }
        ui.dateOutput(userTime.format(formatter), list);
    }

    /**
     * Returns false as the chatbot continues running after a DateCommand.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

}
