/*
* Thien Le
*/
package TTictactoe;

import java.util.ArrayList;

public class Board {
	private int [][] aBoard;
	public static final int _X = 1;
	public static final int _O = 2;
	public static final int _NULL = 0;
	public ArrayList<Cell> aEmptyCellList;
	public int PlayerTurn;
	
	public Board(int PlayerTurn){
		aBoard = new int[3][3];
		aEmptyCellList = new ArrayList<Cell>();
		for(int i=0;i<3;i++){
			for(int j = 0;j<3;j++){
				aBoard[i][j] = 0;
				aEmptyCellList.add(new Cell(i,j));
			}
		}
		this.PlayerTurn = PlayerTurn;
	}
	
	public Board(Board Input){
		this.aBoard = new int[3][3];
		this.aEmptyCellList = new ArrayList<Cell>();
	
		for(int i=0;i<3;i++){
			for(int j = 0;j<3;j++){
				this.aBoard[i][j] = Input.aBoard[i][j];
			}
		}
		this.PlayerTurn = Input.PlayerTurn;
		getEmptyList();
		//System.out.println(this.aEmptyCellList.toString());
	}
	
	public void getEmptyList(){
		this.aEmptyCellList.clear();
		for(int i=0;i<3;i++){
			for(int j = 0;j<3;j++){
				if(aBoard[i][j] == 0){
					this.aEmptyCellList.add(new Cell(i,j));
				}
			}
		}
	}
	
	public static char getSymbol(int value){
		if(value ==1){
			return 'X';
		} else if (value ==2){
			return 'O';
		} else {
			return '_';
		}
	}
	
	/*Check if any player has three in a row */
	public boolean isThreeInARow(int value){
		
		int []diag1 = new int[3];
		int []diag2 = new int[3];
		for(int i=0;i<3;i++){
			diag1[i] = aBoard[i][i];
			diag2[i] = aBoard[i][2-i];
			int []same_row = {aBoard[i][0],aBoard[i][1],aBoard[i][2]};
			int []same_column = {aBoard[0][i],aBoard[1][i],aBoard[2][i]};
			
			if(inEqualArray(same_row,value)){
				return true;
			}
			if(inEqualArray(same_column,value)){
				return true;
			}
		}
		if(inEqualArray(diag1,value)){
			return true;
		}
		if(inEqualArray(diag2,value)){
			return true;
		}
		
		return false;
	}
	
	public int getScore(int player){
		if(isThreeInARow(player)){
			return 1;
		} else if (isThreeInARow(otherPlayer(player))){
			return -1;
		} else {
			return 0;
		}
	}
	
	public int otherPlayer(int player){
		if(player == _X){
			return _O; 
		} else {
			return _X;
		}
	}
	
	public boolean inEqualArray(int []A,int value){
		for(int i =0;i<A.length;i++){
			if(A[i]!=value){
				return false;
			}
		}
		return true;
	}
	
	public void place(int y, int x,int value){
		if(x < 0 && x > 2){
			return;
		}
		if(y < 0 && y > 2){
			return;
		}
		aBoard[y][x] = value;
		this.getEmptyList();
		//removeMove(y,x);
	}
	
		
	public void switchTurn(int player){
		/*Change turn everything */
		if(player == _X){
			this.PlayerTurn = _O; 
		} else {
			this.PlayerTurn = _X;
		}
	}
	
	public void undo(int y, int x,int value){
		aBoard[y][x] = 0;
		aEmptyCellList.add(new Cell(x,y));
	}
	
	public void removeMoveList(ArrayList<Cell> A){
		for(int i=0;i<A.size();i++){
			place(A.get(i).y,A.get(i).x,0);
		}
		A.clear();
	}
	
	public void unPlace(int y, int x,int value){
		aBoard[y][x] = 0;
		aEmptyCellList.add(new Cell(x,y));
	}
	
	public boolean checkAvaibility(int y, int x){
		for(int i = 0;i<this.aEmptyCellList.size();i++){
			if(this.aEmptyCellList.get(i).x == x && this.aEmptyCellList.get(i).y == y){
				return false;
			}
		}
		return true;
	}
	
	public void drawBoard(){
		System.out.println("");
		System.out.println("=========");
		System.out.println("  0 1 2");
		System.out.print("0 ");
		for(int i=0;i<3;i++){
			System.out.print(getSymbol(aBoard[0][i]) + " ");
		}
		System.out.println("\n");
		System.out.print("1 ");
		for(int i=0;i<3;i++){
			System.out.print(getSymbol(aBoard[1][i]) + " ");
		}
		System.out.println("\n");
		System.out.print("2 ");
		for(int i=0;i<3;i++){
			System.out.print(getSymbol(aBoard[2][i]) + " ");
		}
		System.out.println("\n");
		System.out.println("=========");
		System.out.println("");
	}
	
	public boolean isGameOver(){
		if(this.aEmptyCellList ==null){
			//System.out.println("NULL");
			return true;
		}
		if(this.aEmptyCellList.size()==0 || this.isThreeInARow(_X) || this.isThreeInARow(_O)){
			return true;
		}
		return false;
	}
	
	public void printPossibleMove(){
		for(int i=0;i<this.aEmptyCellList.size();i++){
			System.out.print(this.aEmptyCellList.get(i) + " ");
		}
		System.out.println("");
	}
	
	public void removeMove(int y,int x){
		for(int i = 0;i<this.aEmptyCellList.size();i++){
			if(this.aEmptyCellList.get(i).x == x && this.aEmptyCellList.get(i).y == y){
				this.aEmptyCellList.remove(i);
			}
		}
	}
}
