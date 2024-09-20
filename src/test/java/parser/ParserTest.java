package parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commands.ByeCommand;
import commands.Command;
import commands.DateCommand;
import commands.DeadlineCommand;
import commands.DeleteCommand;
import commands.EventCommand;
import commands.FindCommand;
import commands.ListCommand;
import commands.MarkCommand;
import commands.PriorityCommand;
import commands.TodoCommand;
import commands.UnmarkCommand;
import common.TaskList;
import exceptions.FridayException;
import storage.Storage;

class ParserTest {

    public class TaskListStub extends TaskList {

    };

    public class StorageStub extends Storage {
        public StorageStub() {
            super("");
        }
    };

    private Parser parser;
    private TaskList taskListStub;
    private Storage storageStub;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
        taskListStub = new TaskListStub();
        storageStub = new StorageStub();
    }

    @Test
    public void unknownCommandThrowsException() {
        assertThrows(FridayException.class, () -> parser.parse("invalid command", taskListStub, storageStub));
    }

    @Test
    public void testByeCommand() throws FridayException {
        Command command = parser.parse("bye", taskListStub, storageStub);
        assertTrue(command instanceof ByeCommand);
    }

    @Test
    public void testListCommand() throws FridayException {
        Command command = parser.parse("list", taskListStub, storageStub);
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void testMarkCommand() throws FridayException {
        Command command = parser.parse("mark", taskListStub, storageStub);
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void testUnmarkCommand() throws FridayException {
        Command command = parser.parse("unmark", taskListStub, storageStub);
        assertTrue(command instanceof UnmarkCommand);
    }

    @Test
    public void testTodoCommand() throws FridayException {
        Command command = parser.parse("todo", taskListStub, storageStub);
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void testDeadlineCommand() throws FridayException {
        Command command = parser.parse("deadline", taskListStub, storageStub);
        assertTrue(command instanceof DeadlineCommand);
    }

    @Test
    public void testEventCommand() throws FridayException {
        Command command = parser.parse("event", taskListStub, storageStub);
        assertTrue(command instanceof EventCommand);
    }

    @Test
    public void testDeleteCommand() throws FridayException {
        Command command = parser.parse("delete", taskListStub, storageStub);
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void testDateCommand() throws FridayException {
        Command command = parser.parse("date", taskListStub, storageStub);
        assertTrue(command instanceof DateCommand);
    }

    @Test
    public void testFindCommand() throws FridayException {
        Command command = parser.parse("find", taskListStub, storageStub);
        assertTrue(command instanceof FindCommand);
    }

    @Test
    public void testPriorityCommand() throws FridayException {
        Command command = parser.parse("priority", taskListStub, storageStub);
        assertTrue(command instanceof PriorityCommand);
    }

}
