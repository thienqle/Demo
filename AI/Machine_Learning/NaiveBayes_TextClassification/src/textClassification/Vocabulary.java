package textClassification;

import java.util.Hashtable;

/**
 * Model for vocabulary
 */
public class Vocabulary {
	
	/**
	 * HashMap data Mapping between id to word
	 */
	private Hashtable<Integer,String> words;
	
	/**
	 * total number of word
	 */
	private int count;

	/**
	 * Construct a map between id and label name
	 * @param input
	 * 	the content of file
	 */
	public Vocabulary(String input) {
		words = new Hashtable<Integer,String>();
		count = 0;
		String []line = input.split("\n");
		int index = 1;
		for(String s : line) {	
			words.put(index,s);
			index++;
			count++;
		}
	}
	
	/**
	 * Function that return vocabulary data
	 * @return 
	 * 	Return Hashtable of vocabulary data
	 */
	public Hashtable<Integer,String> getVocabulary(){
		return this.words;
	}
	
	/**
	 * Function that return total number of word
	 * @return
	 * 	Return total number of word
	 */
	public int getTotal(){
		return this.count;
	}
	
	
}
