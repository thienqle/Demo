/*
 * @Thien
 * Model for Node 
 * Node that has Coordinate x and y
 */
public class Node {
	/*
	* Coordinate x
	*/
	private int x;

	/*
	* Coordinate y
	*/
	private int y;
	
	/* 
	* Construct of node
	* @param X,Y
	*	input integer x, integer y
	*/
	public Node(int X, int Y){
		x=X;
		y=Y;
	}
	
	/*
	* Function that returns value of x
	* @return x
	*/
	public int getX(){
		return x;
	}
	
	/*
	* Function that returns value of y
	* @return x
	*/
	public int getY(){
		return y;
	}
}
