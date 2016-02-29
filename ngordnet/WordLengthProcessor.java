package ngordnet;

/**
 * @Author William Zhuang
 *
 */
public class WordLengthProcessor implements YearlyRecordProcessor {
    public double process(YearlyRecord yearlyRecord) {
    	double sum = 0;
    	double totalCount = 0;
    	for (String x : yearlyRecord.words()) {
    		sum += x.length() * yearlyRecord.count(x);
    		totalCount += yearlyRecord.count(x);
    	}
    	return sum / totalCount;
    }
}