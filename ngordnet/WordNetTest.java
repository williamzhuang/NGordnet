package ngordnet;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
import java.util.Iterator;

/** WordNetTest. 
 *  @Author William Zhuang
 *  Test adapted from WordNetDemo. 
 */

public class WordNetTest {

    @Test
    public void testConstructor() {
        WordNet wn = new WordNet("./wordnet/synsets11.txt", "./wordnet/hyponyms11.txt");
        Set<String> nouns = wn.nouns();
        System.out.println(nouns);
        assertEquals(true, nouns.contains("actifed"));
        assertEquals(true, nouns.contains("action"));
        assertEquals(true, nouns.contains("antihistamine"));
        assertEquals(true, nouns.contains("augmentation"));
        assertEquals(true, nouns.contains("change"));
        assertEquals(true, nouns.contains("demotion"));
        assertEquals(true, nouns.contains("descent"));
        assertEquals(true, nouns.contains("increase"));
        assertEquals(true, nouns.contains("jump"));
        assertEquals(true, nouns.contains("leap"));
        assertEquals(true, nouns.contains("nasal_decongestant"));
        assertEquals(true, nouns.contains("parachuting"));
        assertEquals(false, nouns.contains("transition"));

    }

    @Test
    public void testIsNoun() {
        WordNet wn = new WordNet("./wordnet/synsets11.txt", "./wordnet/hyponyms11.txt");
        assertEquals(true, wn.isNoun("jump"));
        assertEquals(true, wn.isNoun("leap"));
        assertEquals(true, wn.isNoun("nasal_decongestant"));
        assertEquals(false, wn.isNoun("leonidis"));
    }

    @Test
    public void testHyponyms() {
        WordNet wn = new WordNet("./wordnet/synsets11.txt", "./wordnet/hyponyms11.txt");

        assertEquals(true, wn.hyponyms("increase").contains("leap"));
        assertEquals(true, wn.hyponyms("increase").contains("increase"));
        assertEquals(true, wn.hyponyms("increase").contains("augmentation"));
        assertEquals(true, wn.hyponyms("increase").contains("jump"));
        assertEquals(false, wn.hyponyms("increase").contains("nasal_decongestant"));

        WordNet wn2 = new WordNet("./wordnet/synsets14.txt", "./wordnet/hyponyms14.txt");
        Set<String> oranges = wn2.hyponyms("change");

        assertEquals(true, oranges.contains("alteration"));
        assertEquals(true, oranges.contains("change"));
        assertEquals(true, oranges.contains("demotion"));
        assertEquals(true, oranges.contains("increase"));
        assertEquals(true, oranges.contains("jump"));
        assertEquals(true, oranges.contains("leap"));
        assertEquals(true, oranges.contains("modification"));
        assertEquals(true, oranges.contains("saltation"));
        assertEquals(true, oranges.contains("transition"));
        assertEquals(true, oranges.contains("variation"));
    }



    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(WordNetTest.class);
    }
} 