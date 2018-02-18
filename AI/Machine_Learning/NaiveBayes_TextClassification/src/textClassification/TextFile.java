package textClassification;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Model of a text file that for use to load content of file
 */
public class TextFile {
	
	/**
	 * Content of file
	 */
	private String content;
	
	/**
	 * Content of file
	 */
	private String filePath;
	
	/**
	 * Construct an file with given file path
	 * @param filePath
	 * 	the path of input file
	 */
	public TextFile(String filePath) {
		this.filePath = filePath;
		loadFile();
	}
	
	/**
	 * Load file content
	 */
	public void loadFile() {
		StringBuffer output = new StringBuffer();
		try{
	        FileInputStream fstream = new FileInputStream(this.filePath);
	        DataInputStream in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        String line;
	        while((line = br.readLine()) != null){
	        	output.append(line + "\n");
	        }
	        in.close();
        } catch (Exception e){//Catch exception if any
          System.err.println("Error: " + e.getMessage());
        }
		content = output.toString();
	}
	
	/**
	 * Function returns the content of file that has been loaded.
	 * @return 
	 * 	content of file as string
	 */
	public String getFileContent() {
		return this.content;
	}

}
