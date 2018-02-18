package textClassification;

import java.util.Hashtable;
import java.util.Set;

/**
 * The model that use for mapping of data
 */
public class Map {
	
	/**
	 * HashMap data Mapping between id to label name
	 */
	private Hashtable<Integer,String> mapData;

	/**
	 * Construct a map between id and label name
	 * @param input
	 * 	the content of file
	 */
	public Map(String input) {
		mapData = new Hashtable<Integer,String>();
		String []line = input.split("\n");
		for(String s : line) {
			String []field = s.split(",");
			if(field.length == 2) {
				mapData.put(Integer.parseInt(field[0]),field[1]);
			}
		}
	}
	
	/**
	 * Function that return mapping data
	 * @return
	 * 	Return Hashtable of mapping data
	 */
	public Hashtable<Integer,String> getMap(){
		return this.mapData;
	}
	
	/**
	 * Function that return set of classes (categories)
	 * @return
	 * 	Return Hashtable of mapping data
	 */
	public Set<Integer> getCategories(){
		return this.mapData.keySet();
	}
}
