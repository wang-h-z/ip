package commands;

import common.TaskList;

import storage.Storage;

import ui.Ui;

public class MarkCommand extends Command {

    private final String input;
    public MarkCommand (String input) {
        this.input = input;
    }

    /**
     * Separates the input string and identifies an index from the string. Marks the Task in the TaskList with that
     * index (as shown when printed) as completed. Calls the Ui to output a terminal message to the user to show that
     * this has been done.
     *
     * @param list List which stores all Tasks in the chatbot.
     * @param ui The interface which the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        int idx = Integer.parseInt(this.input.substring(5).trim());
        list.get(idx - 1).markAsDone();
        storage.saveTasks(list);
        String output = "\t Nice! I've marked this task as done:" + "\n" + "\t  " + list.get(idx - 1);
        ui.showOutput(output);
    }

    /**
     * Returns false as a MarkCommand does not stop the chatbot.
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
