import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LanguageDetectorTest {

    double tolerance = 0.01;

    @Test
    public void testApply1() {
        LanguageDetector ld = new LanguageDetector(2, 1001);
        ld.learnLanguage("ape", "banana banana");
        ld.learnLanguage("cow", "mooooooo");

        LanguageDetector.HashMap<Integer> votes = ld.apply("meoow");
        assertEquals(0, (int)votes.get("ape"));
        assertEquals(1, (int)votes.get("cow"));
        assertEquals(null, votes.get("cat"));
    }

    @Test
    public void testApply2() {
        LanguageDetector ld = new LanguageDetector(2, 1001);
        ld.learnLanguage("ape", "banana banana");
        ld.learnLanguage("cow", "mooooooo");

        LanguageDetector.HashMap<Integer> votes2 = ld.apply("moooooooooooonana");
        assertEquals(3, (int)votes2.get("ape"));
        assertEquals(12, (int)votes2.get("cow"));
    }

}


