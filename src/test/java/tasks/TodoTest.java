package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import commands.PriorityCommand;

public class TodoTest {

    @Test
    public void toStringTest() {
        Todo todo = new Todo("buy milk");
        assertEquals("[T][ ][ ] buy milk", todo.toString(),
                "The toString method should return the correct format");
    }

    @Test
    public void fromStringTestNotDone() {
        Todo todo = Todo.fromString("[T][ ][ ] buy milk");
        assertEquals("[T][ ][ ] buy milk", todo.toString(),
                "The fromString method should correctly parse a Todo that is not done");
    }

    @Test
    public void fromStringTestDone() {
        Todo todo = Todo.fromString("[T][ ][X] buy milk");
        assertTrue(todo.isDone(), "The Todo should be marked as done when [X] is present");
        assertEquals("[T][ ][X] buy milk", todo.toString(),
                "The fromString method should correctly parse a Todo that is done");
    }

    @Test
    public void fromStringTestWithPriority() {
        Todo todo = Todo.fromString("[T][H][ ] buy milk");
        assertEquals("H", todo.getPriority().toString(), "The priority should be set to HIGH");
        assertEquals("[T][H][ ] buy milk", todo.toString(),
                "The fromString method should correctly parse a Todo with HIGH priority");
    }

    @Test
    public void markAsDoneTest() {
        Todo todo = new Todo("buy milk");
        todo.markAsDone();
        assertTrue(todo.isDone(), "The task should be marked as done");
        assertEquals("[T][ ][X] buy milk", todo.toString(),
                "The toString method should reflect the task being done");
    }

    @Test
    public void setPriorityTest() {
        Todo todo = new Todo("buy milk");
        todo.setPriority(PriorityCommand.Priorities.HIGH);
        assertEquals("H", todo.getPriority().toString(), "The priority should be set to HIGH");
        assertEquals("[T][H][ ] buy milk", todo.toString(),
                "The toString method should reflect the HIGH priority");
    }

    @Test
    public void emptyDescriptionThrowsAssertionError() {
        assertThrows(AssertionError.class, () -> new Todo(""),
                "Empty description should throw an AssertionError");
    }
}
