package consulting.saladinobelisario.controller.persistence;

import java.util.Base64;


public class WordScrambler {

    private static Base64.Encoder enc = Base64.getEncoder();
    private static Base64.Decoder dec = Base64.getDecoder();


    public static String base64Encode(String text) {
        try {
            return enc.encodeToString(text.getBytes());
        } catch (Exception e) {
            return null;
        }
    }

    public static String base64Decode(String text) {
        try {
            return new String(dec.decode(text.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }
}
