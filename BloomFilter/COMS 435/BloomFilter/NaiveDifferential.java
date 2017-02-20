import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class NaiveDifferential{

    private String sDiffFile;
    private String sDatabase;

    public void setDataFile(String filename1, String filename2){
	this.sDiffFile = filename1;
	this.sDatabase = filename2;
    }

    public NaiveDifferential(String filename1, String filename2){
	this.sDiffFile = filename1;
	this.sDatabase = filename2;
    }

    
    public NaiveDifferential(){
	this.sDiffFile = "E:\\ISU study\\STUDY\\Fall2016\\COMS 435\\Homeworks\\DiffFile.txt";
	this.sDatabase = "E:\\ISU study\\STUDY\\Fall2016\\COMS 435\\Homeworks\\database.txt";
    }
    
    
    /*that gets a key as parameter and returns the record corresponding to the record. It does not use Bloom Filter. */
     public String retrieveRecord(String key){
	 if(key != null && !key.isEmpty()){
	 try{
	    FileInputStream fstream = new FileInputStream(sDiffFile);
	    // Get the object of DataInputStream
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String value;
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
	    String value;
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
	return "\nNo record found\n";

    }

}
