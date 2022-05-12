package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    @Test
    void testInitClass() {
        Differ dif = new Differ();
        assertEquals(dif.getClass(), Differ.class);
    }

}
