package parser;

import commands.*;

import exceptions.FridayException;

import common.TaskList;

import storage.Storage;

import tasks.Task;

public class Parser {

    /**
     * Enum representing all possible commands recognized by the chatbot.
     * Provides a method to convert a string input into an enum constant, defaulting to UNKNOWN for unrecognized inputs.
     */
    private enum CommandEnum {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, DATE, FIND, PRIORITY, UNKNOWN;

        public static CommandEnum fromString(String input) {
            try {
                return CommandEnum.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return UNKNOWN;
            }
        }
    }

    /**
     * Parses the user input and returns a Command object based on the identified command type.
     * The input string is analyzed to determine which CommandEnum it corresponds to, and the appropriate
     * command object is created. If the input string is not recognized as a valid command,
     * a FridayException is thrown.
     *
     * @param input The user input string.
     * @param list The TaskList object which stores all Tasks in the chatbot.
     * @param storage The Storage object that manages saving and loading tasks.
     * @return The corresponding Command object to be executed by the chatbot.
     * @throws FridayException If the input string contains an unrecognized command.
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

            case DELETE:
                return new DeleteCommand(input);

            case DATE:
                return new DateCommand(input);

            case FIND:
                return new FindCommand(input);

            case PRIORITY:
                return new PriorityCommand(input);

            case UNKNOWN:
            default:
                throw new FridayException("Sorry, I do not understand this command.");
        }
    }
}