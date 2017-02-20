/*
* Thien Le
*/
import java.util.Scanner;

public class EmpericalComparison{

    public static void main(String[] args) {

	String sDiffFile = "E:\\ISU study\\STUDY\\Fall2016\\COMS 435\\Homeworks\\DiffFile.txt";
	String sDatabase = "E:\\ISU study\\STUDY\\Fall2016\\COMS 435\\Homeworks\\database.txt";
	int numOfRecords = 1262146;
	int bitsPerElement = 4;
	String key = "A.R.P. ( Air Raid";
      
	BloomDifferential aBloomDifferential = new BloomDifferential(sDiffFile,sDatabase,numOfRecords,bitsPerElement);
	aBloomDifferential.createFilter();
	//System.out.println("Running time of Bloom Filter on DiffFile:\n" + (RunningTimeBloomFilter(aBloomDifferential,key))/1000.0 + " seconds");
	//System.out.println("Running time of Naive Search:\n" + (RunningTimeNaive(sDiffFile,sDatabase,key))/1000.0 + " seconds");

	    
	String c = "n";
	while(!c.equals("y")){
	    Scanner aScanner =  new Scanner(System.in);
	    System.out.println("Enter a valid key (Please do not add space to the end of the key):");
	    key = aScanner.nextLine();
    	    System.out.println("Results of Bloom Filter on DiffFile searching:");
	    System.out.println("Running time of Bloom Filter on DiffFile:\n" + (RunningTimeBloomFilter(aBloomDifferential,key))/1000.0 + " seconds");
    	    System.out.println("Results of Naive searching:");
	    System.out.println("Running time of Naive Search:\n" + (RunningTimeNaive(sDiffFile,sDatabase,key))/1000.0 + " seconds");
	    System.out.println("Do you want to exit (y/n)?:");
	    c = aScanner.nextLine();
	 }
	    
	    
    }

    public static long RunningTimeNaive(String sDiffFile,String sDatabase,String key){
	long startTime = System.currentTimeMillis();
	NaiveDifferential aNaiveDifferential = new NaiveDifferential(sDiffFile,sDatabase);
	System.out.println(aNaiveDifferential.retrieveRecord(key));
	long stopTime = System.currentTimeMillis();
	return stopTime - startTime;
    }

    public static long RunningTimeBloomFilter(BloomDifferential aBloomDifferential,String key){
	long startTime = System.currentTimeMillis();
	System.out.println(aBloomDifferential.retrieveRecord(key));
	long stopTime = System.currentTimeMillis();
	return stopTime - startTime;
    }
    
}
