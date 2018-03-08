/**
 * Thien Le
 */
package logisticRegression;

/**
 * Model of a podouble 
 */
public class Point {

	/**
	 * coordinate x,y of a podouble
	 */
	public double x1;
	public double x2;
	public int y; //Only 0 and 1

	/**
	 * Construct of a podouble with given x,y
	 * @param x
	 * @param y
	 */
	public Point(double x1,double x2,int y) {
		this.x1 = x1;
		this.x2 = x2;
		this.y = y;
	}
	
	public String toString() {
		return "(" + Double.toString(x1) + "," + Double.toString(x2) + "|" + y + ")";
	}
}
