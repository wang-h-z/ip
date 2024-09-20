package commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import common.DateTimeParser;
import common.TaskList;
import exceptions.FridayException;
import storage.Storage;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import ui.Ui;

/**
 * Represents a command to filter tasks that are due before a specified date.
 * The DateCommand parses a date from user input and returns tasks with deadlines or events that occur before that date.
 */
public class DateCommand extends Command {

    private final String input;

    public DateCommand(String input) {
        this.input = input;
    }

    /**
     * Generates a response for the CLI interface by finding tasks due before a given date.
     * Throws an exception to handle missing or invalid inputs.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param ui The Ui object that handles interactions with the user.
     * @param storage The Storage object that manages saving and loading tasks.
     * @throws FridayException If an error occurs in parsing or processing the tasks.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        LocalDateTime userTime = parseDate();
        TaskList dateList = getTasksBeforeDate(list, userTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        ui.dateOutput(userTime.format(formatter), dateList);
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

    /**
     * Generates a response for the GUI interface by finding tasks due before a given date.
     * Throws an exception to handle missing or invalid inputs.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The formatted string response to be displayed in the GUI.
     * @throws FridayException If an error occurs in parsing or processing the tasks.
     */
    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        LocalDateTime userTime = parseDate();
        TaskList dateList = getTasksBeforeDate(list, userTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        Ui ui = new Ui();
        return String.format("Here are the tasks that need to be done by this date: "
                + userTime.format(formatter) + "\n" + ui.printList(dateList));
    }

    /**
     * Parses the date string from the input and checks for validity. If the date string is invalid, a
     * FridayException is thrown.
     *
     * @return LocalDateTime object representing the parsed date.
     * @throws FridayException If the date string is missing or cannot be parsed.
     */
    private LocalDateTime parseDate() throws FridayException {
        String date = this.input.substring("date".length()).trim();
        if (date.isEmpty()) {
            throw new FridayException("Please give me a date. Try again.");
        }
        try {
            return DateTimeParser.parse(date);
        } catch (IllegalArgumentException e) {
            throw new FridayException("I do not understand this format. Please try in this manner: dd/mm/yyyy HHmm");
        }
    }

    /**
     * Filters tasks from the TaskList that have deadlines or events before the given date.
     *
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param userTime LocalDateTime object representing the date to filter by.
     * @return A TaskList containing tasks due before the specified date.
     */
    private TaskList getTasksBeforeDate(TaskList list, LocalDateTime userTime) {
        TaskList dateList = new TaskList();
        for (Task t : list.getList()) {
            if (t instanceof Deadline) {
                Deadline d = (Deadline) t;
                if (d.getDate() != null && d.getDate().isBefore(userTime)) {
                    dateList.add(d);
                }
            } else if (t instanceof Event) {
                Event e = (Event) t;
                if (e.getDate() != null && e.getDate().isBefore(userTime)) {
                    dateList.add(e);
                }
            }
        }
        return dateList;
    }
}

