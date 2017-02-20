/*
* Thien Le
*/
package TTictactoe;

public class Cell {
	public int x,y;
			
	public Cell(){
		this.x = 0;
		this.y = 0;
	}
	
	public Cell(int y, int x){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return "(" + x + "," + y +")"; 
	}
		
}
