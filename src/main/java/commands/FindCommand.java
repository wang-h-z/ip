package commands;

import common.TaskList;

import exceptions.FridayException;
import storage.Storage;

import ui.Ui;

public class FindCommand extends Command {

    private final String input;

    public FindCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the find command by searching for tasks that match the provided keyword.
     *
     * @param list    List which stores all Tasks in the chatbot.
     * @param ui      The interface that the user will be interacting with.
     * @param storage Stores previous and current Task objects.
     * @throws FridayException If no keyword is provided for the search.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        TaskList filtered = findTasksByKeyword(list);
        ui.findOutput(filtered);
    }

    /**
     * Returns false as the chatbot continues running after a FindCommand.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        TaskList filtered = findTasksByKeyword(list);
        return "Here are the matching tasks in your list:" + "\n" + new Ui().printList(filtered);
    }

    /**
     * Extracts the keyword from the input and filters the TaskList based on the keyword.
     *
     * @param list The TaskList to search through.
     * @return A TaskList containing the tasks that match the keyword.
     * @throws FridayException If no keyword is provided.
     */
    private TaskList findTasksByKeyword(TaskList list) throws FridayException {
        if (this.input.trim().length() == "find".length() || this.input.substring(5).trim().isEmpty()) {
            throw new FridayException("Please give me something to find");
        }
        String keyword = this.input.substring(5).trim();
        return list.findTasks(keyword);
    }
}
