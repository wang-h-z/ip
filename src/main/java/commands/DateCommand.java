package commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import common.DateTimeParser;
import exceptions.FridayException;
import exceptions.InputException;
import storage.Storage;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import common.TaskList;
import ui.Ui;

public class DateCommand extends Command {

    private final String input;

    public DateCommand(String input) {
        this.input = input;
    }

    /**
     * Extracts the date from the input, checks if the format is valid, and filters tasks based on the date.
     * <p>
     * Throws InputException if the date format is invalid or missing.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException If an error occurs in parsing or processing the tasks.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        LocalDateTime userTime = parseDate();
        TaskList dateList = getTasksBeforeDate(list, userTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
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

    /**
     * Generates a response for the GUI interface by finding tasks due before a given date.
     * Throws InputException if the date format is invalid or missing.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param storage Stores previous and current Task objects.
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
     * Parses the date string from the input and checks for validity. If the date string is invalid, an
     * InputException is thrown.
     *
     * @return LocalDateTime object representing the parsed date.
     * @throws InputException If the date string is missing or cannot be parsed.
     */
    private LocalDateTime parseDate() throws InputException {
        String date = this.input.substring("date".length()).trim();
        if (date.isEmpty()) {
            throw new InputException("Please give me a date. Try again.");
        }
        try {
            return DateTimeParser.parse(date);
        } catch (IllegalArgumentException e) {
            throw new InputException("I do not understand this format. Please try in this manner: dd/mm/yyyy HHmm");
        }
    }

    /**
     * Filters tasks from the TaskList that have deadlines or events before the given date.
     *
     * @param list TaskList containing all tasks.
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

