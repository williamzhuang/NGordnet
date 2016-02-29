package ngordnet;
import java.io.IOException;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/** Provides a simple user interface for exploring WordNet and NGram data.
 * @Author William Zhuang
 * Adapted from ExampleUI.
 */

public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                           + wordFile + ", " + countFile + ", " + synsetFile +
                           ", and " + hyponymFile + ".");

        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);

        int startDate = -99999;
        int endDate = 99999;

        while (true) {
            System.out.println("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
            switch (command) {
                case "quit":
                    return;
                case "help":
                    In in2 = new In("./ngordnet/help.txt");
                    String helpStr = in2.readAll();
                    System.out.println(helpStr);
                    break;
                case "range":
                    try {
                        startDate = Integer.parseInt(tokens[0]);
                        endDate = Integer.parseInt(tokens[1]);
                        System.out.println("Start date: " + startDate);
                        System.out.println("End date: " + endDate);
                    } catch(IndexOutOfBoundsException e) {
                        System.err.println("Please input a start and end date");
                    }
                    break;
                case "count":
                    try {
                        System.out.println(ngm.countInYear(tokens[0], Integer.parseInt(tokens[1])));
                    } catch(IndexOutOfBoundsException e) {
                        System.err.println("Please input a word and year only.");
                    }
                    break;
                case "hyponyms":
                    try {
                        System.out.println(wn.hyponyms(tokens[0]));
                    } catch(IndexOutOfBoundsException e) {
                        System.err.println("Please input only a single word.");
                    }
                    
                    break;
                case "history":
                    try {
                        String testCatch = tokens[1];
                        Plotter.plotAllWords(ngm, tokens, startDate, endDate);
                    } catch(IndexOutOfBoundsException e) {
                        System.err.println("Please input words only.");
                    }
                    
                    break;
                case "hypohist":
                    try {
                        Plotter.plotCategoryWeights(ngm, wn, tokens, startDate, endDate);
                    } catch(IndexOutOfBoundsException e) {
                        System.err.println("Please input words only.");
                    }
                    break;
                case "wordlength":
                    WordLengthProcessor wlp = new WordLengthProcessor();
                    Plotter.plotProcessedHistory(ngm, startDate, endDate, wlp);
                    break;
                case "zipf":
                    try {
                        Plotter.plotZipfsLaw(ngm, Integer.parseInt(tokens[0]));
                    } catch(IndexOutOfBoundsException e) {
                        System.err.println("Please a single year");
                    }
                    break;
                default: 
                    System.out.println("Invalid command.");
                    break;
            }
        }
        
    }
}
 