package ngordnet;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
/**
 * @Author William Zhuang
 * Citations: http://stackoverflow.com/questions/10960213/how-to-read-comma-
 *              separated-values-from-text-file-in-java
 *            http://stackoverflow.com/questions/2275004/in-java-how-to-check-if-a-string-
 *              contains-a-substring-ignoring-the-case
 *            http://stackoverflow.com/questions/1318980/how-to-iterate-over-a-treemap
 */
    
public class WordNet {
    
    private TreeMap<Integer, String> synsets;
    private HashMap<String, List<Integer>> revSyn;
    private Digraph digraph;
    private int size;

    /** Creates a WordNet using files form SYNSETFILENAME and HYPONYMFILENAME */
    public WordNet(String synsetFilename, String hyponymFilename) {
        synsets = new TreeMap<Integer, String>();
        In in = new In(synsetFilename);
        In hypoIn = new In(hyponymFilename);

        int num = 0;
        String line = null;
        String[] split = null;
        size = 0;

        /** Reads in and parses synsets acoordingly. */
        while (!in.isEmpty()) {
            line = in.readLine();
            split = line.split(",");
            synsets.put(Integer.parseInt(split[0]), split[1]);
            size += 1;
        }

        revSyn = invert(synsets);

        /** Generates the digraph with appropriate edges. */
        digraph = new Digraph(size);

        while (!hypoIn.isEmpty()) {
            line = hypoIn.readLine();
            split = line.split(",");
            for (int i = 1; i < split.length; i += 1) {
                digraph.addEdge(Integer.parseInt(split[0]), 
                                Integer.parseInt(split[i]));        
            }
        }
    }

    /** Returns true if NOUN is a word in some synset. */
    public boolean isNoun(String noun) {
        if (revSyn.get(noun) != null) { 
            return true;
        } 
        return false;
    }

    /** Returns the set of all nouns. */
    public Set<String> nouns() {
        Set<String> allNouns = new TreeSet<String>();
        String line = null;
        String[] split = null;
        for (int i = 0; i < size; i += 1) {
            line = synsets.get(i);
            split = line.split(" ");
            for (int j = 0; j < split.length; j += 1) {
                allNouns.add(split[j]);
            }
        }
        return allNouns;
    }

    /** Returns the set of all hyponyms of WORD including WORD itself. */
    public Set<String> hyponyms(String word) {
        List<Integer> numbers = revSyn.get(word);
        Set<Integer> synsetID = new TreeSet<Integer>();

        for (int i = 0; i < numbers.size(); i += 1) {
            synsetID.add(numbers.get(i));
        }

        Set<Integer> outIDs = GraphHelper.descendants(digraph, synsetID);
        
        Set<String> hyponyms = new TreeSet<String>();

        hyponyms.add(word);
        
        String line = null;
        String[] split = null;

        for (int x : outIDs) {
            line = synsets.get(x);
            split = line.split(" ");
            for (int i = 0; i < split.length; i += 1) {
                hyponyms.add(split[i]);
            }
        }

        return hyponyms;
    }

    private HashMap<String, List<Integer>> invert(TreeMap<Integer, String> map) {
        HashMap<String, List<Integer>>  output = new HashMap();
        String line = null;
        String[] split = null;
        List<Integer> holder;

        for (Map.Entry<Integer, String> x : map.entrySet()) {
            line = x.getValue();
            split = line.split(" ");
            for (int i = 0; i < split.length; i += 1) {
                if (output.get(split[i]) == null) {
                    holder = new LinkedList();
                    holder.add(x.getKey());
                    output.put(split[i], holder);
                } else {
                    holder = output.get(split[i]);
                    holder.add(x.getKey());
                    output.put(split[i], holder);
                }

            }
            
        }

        return output;
    }
}
