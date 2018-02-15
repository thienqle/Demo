/*
* Thien Le
*/
package tictactoe;

import java.util.Scanner;

import tictactoe.TMinimax.ScoreAndMove;

/*
* Model of a game
*/
public class Game {
	
	/*
	* Value of player X 
	* X is 1, O is 2
	*/
	public static final int _X = 1;
	public static final int _O = 2;
	
	/**
	* Main method
	*/
	public static void main(String []agrv){
		TMinimax minimax = new TMinimax();
		Board board = new Board(1);
		board.drawBoard();
		
		int symbol = 0;
		while(symbol!=1 && symbol!=2){
			Scanner scanner = new Scanner(System.in);
			System.out.println("Choose your symbol (1) for X or (2) for O: ");
			symbol = scanner.nextInt();
			String strSymbol = symbol == 1?"X":"O";
			System.out.println("Your symbol is: " + strSymbol);
		}
		
		int turn = 0;
		while(turn!=1 && turn!=2){
			Scanner scanner = new Scanner(System.in);
			System.out.println("Choose your turn (1) player goes first or (2) computer goes first: ");
			turn = scanner.nextInt();
		}
		
		if(turn==1){
			System.out.println("Player goes fist");
			playGame_player_first(board,minimax,symbol);
		} else {
			System.out.println("Computer goes fist");
			playGame_computer_first(board,minimax,symbol);
		}
	}

	/**
	* Method that operates game with player goes first
	* @param board: board game	
	* @param minimax: minimax operation
	* @param player: player value
	*/
	public static void playGame_player_first(Board board,TMinimax minimax,int player){

		int Computer;
		if(player == _X){
			Computer = _O;
		} else {
			Computer = _X;
		}
		
		while(!board.isGameOver()){
			int [] location = ParseLocation(getInput());
			while(board.checkAvaibility(location[0],location[1])){
				System.out.println("You cannot move to that position.");
				location = ParseLocation(getInput());
			}
			board.place(location[0],location[1],player);
			board.drawBoard();
			
			/*Computer move */
			board.playerTurn= player;
			ScoreAndMove aTmp = minimax.minimax(board,Computer,Integer.MAX_VALUE,0);
			Cell move = aTmp.move;
			
			minimax.count = 0;
			if(!board.isGameOver()){
				board.place(move.x,move.y,Computer);
				board.drawBoard();	
			}
		}
	}
	
	/**
	* Method that operates game with computer goes first
	* @param board: board game	
	* @param minimax: minimax operation
	* @param player: player value
	*/
	public static void playGame_computer_first(Board board,TMinimax minimax,int player){
		
		int Computer;
		if(player == _X){
			Computer = _O;
		} else {
			Computer = _X;
		}
		
		while(!board.isGameOver()){
			
			
			/*Computer move */
			board.playerTurn= player;
			ScoreAndMove aTmp = minimax.minimax(board,Computer,Integer.MAX_VALUE,0);
			Cell aMove = aTmp.move;
			
			minimax.count = 0;
			if(!board.isGameOver()){
				board.place(aMove.x,aMove.y,Computer);
				board.drawBoard();	
			}
			
			/*player move */
			if(!board.isGameOver()){
				int [] location = ParseLocation(getInput());
				while(board.checkAvaibility(location[0],location[1])){
					System.out.println("You cannot move to that position.");
					location = ParseLocation(getInput());
				}
				board.place(location[0],location[1],player);
				board.drawBoard();
			}
		}
	}
	
	/**
	* Method that gets input from user 
	*/
	static public String getInput(){
		int x,y;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your move (row and column): ");
		String input = scanner.nextLine();
		return input;
	}
	
	/**
	* Method that parse input to coordinators
	* @param input: string input from user	
	* @return array integer: array that stores 2 coordinate value
	*/
	static public int[] ParseLocation(String input){
		int [] location = new int[2];
		location[0] = 0;
		location[1] = 0;
		
		try {
			/*Catch error of input length */
			if(input.split(" ").length !=2){
				ParseLocation(getInput());
			}
			String []tmp = input.split(" ");
			/*Swicth x and y */
			location[0] = Integer.parseInt(tmp[0]);
			location[1] = Integer.parseInt(tmp[1]);
			return location;
		} catch (Exception e){
			/*Catch error of input is not integer*/
			ParseLocation(getInput());	
		}
		return location;
	}
	
	
}
