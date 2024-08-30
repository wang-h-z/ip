package tasks;

import exceptions.InputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import common.TaskList;

import parser.Parser;

import storage.Storage;
public class ParserTest {

    public class TaskListStub extends TaskList {

    };

    public class StorageStub extends Storage {
        public StorageStub() {
            super("");
        }
    };
    @Test
    public void unknownCommandThrowsException() {
        assertThrows(InputException.class, () -> new Parser().parse("invalid input", new TaskListStub(), new StorageStub()));
    }
}
