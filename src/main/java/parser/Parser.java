package parser;

import commands.*;

import exceptions.FridayException;
import exceptions.InputException;

import common.TaskList;

import storage.Storage;

import tasks.Task;

public class Parser {

    private enum CommandEnum {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, DATE, FIND, UNKNOWN;

        public static CommandEnum fromString(String input) {
            try {
                return CommandEnum.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return UNKNOWN;
            }
        }
    }

    /**
     * Returns a Command Object. The method recieved an input string and determines which CommandEnum this input belongs
     * to. If the command given in the input string is valid, a respective command object is created and returned.
     *<p>
     * If the parser does not understand the input string, an InputException is thrown under the UNKOWN enum case.
     *
     * @param input The user input string.
     * @param list The TaskList.
     * @param storage Saves the tasks in a .txt file.
     * @return Command object to be executed in the chatbot.
     * @throws FridayException
     */
    public Command parse(String input, TaskList list, Storage storage)  throws FridayException {

        String[] parts = input.trim().split(" ", 2);
        CommandEnum command = CommandEnum.fromString(parts[0]);
        int idx;
        Task task;

        switch (command) {

            case BYE:
                return new ByeCommand();

            case LIST:
                return new ListCommand();

            case MARK:
                return new MarkCommand(input);

            case UNMARK:
                return new UnmarkCommand(input);

            case TODO:
                return new TodoCommand(input);

            case DEADLINE:
                return new DeadlineCommand(input);

            case EVENT:
                return new EventCommand(input);

            //Logic for Delete command
            case DELETE:
                return new DeleteCommand(input);

            case DATE:
                return new DateCommand(input);

            case FIND:
                return new FindCommand(input);

            case UNKNOWN:
            default:
                throw new InputException("Sorry, I have honestly 0 clue what you are saying... T.T");
        }
    }
}