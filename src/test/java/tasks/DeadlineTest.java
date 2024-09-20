package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import commands.PriorityCommand;
import common.DateTimeParser;

public class DeadlineTest {

    @Test
    public void testInvalidDateParsing() {
        Deadline deadline = new Deadline("Attend meeting", "Invalid date");
        assertEquals("[D][ ][ ] Attend meeting (by: Invalid date)", deadline.toString());
        assertNull(deadline.getDate()); // No LocalDateTime should be stored
    }

    @Test
    public void testFromStringParsing() {
        String taskString = "[D][H][X] Finish presentation (by: Sep 20 2024, 5:00 pm)";
        Deadline deadline = Deadline.fromString(taskString);
        assertTrue(deadline.isDone());
        assertEquals(PriorityCommand.Priorities.HIGH, deadline.getPriority());
        assertEquals("Finish presentation", deadline.getDescription());
        assertEquals("[D][H][X] Finish presentation (by: Sep 20 2024, 5:00 pm)", deadline.toString());
    }

    @Test
    public void testFromStringParsingWithInvalidDate() {
        String taskString = "[D][M][ ] Complete assignment (by: Invalid date)";
        Deadline deadline = Deadline.fromString(taskString);
        assertFalse(deadline.isDone());
        assertEquals(PriorityCommand.Priorities.MEDIUM, deadline.getPriority());
        assertEquals("Complete assignment", deadline.getDescription());
        assertEquals("[D][M][ ] Complete assignment (by: Invalid date)", deadline.toString());
        assertNull(deadline.getDate()); // Date should remain null
    }

    @Test
    public void testDeadlineWithoutPriority() {
        Deadline deadline = new Deadline("Submit project", "Sep 30 2024, 11:59 pm");
        assertNull(deadline.getPriority());
        assertEquals("[D][ ][ ] Submit project (by: Sep 30 2024, 11:59 pm)", deadline.toString());
    }

    @Test
    public void testSetPriority() {
        Deadline deadline = new Deadline("Submit proposal", "Sep 15 2024, 10:00 am");
        deadline.setPriority(PriorityCommand.Priorities.LOW);
        assertEquals(PriorityCommand.Priorities.LOW, deadline.getPriority());
        assertEquals("[D][L][ ] Submit proposal (by: Sep 15 2024, 10:00 am)", deadline.toString());
    }

    @Test
    public void testMarkAsDone() {
        Deadline deadline = new Deadline("Attend conference", "Sep 20 2024, 8:00 am");
        deadline.markAsDone();
        assertTrue(deadline.isDone());
        assertEquals("[D][ ][X] Attend conference (by: Sep 20 2024, 8:00 am)", deadline.toString());
    }

    @Test
    public void testEmptyDescriptionThrowsAssertionError() {
        assertThrows(AssertionError.class, () -> new Deadline("", "Sep 20 2024, 5:00 pm"));
    }

    @Test
    public void testEmptyDeadlineThrowsAssertionError() {
        assertThrows(AssertionError.class, () -> new Deadline("Submit report", ""));
    }

    @Test
    public void testInvalidDateThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DateTimeParser.parse("invalid date"));
    }
}

