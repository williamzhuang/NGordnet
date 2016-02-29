package ngordnet;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
import java.util.Iterator;

/** TimeSeriesTest. 
 *  @author William Zhuang
 */

public class TimeSeriesTest {

    @Test
    public void testBasicConstructor() {
        TimeSeries<Double> ts1 = new TimeSeries<Double>();
        ts1.put(1992, 10.0);
        ts1.put(1993, 4.2);
        ts1.put(2000, 2.2);
        ts1.put(1989, 1.6);

        assertEquals(10.0, ts1.get(1992), 0.00001);
        assertEquals(1.6, ts1.get(1989), 0.00001);
    }

    @Test
    public void testCopyConstructor() {
        TimeSeries<Double> ts1 = new TimeSeries<Double>();
        ts1.put(1992, 3.6);
        ts1.put(1993, 4.2);
        ts1.put(2000, 2.2);
        ts1.put(1989, 1.6);

        TimeSeries<Double> ts2 = new TimeSeries<Double>(ts1);
        assertEquals(3.6, ts2.get(1992), 0.00001);
        assertEquals(1.6, ts2.get(1989), 0.00001);
    }

    @Test
    public void testLimitedCopyConstructor() {
        TimeSeries<Double> ts1 = new TimeSeries<Double>();
        ts1.put(1992, 3.6);
        ts1.put(1993, 4.2);
        ts1.put(2000, 2.2);
        ts1.put(1989, 1.6);

        TimeSeries<Double> ts2 = new TimeSeries<Double>(ts1, 1990, 2000);
        assertEquals(3.6, ts2.get(1992), 0.00001);
        assertEquals(null, ts2.get(1989));
        assertEquals(2.2, ts2.get(2000), 0.00001);
    }

    @Test
    public void testDividedBy() {
        TimeSeries<Integer> ts1 = new TimeSeries<Integer>();
        ts1.put(1992, 2);
        ts1.put(1993, 4);
        ts1.put(2000, 24);
        ts1.put(1989, 48);

        TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1992, 1);
        ts2.put(1993, 2);
        ts2.put(2000, 3);
        ts2.put(1989, 4);

        TimeSeries<Double> ts3 = ts1.dividedBy(ts2);
        assertEquals(2, ts3.get(1992), 0.0001);
        assertEquals(12, ts3.get(1989), 0.0001);
    }

    @Test
    public void testPlus() {
        TimeSeries<Double> ts1 = new TimeSeries<Double>();
        ts1.put(1992, 3.6);
        ts1.put(1993, 4.2);
        ts1.put(2000, 2.2);
        ts1.put(1989, 1.6);

        TimeSeries<Double> ts2 = new TimeSeries<Double>();
        ts2.put(1992, 1.8);
        ts2.put(1993, 2.1);
        ts2.put(2000, 1.1);
        ts2.put(1989, 1.6);
        ts2.put(2001, 2.2);

        TimeSeries<Double> ts3 = ts1.plus(ts2);
        assertEquals(5.4, ts3.get(1992), 0.0001);
        assertEquals(3.2, ts3.get(1989), 0.0001);
        assertEquals(2.2, ts3.get(2001), 0.0001);

        // Testing addition to empty TimeSeries.
        TimeSeries<Double> ts4 = new TimeSeries<Double>();

        TimeSeries<Double> ts5 = new TimeSeries<Double>();
        ts4.put(1992, 3.6);
        ts4.put(1993, 2.4);

        TimeSeries<Double> ts6 = ts4.plus(ts5);
        TimeSeries<Double> ts7 = ts5.plus(ts4);
        assertEquals(3.6, ts6.get(1992), 0.00001);
        assertEquals(3.6, ts7.get(1992), 0.00001);

    }

    @Test
    public void testYears() {
        TimeSeries<Double> ts1 = new TimeSeries<Double>();
        ts1.put(1992, 3.6);
        ts1.put(1993, 4.2);
        ts1.put(2000, 2.2);
        ts1.put(1989, 1.6);

        Collection<Number> years = ts1.years();
        assertEquals(true, years.contains(1992));
        assertEquals(true, years.contains(2000));
        assertEquals(false, years.contains(1999));
    }

    @Test
    public void testData() {
        TimeSeries<Double> ts1 = new TimeSeries<Double>();
        ts1.put(1992, 3.6);
        ts1.put(1993, 4.2);
        ts1.put(2000, 2.2);
        ts1.put(1989, 1.6);

        Collection<Number> data = ts1.data();
        assertEquals(true, data.contains(3.6));
        assertEquals(true, data.contains(4.2));
        assertEquals(false, data.contains(1.8));
    }



    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TimeSeriesTest.class);
    }
} 