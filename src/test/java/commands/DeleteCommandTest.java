package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import common.TaskList;
import exceptions.FridayException;
import storage.Storage;
import tasks.Task;
import ui.Ui;

public class DeleteCommandTest {

    public class TaskListStub extends TaskList {
        private ArrayList<Task> tasks;

        public TaskListStub() {
            tasks = new ArrayList<>();
            tasks.add(new Task("Test Task"));
        }

        @Override
        public Task remove(int idx) {
            return tasks.remove(idx);
        }

        @Override
        public int size() {
            return tasks.size();
        }

        @Override
        public boolean isEmpty() {
            return tasks.isEmpty();
        }
    }

    public class StorageStub extends Storage {
        public StorageStub() {
            super("");
        }

        @Override
        public void saveTasks(TaskList list) {
            // Do nothing for testing
        }
    }

    private Ui ui;
    private TaskList taskListStub;
    private Storage storageStub;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
        taskListStub = new TaskListStub();
        storageStub = new StorageStub();
    }

    @Test
    public void testDeleteCommandValidInput() throws FridayException {
        DeleteCommand command = new DeleteCommand("delete 1");
        command.execute(taskListStub, ui, storageStub);
        assertEquals(0, taskListStub.size());
    }

    @Test
    public void testDeleteCommandInvalidIndex() {
        DeleteCommand command = new DeleteCommand("delete 2");
        FridayException thrown = assertThrows(FridayException.class, () -> command.execute(taskListStub,
                ui, storageStub));
        assertEquals("Attempting to delete task which is not in the list. "
                + "Please ensure the number is correct.", thrown.getMessage());
    }

    @Test
    public void testDeleteCommandEmptyInput() {
        DeleteCommand command = new DeleteCommand("delete ");
        FridayException thrown = assertThrows(FridayException.class, () -> command.execute(taskListStub,
                ui, storageStub));
        assertEquals("Please give a valid task for me to delete. Try again.", thrown.getMessage());
    }

    @Test
    public void testDeleteCommandEmptyList() {
        taskListStub = new TaskListStub() {
            @Override
            public boolean isEmpty() {
                return true;
            }
        };

        DeleteCommand command = new DeleteCommand("delete 1");
        FridayException thrown = assertThrows(FridayException.class, () -> command.execute(taskListStub,
                ui, storageStub));
        assertEquals("Attempting to delete task from an empty list. Please add tasks first.",
                thrown.getMessage());
    }
}
