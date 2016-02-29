package ngordnet;
import java.util.HashMap;
import java.util.Collection;
import edu.princeton.cs.introcs.In;
/**
 * @Author William Zhuang
 * Citations: http://stackoverflow.com/questions/1635764/
 *                string-parsing-in-java-with-delimeter-tab-t-using-split
 *
 */

public class NGramMap {
    HashMap<Integer, YearlyRecord> wordMap;
    HashMap<String, TimeSeries> wordCounts;
    TimeSeries<Long> totalCountMap;
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        In words = new In(wordsFilename);
        In counts = new In(countsFilename);
        wordMap = new HashMap<Integer, YearlyRecord>();
        totalCountMap = new TimeSeries<Long>();
        wordCounts = new HashMap<String, TimeSeries>();

        String line = null;
        String[] split = null;
        
        while (!words.isEmpty()) {
            line = words.readLine();
            split = line.split("\t");
            int year = Integer.parseInt(split[1]);

            if (wordMap.get(year) == null) {
                wordMap.put(year, new YearlyRecord());
            }

            if (wordCounts.get(split[0]) == null) {
                wordCounts.put(split[0], new TimeSeries<Integer>());
            }

            wordMap.get(year).put(split[0], Integer.parseInt(split[2]));
            wordCounts.get(split[0]).put(year, Integer.parseInt(split[2]));
        }

        while (!counts.isEmpty()) {
            line = counts.readLine();
            split = line.split(",");
            int year = Integer.parseInt(split[0]);
            Long total = Long.parseLong(split[1]);
            totalCountMap.put(year, total.longValue());
        }
    }

    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {
        return wordMap.get(year).count(word);
    }

    /** Returns a defensive copy of the YearlyRecord of WORD. */
    public YearlyRecord getRecord(int year) {
        return copyYearlyRecord(year);
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return totalCountMap;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> output = new TimeSeries<Integer>();
        for (Integer x : wordMap.keySet()) {
            if ((x >= startYear) && (x <= endYear)) {
                if (wordMap.get(x).count(word) != 0) {
                    output.put(x, wordMap.get(x).count(word));
                }
            }
        }
        return output;
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        TimeSeries<Integer> output = new TimeSeries<Integer>();
        for (Integer x : wordMap.keySet()) {
            if (wordMap.get(x).count(word) != 0) {
                output.put(x, wordMap.get(x).count(word));
            }
        }
        return output;
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Double> output = new TimeSeries<Double>();
        for (Integer x : wordMap.keySet()) {
            if ((x >= startYear) && (x <= endYear)) {
                if (wordMap.get(x).count(word) != 0) {
                    output.put(x, wordMap.get(x).count(word) / totalCountMap.get(x).doubleValue());
                }
            }
        }
        return output;
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Double> output = new TimeSeries<Double>();
        for (Integer x : wordMap.keySet()) {
            if (wordMap.get(x).count(word) != 0) {
                output.put(x, wordMap.get(x).count(word) / totalCountMap.get(x).doubleValue());
            }
        }
        return output;
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
        int startYear, int endYear) {
        TimeSeries<Double> output = new TimeSeries<Double>();
        for (String x : words) {
            output = output.plus(weightHistory(x, startYear, endYear));
        }
        return output;
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> output = new TimeSeries<Double>();
        for (String x : words) {
            output = output.plus(weightHistory(x));
        }
        return output;
    }

    /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
      * by YRP. */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
        YearlyRecordProcessor yrp) {
        TimeSeries<Double> output = new TimeSeries<Double>();
        for (Integer x : wordMap.keySet()) {
            if ((x >= startYear) && (x <= endYear)) {
                output.put(x, yrp.process(wordMap.get(x)));
            }
        }
        return output;
    }

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        TimeSeries<Double> output = new TimeSeries<Double>();
        for (Integer x : wordMap.keySet()) {
            output.put(x, yrp.process(wordMap.get(x)));
        }
        return output;
    }

    private YearlyRecord copyYearlyRecord(int year) {
        YearlyRecord original = wordMap.get(year);
        YearlyRecord copy = new YearlyRecord();
        Collection<String> words = original.words();
        for (String x : words) {
            copy.put(x, original.count(x));
        }
        return copy;
    }
}
