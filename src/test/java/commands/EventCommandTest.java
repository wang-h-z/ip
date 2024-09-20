package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import common.TaskList;
import exceptions.FridayException;
import exceptions.MissingCommandException;
import storage.Storage;
import tasks.Event;
import tasks.Task;
import ui.Ui;



public class EventCommandTest {

    private TaskList taskListStub;
    private Storage storageStub;
    private Ui uiStub;

    @BeforeEach
    public void setUp() {
        taskListStub = new TaskListStub();
        storageStub = new StorageStub();
        uiStub = new UiStub();
    }

    @Test
    public void testEventCommandValidInput() throws FridayException {
        EventCommand command = new EventCommand("event Meeting /from Sep 20 2024, "
                + "2:00 pm /to Sep 20 2024, 4:00 pm");
        command.execute(taskListStub, uiStub, storageStub);
        assertEquals(1, taskListStub.size());
        Event event = (Event) taskListStub.get(0);
        assertEquals("[E][ ][ ] Meeting (from: Sep 20 2024, 2:00 pm to: Sep 20 2024, 4:00 pm)",
                event.toString());
    }

    @Test
    public void testEventCommandInvalidFromDate() throws FridayException {
        EventCommand command = new EventCommand("event Meeting /from InvalidDate /to Sep 20 2024, 4:00 pm");
        command.execute(taskListStub, uiStub, storageStub);
        assertEquals(1, taskListStub.size());
        Event event = (Event) taskListStub.get(0);
        assertEquals("[E][ ][ ] Meeting (from: InvalidDate to: Sep 20 2024, 4:00 pm)", event.toString());
    }

    @Test
    public void testEventCommandMissingFrom() {
        EventCommand command = new EventCommand("event Meeting /to Sep 20 2024, 4:00 pm");
        MissingCommandException thrown = assertThrows(MissingCommandException.class, () -> command.execute(taskListStub,
                uiStub, storageStub));
        assertEquals("You are using this task without the necessary /from command. Try again.",
                thrown.getMessage());
    }

    @Test
    public void testEventCommandFromAfterTo() {
        EventCommand command = new EventCommand("event Meeting /from 09/09/2024 1800"
                + " /to 09/09/2024 1500");
        FridayException thrown = assertThrows(FridayException.class, () -> command.execute(taskListStub,
                uiStub, storageStub));
        assertEquals("Looks like your start date is later than your end date. Please try again",
                thrown.getMessage());
    }

    public class TaskListStub extends TaskList {
        // Optionally override specific behavior if needed, otherwise it inherits from TaskList.
    }

    public class StorageStub extends Storage {
        public StorageStub() {
            super(""); // Empty file path for testing purposes
        }

        @Override
        public void saveTasks(TaskList list) {
            // Simulated saving behavior
        }

        @Override
        public void loadTasks(TaskList list) {
            // Simulated loading behavior
        }
    }

    public class UiStub extends Ui {
        @Override
        public void addTaskToListOutput(Task task, TaskList list) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + list.size() + " tasks in the list.");
        }

        @Override
        public void showOutput(String message) {
            System.out.println(message);
        }
    }
}

