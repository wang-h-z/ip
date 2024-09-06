package commands;

import common.TaskList;

import storage.Storage;

import ui.Ui;

public class UnmarkCommand extends Command {

    private final String input;
    public UnmarkCommand(String input) {
        this.input = input;
    }

    /**
     * Separates the input string and identifies an index from the string. Unmarks the Task in the TaskList with that
     * index (as shown when printed) as completed. Calls the Ui to output a terminal message to the user to show that
     * this has been done.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        int idx = Integer.parseInt(input.substring(7).trim());
        list.get(idx - 1).markAsUndone();
        storage.saveTasks(list);
        ui.showOutput("\t OK, I've marked this task as not done yet:" + "\n" + "\t  " + list.get(idx - 1));
    }

    /**
     * Returns false as a UnmarkCommand does not stop the chatbot.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public String guiResponse(TaskList list, Storage storage) {
        int idx = Integer.parseInt(input.substring(7).trim());
        list.get(idx - 1).markAsUndone();
        storage.saveTasks(list);
        return "OK, I've marked this task as not done yet:" + "\n" + list.get(idx - 1);
    }
}
