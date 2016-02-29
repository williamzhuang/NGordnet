package ngordnet;
import java.util.TreeMap;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.Collection;

/**
 * @Author William Zhuang
 * Time Series contains a series of years and a data point for each year. 
 *
 */

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {    
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
    }

    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. */
    private NavigableSet<Integer> validYears(int startYear, int endYear) {
        NavigableSet<Integer> output = new TreeSet<Integer>();
        for (Integer x : keySet()) {
            if ((x >= startYear) && (x <= endYear)) {
                output.add(x);
            }
        }
        return output;
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        for (Integer x : ts.keySet()) {
            if ((x >= startYear) && (x <= endYear)) {
                put(x, ts.get(x));
            }
        }
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        for (Integer x: ts.keySet()) {
            put(x, ts.get(x));
        }
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> output = new TimeSeries();
        for (Integer x : ts.keySet()) {
            output.put(x, ts.get(x).doubleValue());
        }

        for (Integer x : keySet()) {
            if (ts.get(x) != null) {
                output.put(x, get(x).doubleValue() / ts.get(x).doubleValue());
            } else {
                throw new IllegalArgumentException("ts does not contain year " + x);
            }
        }
        return output;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> output = new TimeSeries();

        for (Integer x : ts.keySet()) {
            output.put(x, ts.get(x).doubleValue());
        }

        for (Integer x : keySet()) {
            if (ts.get(x) != null) {
                output.put(x, get(x).doubleValue() + ts.get(x).doubleValue());
            } else {
                output.put(x, get(x).doubleValue());
            }
        }
        
        return output;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        NavigableSet<Number> output = new TreeSet<Number>();
        output.addAll(keySet());
        return output;
    }

    /** Returns all data for this time series (in any order). */
    public Collection<Number> data() {
        return (Collection<Number>) values();
    }
}
