/*
* Thien Le
*/
package tictactoe;

import java.util.ArrayList;

/*
* Model that represent board game
*/
public class Board {

	/*
	* Field that represent for the Board
	*/
	private int [][] board;

	/*
	* Field value of players
	*/
	public static final int _X = 1;
	public static final int _O = 2;
	public static final int _NULL = 0;

	/*
	* List of empty cell
	*/
	public ArrayList<Cell> emptyCellList;

	/*
	* Current player turn
	*/
	public int playerTurn;
	
	/**
	* Contructs a board game given with player turn
	* @param playerTurn
	* 	1 player X go first	
	* 	2 player X go first
	*/
	public Board(int playerTurn){
		board = new int[3][3];
		emptyCellList = new ArrayList<Cell>();
		for(int i=0;i<3;i++){
			for(int j = 0;j<3;j++){
				board[i][j] = 0;
				emptyCellList.add(new Cell(i,j));
			}
		}
		this.playerTurn = playerTurn;
	}
	
	/**
	* Contructs a board game that copy another board
	* @param input board
	*/
	public Board(Board input){
		this.board = new int[3][3];
		this.emptyCellList = new ArrayList<Cell>();
	
		for(int i=0;i<3;i++){
			for(int j = 0;j<3;j++){
				this.board[i][j] = input.board[i][j];
			}
		}
		this.playerTurn = input.playerTurn;
		getEmptyList();
	}

	/**
	* Function that update the list of cells that is empty (which is available for next player
	*/	
	public void getEmptyList(){
		this.emptyCellList.clear();
		for(int i=0;i<3;i++){
			for(int j = 0;j<3;j++){
				if(board[i][j] == 0){
					this.emptyCellList.add(new Cell(i,j));
				}
			}
		}
	}
	
	/**
	* Function that return symbol of player by given player value
	* @param value: player value (1 for X, 2 for Y)
	* @return char: char represent for player (X or Y)
	*/	
	public char getSymbol(int value){
		if(value ==1){
			return 'X';
		} else if (value ==2){
			return 'O';
		} else {
			return '_';
		}
	}
	
	/**
	* Function that checks if any player has three in a row
	* @param value: player value (1 for X, 2 for Y)
	* @return boolean: 
	*  return true if there is three pieces in a row for given player value
	*/	
	public boolean isThreeInARow(int value){
		
		int []diag1 = new int[3];
		int []diag2 = new int[3];
		for(int i=0;i<3;i++){
			diag1[i] = board[i][i];
			diag2[i] = board[i][2-i];
			int []same_row = {board[i][0],board[i][1],board[i][2]};
			int []same_column = {board[0][i],board[1][i],board[2][i]};
			
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
	
	/**
	* Function that returns score of player
	* @param value: player value (1 for X, 2 for Y)
	* @return integer: 
	*  return score of player (each piece of given player score 1, each opponent piece score -1)
	*/	
	public int getScore(int player){
		if(isThreeInARow(player)){
			return 1;
		} else if (isThreeInARow(otherPlayer(player))){
			return -1;
		} else {
			return 0;
		}
	}
	

	/**
	* Function that return oponent player value
	* @param value: player value (1 for X, 2 for Y)
	* @return integer: value of opponent player
	*/	
	public int otherPlayer(int player){
		if(player == _X){
			return _O; 
		} else {
			return _X;
		}
	}
	
	/**
	* Function that return true if array contains value
	* @param value
	* @return boolean
	* 	return true if array contains value
	* 	return false if array contains value
	*/	
	public boolean inEqualArray(int []A,int value){
		for(int i =0;i<A.length;i++){
			if(A[i]!=value){
				return false;
			}
		}
		return true;
	}
	
	/**
	* Function that place value on board
	* @param value
	* @param y: coordinate y on the board
	* @param x: coordinate x on the board
	*/
	public void place(int y, int x,int value){
		if(x < 0 && x > 2){
			return;
		}
		if(y < 0 && y > 2){
			return;
		}
		board[y][x] = value;
		this.getEmptyList();
	}
	
	/**
	* Function that switch player turn
	*/	
	public void switchTurn(int player){
		/*Change turn everything */
		if(player == _X){
			this.playerTurn = _O; 
		} else {
			this.playerTurn = _X;
		}
	}
	
	/**
	* Function that undo a move of given player
	* @param value: player value (1 for X, 2 for Y)
	* @param y: coordinate y on the board
	* @param x: coordinate x on the board
	*/	
	public void undo(int y, int x,int value){
		board[y][x] = 0;
		emptyCellList.add(new Cell(x,y));
	}
	

	/**
	* Function that clean up given list of cell
	* @param A: given ArrayList
	*/
	public void removeMoveList(ArrayList<Cell> A){
		for(int i=0;i<A.size();i++){
			place(A.get(i).y,A.get(i).x,0);
		}
		A.clear();
	}
	
	/**
	* Function that undo a move of given player
	* @param value: player value (1 for X, 2 for Y)
	* @param y: coordinate y on the board
	* @param x: coordinate x on the board
	*/	
	public void unPlace(int y, int x,int value){
		board[y][x] = 0;
		emptyCellList.add(new Cell(x,y));
	}
	
	/**
	* Function that checks if a cell is avaliable for player to play
	* @param y: coordinate y on the board
	* @param x: coordinate x on the board
	* @return boolean
	* 	return true if array contains value
	* 	return false if array contains value
	*/	
	public boolean checkAvaibility(int y, int x){
		for(int i = 0;i<this.emptyCellList.size();i++){
			if(this.emptyCellList.get(i).x == x && this.emptyCellList.get(i).y == y){
				return false;
			}
		}
		return true;
	}
	
	/**
	* Function that displays game board on screen
	*/
	public void drawBoard(){
		System.out.println("");
		System.out.println("=========");
		System.out.println("  0 1 2");
		System.out.print("0 ");
		for(int i=0;i<3;i++){
			System.out.print(getSymbol(board[0][i]) + " ");
		}
		System.out.println("\n");
		System.out.print("1 ");
		for(int i=0;i<3;i++){
			System.out.print(getSymbol(board[1][i]) + " ");
		}
		System.out.println("\n");
		System.out.print("2 ");
		for(int i=0;i<3;i++){
			System.out.print(getSymbol(board[2][i]) + " ");
		}
		System.out.println("\n");
		System.out.println("=========");
		System.out.println("");
	}
	

	/**
	* Function that check if game is at end stage
	* @return boolean
	* 	return true if game is end
	* 	return false if not
	*/
	public boolean isGameOver(){
		if(this.emptyCellList ==null){
			return true;
		}
		if(this.emptyCellList.size()==0 || this.isThreeInARow(_X) || this.isThreeInARow(_O)){
			return true;
		}
		return false;
	}

	/**
	* Function that print possible move for debug
	*/
	public void printPossibleMove(){
		for(int i=0;i<this.emptyCellList.size();i++){
			System.out.print(this.emptyCellList.get(i) + " ");
		}
		System.out.println("");
	}
	
	/**
	* Function that remove a cell out of empty list
	* @param y: coordinate y on the board
	* @param x: coordinate x on the board
	*/
	public void removeMove(int y,int x){
		for(int i = 0;i<this.emptyCellList.size();i++){
			if(this.emptyCellList.get(i).x == x && this.emptyCellList.get(i).y == y){
				this.emptyCellList.remove(i);
			}
		}
	}
}
