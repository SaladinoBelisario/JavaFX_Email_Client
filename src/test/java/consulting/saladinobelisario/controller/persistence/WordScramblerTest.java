package consulting.saladinobelisario.controller.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordScramblerTest {

    @Test
    public void testScrambler(){
        String testString = "ABC123Def";
        String encodedString = WordScrambler.base64Encode(testString);
        System.out.println(encodedString);
        String decodedString = WordScrambler.base64Decode(encodedString);
        System.out.println(decodedString);
        assertEquals(testString, decodedString);
    }
}
