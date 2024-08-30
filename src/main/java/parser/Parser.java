package parser;

import commands.*;
import exceptions.*;
import common.*;
import storage.*;
import tasks.*;

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