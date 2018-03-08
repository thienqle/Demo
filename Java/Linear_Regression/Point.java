/**
 * Thien Le
 */
package linearRegression;

/**
 * Model of a Point 
 */
public class Point {

	/**
	 * coordinate x,y of a point
	 */
	public double x;
	public double y;

	/**
	 * Construct of a point with given x,y
	 * @param x
	 * @param y
	 */
	public Point(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + Double.toString(x) + "," + Double.toString(y) + ")";
	}
}
