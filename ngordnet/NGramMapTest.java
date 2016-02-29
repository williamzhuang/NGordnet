package ngordnet;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/** NGramMapTest. Tests adapted from NGramDemo.
 *  @author William Zhuang
 */

public class NGramMapTest {

    @Test
    public void testConstructor() {
        NGramMap ngm = new NGramMap("./ngrams/words_that_start_with_q.csv", 
            "./ngrams/total_counts.csv");

        assertEquals(139, ngm.countInYear("quantity", 1736));
        assertEquals(37, ngm.countInYear("questioning", 1797));
        assertEquals(9, ngm.countInYear("qualifications", 1679));
    }

    @Test
    public void testGetRecord() {
        NGramMap ngm = new NGramMap("./ngrams/words_that_start_with_q.csv", 
            "./ngrams/total_counts.csv");
        
        YearlyRecord yr = ngm.getRecord(1736);
        YearlyRecord yr2 = ngm.getRecord(1797);
        YearlyRecord yr3 = ngm.getRecord(1679);
        
        assertEquals(139, yr.count("quantity"));
        assertEquals(37, yr2.count("questioning"));
        assertEquals(9, yr3.count("qualifications"));

        YearlyRecord yr4 = ngm.getRecord(1995);
        yr4.put("questioning", 31337);
        YearlyRecord yr5 = ngm.getRecord(1995);
        assertEquals(90316, yr5.count("questioning"));

    }

    @Test
    public void testTotalCountHistory() {
        NGramMap ngm = new NGramMap("./ngrams/words_that_start_with_q.csv", 
            "./ngrams/total_counts.csv");
        TimeSeries<Long> totalCountHistory = ngm.totalCountHistory();
        assertEquals(8049773, totalCountHistory.get(1736), 0.00001);
        assertEquals(8036677, totalCountHistory.get(1741), 0.00001);
        
    }

    @Test
    public void testCountHistory() {
        NGramMap ngm = new NGramMap("./ngrams/words_that_start_with_q.csv", 
            "./ngrams/total_counts.csv");
        TimeSeries<Integer> countHistory = ngm.countHistory("quantity");
        TimeSeries<Integer> countHistory2 = ngm.countHistory("questioning");
        TimeSeries<Integer> countHistory3 = ngm.countHistory("qualifications");

        assertEquals(139, countHistory.get(1736), 0.0000001);
        assertEquals(37, countHistory2.get(1797), 0.0000001);
        assertEquals(9, countHistory3.get(1679), 0.000001); 
    }

    @Test
    public void testWeightHistory() {
        NGramMap ngm = new NGramMap("./ngrams/words_that_start_with_q.csv", 
            "./ngrams/total_counts.csv");
        TimeSeries<Double> weightHistory = ngm.weightHistory("quantity");

        assertEquals(1.7267E-5, weightHistory.get(1736), 0.000001);
    }

    @Test
    public void testSummedWeightHistory() {
        NGramMap ngm = new NGramMap("./ngrams/words_that_start_with_q.csv", 
            "./ngrams/total_counts.csv");
        ArrayList<String> words = new ArrayList<String>();
        words.add("quantity");
        words.add("quality");
        TimeSeries<Double> sum = ngm.summedWeightHistory(words);
        assertEquals(3.875E-5, sum.get(1736), 0.000001);
    }


    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(NGramMapTest.class);
    }
} 