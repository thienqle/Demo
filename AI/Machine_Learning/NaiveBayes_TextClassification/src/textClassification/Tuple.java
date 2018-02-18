package textClassification;

/**
 * Model for tuple value <x,y>
 */
public class Tuple {
	private int x;
	private int y;
	
	/**
	 * Construct tuple with default value
	 */
	public Tuple() {
		x = 0;
		y = 0;
	}
	
	/**
	 * Construct tuple with default given input value
	 * @param x,y
	 * 	First and second value of tuple
	 */
	public Tuple(int x,int y) {
		x = 0;
		y = 0;
	}
	
	/**
	 * Function to return value x
	 * @return x
	 * 	first value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Function to return value y
	 * @return y
	 * 	second value
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Function to compare 2 value of Tuple
	 * @param a,b
	 * 	First and second values to compare
	 * @return
	 * 	Return true if two given value is equal to values of tuple 
	 */
	public boolean isEqual(int a,int b) {
		return (x==a && y==b);
	}

	/**
	 * Function to compare Tuples
	 * @param tuple
	 * 	First and second values to compare
	 * @return
	 * 	Return true if two given value is equal to values of tuple 
	 */
	public boolean isEqual(Tuple input) {
		return (x==input.getX() && y==input.getY());
	}
}
