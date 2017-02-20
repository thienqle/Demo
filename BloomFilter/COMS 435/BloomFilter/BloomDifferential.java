/*
* Thien Le
*/
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class BloomDifferential{

    private String sDiffFile;
    private String sDatabase;
    private BloomFilterRan aBloomFilterRan;
    //private BloomFilterDet aBloomFilterDet;

    public void setDataFile(String filename1, String filename2){
	this.sDiffFile = filename1;
	this.sDatabase = filename2;
    }

    public BloomDifferential(String DiffFile, String Database,int numberOfRecords,int bitsPerElement){
	this.sDiffFile = DiffFile;
	this.sDatabase = Database;
	this.aBloomFilterRan = new BloomFilterRan(numberOfRecords,bitsPerElement);
    }

    public BloomFilterRan getBloomFilter(){
	return this.aBloomFilterRan;
    }
    
    /*that returns a Bloom Filter corresponding to the records in the differential file DiffFile.txt*/
    public BloomFilterRan createFilter(){
        String sContent = "";
        try{
        FileInputStream fstream = new FileInputStream(sDiffFile);
        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Read File Line By Line
        while((strLine = br.readLine()) != null){
	    String[] word = strLine.split(" ");
	    sContent = "";
	    sContent = word[0] + " " + word[1]+ " " + word[2] + " " + word[3];
	    //System.out.println(sContent);
	    aBloomFilterRan.add(sContent);
        }
        //Close the input stream
        in.close();
        }catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }

	return this.aBloomFilterRan;
    }

    /*that gets a key as parameter and returns the record corresponding to the record by consulting the Bloom Filter rst.*/
     public String retrieveRecord(String key){
	 if(key == null || key.isEmpty()){
	     return "\nNo record found\n";
	 } else {
	     String value = "";
	     if(aBloomFilterRan.appears(key)){
		 try{
		     FileInputStream fstream = new FileInputStream(sDiffFile);
		     // Get the object of DataInputStream
		     DataInputStream in = new DataInputStream(fstream);
		     BufferedReader br = new BufferedReader(new InputStreamReader(in));
		     boolean run=true;
		     while((value = br.readLine()) != null && run){
			 if(value.toLowerCase().contains(key.toLowerCase())){
			     run = false;
			     return value;
			 }
		     }
		     //Close the input stream
		     in.close();
		 }catch (Exception e){//Catch exception if any
		     System.err.println("Error: " + e.getMessage());
		 }
		 
		 //If record cannot be find in Diffile, access to database.txt
		 try{
		     FileInputStream fstream = new FileInputStream(sDatabase);
		     // Get the object of DataInputStream
		     DataInputStream in = new DataInputStream(fstream);
		     BufferedReader br = new BufferedReader(new InputStreamReader(in));
		     boolean run=true;
		     while((value = br.readLine()) != null && run){
			 if(value.toLowerCase().contains(key.toLowerCase())){
			     run = false;
			     return value;
			 }
		     }
		     //Close the input stream
		     in.close();
		 }catch (Exception e){//Catch exception if any
		     System.err.println("Error: " + e.getMessage());
		 }

	     } else {
		 //If record cannot be find in Diffile, access to database.txt
		 try{
		     FileInputStream fstream = new FileInputStream(sDatabase);
		     // Get the object of DataInputStream
		     DataInputStream in = new DataInputStream(fstream);
		     BufferedReader br = new BufferedReader(new InputStreamReader(in));
		     boolean run=true;
		     while((value = br.readLine()) != null && run){
			 if(value.toLowerCase().contains(key.toLowerCase())){
			     run = false;
			     return value;
			 }
		     }
		     //Close the input stream
		     in.close();
		 }catch (Exception e){//Catch exception if any
		     System.err.println("Error: " + e.getMessage());
		 }

	     }
	 }
	 return "\nNo record found\n";
	 
    }
    
}
