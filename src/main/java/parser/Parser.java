package parser;

import commands.*;
import exceptions.*;
import common.*;
import storage.*;
import tasks.*;

public class Parser {

    private enum CommandEnum {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, DATE, UNKNOWN;

        public static CommandEnum fromString(String input) {
            try {
                return CommandEnum.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                return UNKNOWN;
            }
        }
    }

    public String printList(TaskList list) {
        String items = "";
        if (list.isEmpty()) {
            return "\t  There are currently no items in your list.";
        }
        for (int i = 0; i < list.size(); i++) {
            String d = String.format("\t  %s.%s", i + 1, list.get(i));
            if (i == list.size() - 1) {
                items = items.concat(d);
            } else {
                items = items.concat(d + "\n");
            }
        }
        return items;
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

            case UNKNOWN:
            default:
                throw new InputException("Sorry, I have honestly 0 clue what you are saying... T.T");
        }
    }
}