package textClassification;

import java.util.Hashtable;
import java.util.Set;

/**
 * This model of data that use for data of application
 */
public class Data {

	
	/**
	 * Hashtable that store data
	 */
	private Hashtable<Integer,Hashtable<Integer,Integer>> data;
	
	/**
	 * Hashtable that store number of words occur of each doc
	 */
	private Hashtable<Integer,Integer> countWordDoc;
	
	private int totalWordCount;
	
	/**
	 * Construct Data with docID,wordID,count
	 * @param input
	 * 	the content of file
	 */
	public Data(String input) {
		countWordDoc = new Hashtable<Integer,Integer>();
		totalWordCount = 0;
		data= new Hashtable<Integer,Hashtable<Integer,Integer>>();

		String []line = input.split("\n");
		for(String s : line) {
			String []field = s.split(",");
			if(field.length == 3) {
				totalWordCount+=Integer.parseInt(field[2]);
				
				int doc_ID = Integer.parseInt(field[0]);
				int word_ID = Integer.parseInt(field[1]);
				int count_ID = Integer.parseInt(field[2]);
				if(data.containsKey(doc_ID)){
					data.get(doc_ID).put(word_ID,count_ID);
				} else {
					Hashtable<Integer,Integer> pair = new Hashtable<Integer,Integer>();
					pair.put(word_ID,count_ID);
					data.put(doc_ID, pair);
				}
				
				if(countWordDoc.containsKey(doc_ID)) {
					countWordDoc.put(doc_ID,countWordDoc.get(doc_ID) + count_ID);
				} else {
					countWordDoc.put(doc_ID,count_ID);
				}
			}
		}
	}
	
	/**
	 * Function that return total number of word in all documents
	 * @return numberOfword
	 * 	Number of word in all of documents 
	 */
	public int getTotalWordCount() {
		return this.totalWordCount;
	}
	
	/**
	 * Function that return total number of word of given document and given word
	 * @param doc,word
	 * 	Given document and word
	 * @return numberOfword
	 * 	Number of word in all of documents 
	 */
	public int getWordCount(int doc,int word) {
		if(this.data.get(doc).containsKey(word)) {
			return this.data.get(doc).get(word);
		} else {
			return 0;
		}
	}
	
	/**
	 * Function that return total number of word of given document
	 * @param doc,word
	 * 	Given document and word
	 * @return numberOfword
	 * 	Number of word in all of documents 
	 */
	public int getWordCount1Doc(int doc) {
		return this.countWordDoc.get(doc);
	}
	
	/**
	 * Function that return list of word of given document
	 * @param doc
	 * 	Given document 
	 * @return Set<Integer>
	 * 	Number of word in all of documents 
	 */
	public Set<Integer> getWordList(int doc) {
		return this.data.get(doc).keySet();
		
	}
	
	/**
	 * Function that print all data
	 */
	public void printAll() {

		Set<Integer> keys = data.keySet();
		for(int k:keys) {
			System.out.println(data.get(k));
		}
	}
}
