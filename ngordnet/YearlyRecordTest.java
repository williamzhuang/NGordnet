package ngordnet;
import org.junit.Test;
import java.util.HashMap;
import java.util.Collection;

import static org.junit.Assert.*;

/** YearlyRecordTest. 
 *  @Author William Zhuang
 *  Tests adapted from YearlyRecordDemo.
 */

public class YearlyRecordTest {

    @Test
    public void testHashConstructor() {
        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        hash.put("quayside", 95);
        hash.put("surrogate", 340);
        hash.put("merchantman", 181);

        YearlyRecord yr = new YearlyRecord(hash);
        assertEquals(95, yr.count("quayside"));
        assertEquals(340, yr.count("surrogate"));
        assertEquals(181, yr.count("merchantman"));

        assertEquals(3, yr.rank("quayside"));
    }

    @Test
    public void testCount() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("quayside", 95);
        yr.put("surrogate", 340);
        yr.put("merchantman", 181);

        assertEquals(95, yr.count("quayside"));
        assertEquals(340, yr.count("surrogate"));
        assertEquals(181, yr.count("merchantman"));
    }

    @Test
    public void testCounts() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("quayside", 95);
        yr.put("surrogate", 340);
        yr.put("merchantman", 181);
        
        Collection<Number> counts = yr.counts();
        assertEquals(true, counts.contains(95));
        assertEquals(true, counts.contains(340));
        assertEquals(true, counts.contains(181));
    }

    @Test
    public void testSize() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("quayside", 95);
        yr.put("surrogate", 340);
        yr.put("merchantman", 181);
        
        assertEquals(3, yr.size());
    }

    @Test
    public void testRank() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("quayside", 95);
        yr.put("surrogate", 340);
        yr.put("merchantman", 181);
        yr.put("oranges", 181);

        assertEquals(1, yr.rank("surrogate"));
        assertEquals(4, yr.rank("quayside"));

        yr.put("limon", 200);
        yr.put("diamond", 400);
        yr.put("fail", 1);

        assertEquals(1, yr.rank("diamond"));
        assertEquals(2, yr.rank("surrogate"));
        assertEquals(3, yr.rank("limon"));
        assertEquals(4, yr.rank("merchantman"));
        assertEquals(5, yr.rank("oranges"));
        assertEquals(6, yr.rank("quayside"));
        assertEquals(7, yr.rank("fail"));

        yr.put("fail", 99);
        yr.put("diamond", 40);

        assertEquals(1, yr.rank("surrogate"));
        assertEquals(2, yr.rank("limon"));
        assertEquals(3, yr.rank("merchantman"));
        assertEquals(4, yr.rank("oranges"));
        assertEquals(5, yr.rank("fail"));
        assertEquals(6, yr.rank("quayside"));
        assertEquals(7, yr.rank("diamond"));
    }
    


    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(YearlyRecordTest.class);
    }
} 