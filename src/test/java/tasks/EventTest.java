package tasks;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EventTest {

    @Test
    public void testToStringWithValidDates() {
        Event event = new Event("Project meeting", "2024-09-19 14:00", "2024-09-19 16:00");
        assertEquals("[E][ ][ ] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)", event.toString());
    }

    @Test
    public void testToStringWithInvalidDates() {
        Event event = new Event("Project meeting", "invalid date", "another invalid date");
        assertEquals("[E][ ][ ] Project meeting (from: invalid date to: another invalid date)", event.toString());
    }

    @Test
    public void testFromStringWithValidDates() {
        String input = "[E][ ][ ] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)";
        Event event = Event.fromString(input);
        assertEquals("[E][ ][ ] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)", event.toString());
        assertFalse(event.isDone());
        assertEquals(LocalDateTime.of(2024, 9, 19, 16, 0), event.getDate());
    }

    @Test
    public void testFromStringWithDoneStatus() {
        String input = "[E][ ][X] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)";
        Event event = Event.fromString(input);
        assertTrue(event.isDone());
        assertEquals("[E][ ][X] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)", event.toString());
    }

    @Test
    public void testFromStringWithPriority() {
        String input = "[E][H][ ] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)";
        Event event = Event.fromString(input);
        assertEquals("H", event.getPriority().toString());
        assertEquals("[E][H][ ] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)", event.toString());
    }

    @Test
    public void testMarkAsDone() {
        Event event = new Event("Project meeting", "2024-09-19 14:00", "2024-09-19 16:00");
        event.markAsDone();
        assertTrue(event.isDone(), "The task should be marked as done");
        assertEquals("[E][ ][X] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)", event.toString());
    }

    @Test
    public void testEmptyDescriptionThrowsAssertionError() {
        assertThrows(AssertionError.class, () -> new Event("", "2024-09-19 14:00", "2024-09-19 16:00"),
                "Empty description should throw an AssertionError");
    }

    @Test
    public void testEmptyFromThrowsAssertionError() {
        assertThrows(AssertionError.class, () -> new Event("Project meeting", "", "2024-09-19 16:00"),
                "Empty /from should throw an AssertionError");
    }

    @Test
    public void testEmptyToThrowsAssertionError() {
        assertThrows(AssertionError.class, () -> new Event("Project meeting", "2024-09-19 14:00", ""),
                "Empty /to should throw an AssertionError");
    }

    @Test
    public void testToStringWithDifferentDateFormats() {
        // Test another supported date format: "d/M/yyyy HHmm" -> "2/12/2024 1800"
        Event event = new Event("Deadline submission", "2/12/2024 1400", "2/12/2024 1800");
        assertEquals("[E][ ][ ] Deadline submission (from: Dec 02 2024, 2:00 pm to: Dec 02 2024, 6:00 pm)", event.toString());

        // Test another supported format: "MM/dd/yyyy hh:mm a" -> "12/02/2024 02:00 PM"
        Event event2 = new Event("Exam preparation", "12/02/2024 02:00 PM", "12/02/2024 04:00 PM");
        assertEquals("[E][ ][ ] Exam preparation (from: 12/02/2024 02:00 PM to: 12/02/2024 04:00 PM)", event2.toString());
    }
}
