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

    @Override
    public boolean isExit() {
        return false;
    }

}
