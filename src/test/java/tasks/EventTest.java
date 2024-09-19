package tasks;

import commands.PriorityCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventTest {

    @Test
    public void testToStringWithValidDates() {
        Event event = new Event("Project meeting", "2024-09-19 14:00", "2024-09-19 16:00");
        assertEquals("[E][ ][ ] Project meeting (from: Sep 19 2024, 2:00 PM to: Sep 19 2024, 4:00 PM)", event.toString());
    }

    @Test
    public void testFromStringWithValidDates() {
        String eventString = "[E][ ][ ] Project meeting (from: Sep 19 2024, 2:00 PM to: Sep 19 2024, 4:00 PM)";
        Event event = Event.fromString(eventString);
        assertEquals(event.toString(), eventString);
    }

    @Test
    public void testFromStringWithPriority() {
        String eventString = "[E][H][ ] Project meeting (from: Sep 19 2024, 2:00 PM to: Sep 19 2024, 4:00 PM)";
        Event event = Event.fromString(eventString);
        assertEquals("H", event.getPriority().toString());
        assertEquals(event.toString(), eventString);
    }

    @Test
    public void testMarkAsDone() {
        Event event = new Event("Project meeting", "2024-09-19 14:00", "2024-09-19 16:00");
        event.markAsDone();
        assertEquals("[E][ ][X] Project meeting (from: Sep 19 2024, 2:00 PM to: Sep 19 2024, 4:00 PM)", event.toString());
    }

    @Test
    public void testToStringWithDifferentDateFormats() {
        Event event = new Event("Deadline submission", "2024-12-02 14:00", "2024-12-02 18:00");
        assertEquals("[E][ ][ ] Deadline submission (from: Dec 02 2024, 2:00 PM to: Dec 02 2024, 6:00 PM)", event.toString());
    }

    @Test
    public void testEventWithoutPriority() {
        Event event = new Event("Casual meeting", "2024-11-10 10:00", "2024-11-10 12:00");
        assertEquals("[E][ ][ ] Casual meeting (from: Nov 10 2024, 10:00 AM to: Nov 10 2024, 12:00 PM)", event.toString());
    }

    @Test
    public void testSetPriority() {
        Event event = new Event("Annual review", "2024-11-15 09:00", "2024-11-15 11:00");
        event.setPriority(PriorityCommand.Priorities.HIGH);
        assertEquals("[E][H][ ] Annual review (from: Nov 15 2024, 9:00 AM to: Nov 15 2024, 11:00 AM)", event.toString());
    }

    @Test
    public void testEmptyDescriptionThrowsException() {
        assertThrows(AssertionError.class, () -> new Event("", "2024-11-15 09:00", "2024-11-15 11:00"));
    }

    @Test
    public void testEmptyFromDateThrowsException() {
        assertThrows(AssertionError.class, () -> new Event("Annual review", "", "2024-11-15 11:00"));
    }

    @Test
    public void testEmptyToDateThrowsException() {
        assertThrows(AssertionError.class, () -> new Event("Annual review", "2024-11-15 09:00", ""));
    }

    @Test
    public void testInvalidDateFormatFallsBackToString() {
        Event event = new Event("Project meeting", "invalid-date", "2024-11-15 11:00");
        assertEquals("[E][ ][ ] Project meeting (from: invalid-date to: Nov 15 2024, 11:00 AM)", event.toString());
    }
}


