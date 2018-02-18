package textClassification;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * The model that use for label of data
 */
public class Label {

	/**
	 * Hashtable that store label of data
	 */
	private Hashtable<Integer,ArrayList<Integer>> label;
	
	/**
	 * Hashtable that store label count of data
	 */
	private Hashtable<Integer,Integer> labelCount;
	
	/**
	 * Hashtable that store number of all file
	 */
	private int total;
	
	/**
	 * Construct a label
	 * @param input
	 * 	String content of label file
	 */
	public Label(String input) {
		label = new Hashtable<Integer,ArrayList<Integer>>();
		labelCount = new Hashtable<Integer,Integer>();
		total = 0;
		String []line = input.split("\n");
		int index = 1;
		for(String s : line) {
			int id = Integer.parseInt(s);
			
			/*update label */
			if(!label.containsKey(id)) {
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				tmp.add(index);
				label.put(id,tmp);
			} else {
				label.get(id).add(index);
			}
			index++;
			
			/*update labelCount */
			if (labelCount.containsKey(id)) {
				labelCount.put(id,labelCount.get(id)+1);
			} else {
				labelCount.put(id,0);
			}
			total++;
		}
	}
	
	/**
	 * Function that return list label
	 * @return ArrayList
	 * 	ArrayList of label
	 */
	public Hashtable<Integer,ArrayList<Integer>> getLabel(){
		return this.label;
	}
	
	/**
	 * Function that return list of file in give category
	 * @param int
	 * 	Given category
	 * @return ArrayList
	 * 	ArrayList of files
	 */
	public ArrayList<Integer> getFileList(int category){
		return this.label.get(category);
	}
	
	
	/**
	 * Function that return if file belong to category or not
	 * @param int
	 * 	Given category
	 * @return boolean
	 * 	ArrayList of files
	 */
	public boolean checkFileCategory(int file,int category){
		return this.label.get(category).contains(file);
	}
	
	/**
	 * compute prior of an give category P(wj)
	 * @param category
	 * 	int of category ID
	 * @return float
	 * 	prior of given category ID
	 */
	public float computeClassPrior(int category) {
		int occurrence = labelCount.get(category);
		return (float)occurrence/(float)total;
	}
	
	/**
	 * Function that return total number of file
	 * @return Integer
	 * 	Total number of file that is labeled
	 */
	public int getTotalNoFiles(){
		return this.total;
	}
	
	/**
	 * Function that print ArrayList to debug
	 */
	public void printLabel(){
		Set<Integer> key = labelCount.keySet();
		for(int k:key) {
			System.out.println(k + " " + labelCount.get(k));
		}
	}
}
