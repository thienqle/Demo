/*
* Thien Le
*/
import java.util.BitSet;
import java.lang.Math;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class FalsePositives{

    public static void main(String []args){

	int numberOfRecords = 1262147/2;
	int numberOfTestRecords = 1262147/2;
	int numberOfTotalRecords = 1262147;
	String sTestFile = "E:\\ISU study\\STUDY\\Fall2016\\COMS 435\\Homeworks\\DiffFile.txt";
	int numOfFalse = 0;

	System.out.println("================================");
	System.out.println("== BLOOM FILTER RANDOM    ======");
	System.out.println("================================");
	ExperimentOnBloomFilterRan(sTestFile,4,numberOfRecords,numberOfTestRecords,numberOfTotalRecords,numberOfRecords+1,1262147,0,1262147/2);
	System.out.println("================================");
	ExperimentOnBloomFilterRan(sTestFile,8,numberOfRecords,numberOfTestRecords,numberOfTotalRecords,numberOfRecords+1,1262147,0,1262147/2);
	System.out.println("================================");
	ExperimentOnBloomFilterRan(sTestFile,10,numberOfRecords,numberOfTestRecords,numberOfTotalRecords,numberOfRecords+1,1262147,0,1262147/2);
	System.out.println("================================");	
    }

    public static double falsePositiveTheory(int bitsPerElement){
	return Math.pow(0.618,bitsPerElement);
    }

    public static void createTestFile(String sFilename,String sDiffFile,int numberOfRecords){
	String sData = readFileToLine(sDiffFile,numberOfRecords);
	WriteToFile(sFilename,sData); 
    }

    public static void WriteToFile(String sFilename,String data) {
        try{
	    // Create file
	    FileWriter fstream = new FileWriter(sFilename);
	    BufferedWriter out = new BufferedWriter(fstream);
	    out.write(data);
	    //Close the output stream
	    out.close();
	}catch (Exception e){//Catch exception if any
	    System.err.println("Error: " + e.getMessage());
	}
    }

    public static String readFileToLine(String sFilename,int line){
        String sContent = "";
        try{
        FileInputStream fstream = new FileInputStream(sFilename);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        for(int i=0;i<=line;i++){
            if((strLine = br.readLine()) != null){
                sContent = sContent + strLine;
                sContent = sContent + "\n";
            }
        }
        in.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }
        return sContent;
        /*
            //Java 8
            try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                stream.forEach(System.out::println);
            }
        */   
    }

    public static String loadDataBloomFilterRan(BloomFilterRan aBloomFilterRan,String sFilename,int from, int to){
        String sContent = "";
        try{
        FileInputStream fstream = new FileInputStream(sFilename);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
	int i = 0;
        while((strLine = br.readLine()) != null){
            if(i>=from && i<=to){
		String[] word = strLine.split(" ");
		sContent = "";
		sContent = word[0] + " " + word[1]+ " " + word[2] + " " + word[3];
		aBloomFilterRan.add(sContent);
	    }
	    i++;
        }
        //Close the input stream
        in.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }
        return sContent;
    }

     public static int CountFalsePositiveRan(BloomFilterRan aBloomFilterRan,String sFilename,int from, int to){
	int FalsePositive = 0;
        try{
        FileInputStream fstream = new FileInputStream(sFilename);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
	int i =0;
        while((strLine = br.readLine()) != null){
            if(i>=from && i<=to){
		String[] word = strLine.split(" ");
		String sContent = "";
		sContent = word[0] + " " + word[1]+ " " + word[2] + " " + word[3];
		if(aBloomFilterRan.appears(sContent)){
		    FalsePositive++;
		}
	    }
	    i++;
        }
        //Close the input stream
        in.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }
        return FalsePositive;
    }



    public static String loadDataBloomFilterDet(BloomFilterDet aBloomFilterDet,String sFilename,int from, int to){
        String sContent = "";
        try{
        FileInputStream fstream = new FileInputStream(sFilename);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
	int i = 0;
        while((strLine = br.readLine()) != null){
            if(i>=from && i<=to){
		String[] word = strLine.split(" ");
		sContent = "";
		sContent = word[0] + " " + word[1]+ " " + word[2] + " " + word[3];
		aBloomFilterDet.add(sContent);
	    }
	    i++;
        }
        //Close the input stream
        in.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }
        return sContent;
    }

     public static int CountFalsePositiveDet(BloomFilterDet aBloomFilterDet,String sFilename,int from, int to){
	int FalsePositive = 0;
        try{
        FileInputStream fstream = new FileInputStream(sFilename);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
	int i =0;
        while((strLine = br.readLine()) != null){
            if(i>=from && i<=to){
		String[] word = strLine.split(" ");
		String sContent = "";
		sContent = word[0] + " " + word[1]+ " " + word[2] + " " + word[3];
		if(aBloomFilterDet.appears(sContent)){
		    FalsePositive++;
		}
	    }
	    i++;
        }
        //Close the input stream
        in.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }
        return FalsePositive;
    }


    public static void ExperimentOnBloomFilterRan(String sTestFile,int bitsPerElement,int numberOfRecords,int numberOfTestRecords,int numberOfTotalRecords,int LoadFrom,int LoadTo,int TestFrom, int TestTo){
	int numOfFalse = 0;

	/*Print out the Fasle Positive on Theroy */
	System.out.println("False Positive on Theory of " + bitsPerElement + " bits per element: " +falsePositiveTheory(bitsPerElement));

	/*Load into Bloom Filter with given number of records*/
	BloomFilterRan aBloomFilterRan = new BloomFilterRan(numberOfRecords,bitsPerElement);
	loadDataBloomFilterRan(aBloomFilterRan,sTestFile,LoadFrom,LoadTo);
	
	/*Check appear of key from 0 to 1000 */
	numOfFalse = CountFalsePositiveRan(aBloomFilterRan,sTestFile,TestFrom,TestTo);
	/*Print out the Real False Positive */
	System.out.print("False Positive Application with " + bitsPerElement + " bits per element: ");
	System.out.printf("%.4f",(float)numOfFalse/numberOfTestRecords);
	System.out.printf("\n");
	System.out.println("Number of records false is " + numOfFalse + " over " + numberOfTestRecords);
	System.out.println("Number of hashes:" + aBloomFilterRan.numHashes());
	System.out.println("Bloom Filter size:" + aBloomFilterRan.filterSize());
	System.out.println("Set size:" + aBloomFilterRan.getSetSize());
	System.out.println("Data loaded:" + aBloomFilterRan.dataSize() + "\n");
	//System.out.println(aBloomFilterRan.getBloomFilter());
    }

        public static void ExperimentOnBloomFilterDet(String sTestFile,int bitsPerElement,int numberOfRecords,int numberOfTestRecords,int numberOfTotalRecords,int LoadFrom,int LoadTo,int TestFrom, int TestTo){
	int numOfFalse = 0;

	/*Print out the Fasle Positive on Theroy */
	System.out.println("False Positive on Theory of " + bitsPerElement + " bits per element: " +falsePositiveTheory(bitsPerElement));

	/*Load into Bloom Filter with given number of records*/
	BloomFilterDet aBloomFilterDet = new BloomFilterDet(numberOfRecords,bitsPerElement);
	loadDataBloomFilterDet(aBloomFilterDet,sTestFile,LoadFrom,LoadTo);
	
	/*Check appear of key from 0 to 1000 */
	numOfFalse = CountFalsePositiveDet(aBloomFilterDet,sTestFile,TestFrom,TestTo);
	/*Print out the Real False Positive */
	System.out.print("False Positive Application with " + bitsPerElement + " bits per element: ");
	System.out.printf("%.4f",(float)numOfFalse/numberOfTestRecords);
	System.out.printf("\n");
	System.out.println("Number of records false is " + numOfFalse + " over " + numberOfTestRecords);
	System.out.println("Number of hashes:" + aBloomFilterDet.numHashes());
	System.out.println("Bloom Filter size:" + aBloomFilterDet.filterSize());
	System.out.println("Set size:" + aBloomFilterDet.getSetSize());
	System.out.println("Data loaded:" + aBloomFilterDet.dataSize() + "\n");
	//System.out.println(aBloomFilterRan.getBloomFilter());
    }
    
    
       
}
