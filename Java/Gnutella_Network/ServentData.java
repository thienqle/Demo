/**
 * @author Thien
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Model of Servent Data 
 */
public class ServentData {

	/**
	 * Fields of Servent Data 
	 */
	public ArrayList<ServentInfo> serventList;
	public ArrayList<String> filenames;
	public HashMap<String,String> files;
	public HashMap<ServentInfo,Long> lastPong;
	public String IPAddress;
	public int localPort = 0;
	public int knowNeighbor = 0;
	
	/**
	 * Constructs servent Data 
	 */
	public ServentData(){
		serventList = new ArrayList<ServentInfo>();
		filenames = new ArrayList<String>();
		files = new HashMap<String,String>();
		lastPong = new HashMap<ServentInfo,Long>();
		/*Random files and their content*/
		Random random = new Random();
		ArrayList<Integer> tmp = new ArrayList<Integer>(); 
		int numOfFile = (int) (random.nextInt(5 - 1 + 1) + 1);
		for(int i = 0;i<numOfFile;i++){
			tmp.add(i);
			int filename = (int) (random.nextInt(10 - 1 + 1) + 1);
			if(!tmp.contains(filename)){
				tmp.add(filename);
				filenames.add(new String("file" + filename+ ".txt"));
				files.put(new String("file" + filename+ ".txt"), randomGenertateContent());
			} 
		}
	}
	
	/**
	 * method that check last pong list contains given servent Information 
	 * @param input of servent Info
	 * @return boolean
	 * 	true if last pong list contain servent Info
	 */
	public boolean lastPongContains(ServentInfo input){
		for(ServentInfo s : lastPong.keySet()){
			if(s.IP.equals(input.IP) && s.port == input.port){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * method that generates a random content of a file 
	 * @return random string 
	 */
	public String randomGenertateContent(){
		String []temp = {"This","Is","Random","Randomize","Content","Random","Randomize","Random","Randomly","Random Text"};
		Random random = new Random();
		int numOfWord = (int) (random.nextInt(20 - 1 + 1) + 1);
		String output = "";
		for(int i=0;i<numOfWord;i++){
			int index = (int) (random.nextInt(temp.length - 2 + 1));
			output+= temp[index] + " ";
		}
		return output;
	}

	/**
	 * method that gets file content of given filename
	 * @param filename as a String 
	 * @return filecontent as a String
	 */
	public String getFileContent(String filename){
		String output = "";
		for(String s : files.keySet()){
			if(s.equals(filename.trim())){
				output = files.get(s);
				return output;
			}
		}
		return "File does not exist";
	}
	
	/**
	* Method that returns number of Neighbor
	* @return number of neighbor as Integer
	*/		
	public int getNeighBorCount(){
		return serventList.size(); 
	}
	
	/**
	* Method that checks if a node is in Servent List
	* @return boolean
	* 	true if that Servent Information is stored in servent list
	* 	false if that Servent Information is not in servent List 
	*/		
	public boolean isContainNode(ServentInfo a){
		for(int i = 0;i<serventList.size();i++){
			ServentInfo b = serventList.get(i);
			if(b.IP.equals(a.IP) && b.port == a.port){
				return true;
			}
		}
		return false;
	}
	
	/**
	* Method that checks if a node is in Servent List
	* @return boolean
	* 	true if that Servent Information is stored in servent list
	* 	false if that Servent Information is not in servent List 
	*/
	public void printNode(){
		System.out.println("\nCurrent neigbor: " + serventList.size());
		for(int i = 0;i<serventList.size();i++){
			ServentInfo b = serventList.get(i);
			System.out.println("(" + b.IP + ":" + b.port + "); ");
		}
		System.out.println("");
	}
	
	/**
	* Method that displays filename
	* @see filename in console
	*/
	public void printFilenames(){
		System.out.println("\nAgent list of files: " + this.filenames.size());
		for(int i = 0;i<filenames.size();i++){
			System.out.println(i+1+ ") " + filenames.get(i));
		}
		System.out.println("");
	}
	
	/**
	* Method that displays filename
	* @see filename in console
	*/
	public boolean isContainFile(String input){
		for(int i = 0;i<filenames.size();i++){
			if(filenames.get(i).equals(input)){
				return true;
			}
		}
		return false;
	}
}
