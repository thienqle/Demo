/*
* Thien Le
*/
package tictactoe;

/*
* Model of a Cell in Board game
*/
public class Cell {

	/*
	* Coordinate x and y
	*/
	public int x,y;
		

	/**
	* Construct a cell
	*/
	public Cell(){
		this.x = 0;
		this.y = 0;
	}
	
	/**
	* Construct a cell from given coordinate
	* @param x: x coordinate
	* @param y: y coordinate
	*/
	public Cell(int y, int x){
		this.x = x;
		this.y = y;
	}
	
	/**
	* Method that displays a cell
	*/
	public String toString(){
		return "(" + x + "," + y +")"; 
	}
		
}
