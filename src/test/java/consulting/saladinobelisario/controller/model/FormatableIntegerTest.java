package consulting.saladinobelisario.controller.model;

import consulting.saladinobelisario.model.FormatableInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatableIntegerTest {

    @Test
    public void testToStringValue(){
        FormatableInteger formatableInteger1 = new FormatableInteger(0);
        assertEquals(formatableInteger1.toString(), "0");

        FormatableInteger formatableInteger2 = new FormatableInteger(500);
        assertEquals(formatableInteger2.toString(), "500 B");

        FormatableInteger formatableInteger3 = new FormatableInteger(2050);
        assertEquals(formatableInteger3.toString(), "2 kB");

        FormatableInteger formatableInteger4 = new FormatableInteger(3348576);
        assertEquals(formatableInteger4.toString(), "3 MB");
    }

    @Test
    public void testCompareResults(){
        FormatableInteger formatableInteger1 = new FormatableInteger(1000);
        FormatableInteger formatableInteger2 = new FormatableInteger(2000);

        assertEquals(formatableInteger1.compareTo(formatableInteger2), -1);
        assertEquals(formatableInteger2.compareTo(formatableInteger1), 1);
        assertEquals(formatableInteger2.compareTo(formatableInteger2), 0);
    }
}
