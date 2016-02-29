package ngordnet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Map;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @Author William Zhuang
 * Citations: http://stackoverflow.com/questions/109383/
 *              how-to-sort-a-mapkey-value-on-the-values-in-java.
 *           http://stackoverflow.com/questions/8502542/
 *              find-element-position-in-a-java-treemap
 *           http://stackoverflow.com/questions/4395871/
 *              how-to-get-index-of-an-item-in-java-util-set
 *           http://stackoverflow.com/questions/9008532/
 *              how-to-find-index-position-of-an-element-in-a-list-when-contains-returns-true
 *           http://stackoverflow.com/questions/20758080/
 *              how-to-convert-collectionnumber-to-collectioninteger-safety
 */

public class YearlyRecord {
    private int wordCount;
    private ValComparator comparator;
    private HashMap<String, Integer> unsorted;
    private TreeMap<String, Integer> sorted; 
    private Collection<Number> countSet;
    private HashMap<String, Integer> rankMap;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        countSet = new TreeSet<Number>();
        unsorted = new HashMap<String, Integer>();
        comparator = new ValComparator(unsorted);
        sorted = new TreeMap<String, Integer>(comparator); 
        rankMap = new HashMap<String, Integer>(sorted);
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        unsorted = otherCountMap;
        comparator = new ValComparator(unsorted);
        sorted = new TreeMap<String, Integer>(comparator);
        sorted.putAll(otherCountMap);
        countSet = new TreeSet<Number>();
        rankMap = new HashMap<String, Integer>(sorted);

        int rank = 1;
        for (String x : sorted.keySet()) {
            countSet.add(unsorted.get(x));
            rankMap.put(x, rank);
            rank += 1;
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        if (unsorted.get(word) != null) {
            return unsorted.get(word);
        } else {
            return 0;
        }
        
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        
        int rank = 1;
        Integer origRank = rankMap.get(word);
        Integer origCount = null;
        
        if (origRank != null) {
            origCount = unsorted.get(word);
            unsorted.remove(word);
            sorted = new TreeMap<String, Integer>(new ValComparator(unsorted));
            sorted.putAll(unsorted);
            countSet.remove(origCount);
        }

        unsorted.put(word, count);
        sorted.put(word, count);
        countSet.add(unsorted.get(word));

        for (String x : sorted.keySet()) {
            if (unsorted.get(x) < count) {
                rankMap.put(x, rankMap.get(x) + 1);
            } else {
                rankMap.put(word, rank);
            }
            rank += 1;

            if ((origRank != null) && (rankMap.get(x) > origRank) && (!x.equals(word))) {
                rankMap.put(x, rankMap.get(x) - 1);
            }
        }
    } 

    /** Returns the number of words recorded this year. */
    public int size() {
        return sorted.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        return sorted.descendingKeySet();
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() { 
        return countSet;
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        return rankMap.get(word);
    }


    private class ValComparator implements Comparator<String> {
        Map<String, Integer> inMap;
        public ValComparator(Map<String, Integer> inMap0) {
            inMap = inMap0;
        }

        public int compare(String a, String b) {
            if ((int) inMap.get(a) >= (int) inMap.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
} 
