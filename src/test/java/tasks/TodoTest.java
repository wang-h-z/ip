package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void toStringTest() {
        assertEquals("[T][ ] test", new Todo("test").toString());
    }

    @Test
    public void fromStringTest() {
        assertEquals(new Todo("test").toString(), "[T][ ] test");
    }


}
