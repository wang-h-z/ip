package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import commands.PriorityCommand;

public class EventTest {

    @Test
    public void testToStringWithValidDates() {
        Event event = new Event("Project meeting", "Sep 19 2024, 2:00 pm", "Sep 19 2024, 4:00 pm");
        assertEquals("[E][ ][ ] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)",
                event.toString());
    }

    @Test
    public void testFromStringWithValidDates() {
        String eventString = "[E][ ][ ] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)";
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
        Event event = new Event("Project meeting", "Sep 19 2024, 2:00 pm", "Sep 19 2024, 4:00 pm");
        event.markAsDone();
        assertEquals("[E][ ][X] Project meeting (from: Sep 19 2024, 2:00 pm to: Sep 19 2024, 4:00 pm)",
                event.toString());
    }

    @Test
    public void testEventWithoutPriority() {
        Event event = new Event("Casual meeting", "Nov 10 2024, 10:00 am", "Nov 10 2024, 12:00 pm");
        assertEquals("[E][ ][ ] Casual meeting (from: Nov 10 2024, 10:00 am to: Nov 10 2024, 12:00 pm)",
                event.toString());
    }

    @Test
    public void testSetPriority() {
        Event event = new Event("Annual review", "Nov 15 2024, 9:00 am", "Nov 15 2024, 11:00 am");
        event.setPriority(PriorityCommand.Priorities.HIGH);
        assertEquals("[E][H][ ] Annual review (from: Nov 15 2024, 9:00 am to: Nov 15 2024, 11:00 am)",
                event.toString());
    }

    @Test
    public void testEmptyDescriptionThrowsException() {
        assertThrows(AssertionError.class, () -> new Event("", "Nov 15 2024, 9:00 am",
                "Nov 15 2024, 11:00 am"));
    }

    @Test
    public void testEmptyFromDateThrowsException() {
        assertThrows(AssertionError.class, () -> new Event("Annual review",
                "", "Nov 15 2024, 11:00 am"));
    }

    @Test
    public void testEmptyToDateThrowsException() {
        assertThrows(AssertionError.class, () -> new Event("Annual review",
                "Nov 15 2024, 9:00 am", ""));
    }

    @Test
    public void testInvalidDateFormatFallsBackToString() {
        Event event = new Event("Project meeting", "invalid-date", "Dec 02 2024, 11:00 am");
        assertEquals("[E][ ][ ] Project meeting (from: invalid-date to: Dec 02 2024, 11:00 am)", event.toString());
    }
}



