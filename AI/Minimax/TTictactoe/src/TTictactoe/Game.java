/*
* Thien Le
*/
package TTictactoe;

import java.util.Scanner;

import TTictactoe.TMinimax.ScoreAndMove;

public class Game {
	
	public static final int _X = 1;
	public static final int _O = 2;
	
	public static void main(String []agrv){
		TMinimax aMinimax = new TMinimax();
		Board aBoard = new Board(1);
		aBoard.drawBoard();
		
		int symbol = 0;
		while(symbol!=1 && symbol!=2){
			Scanner aScanner = new Scanner(System.in);
			System.out.println("Choose your symbol (1) for X or (2) for O: ");
			symbol = aScanner.nextInt();
			String sSymbol = symbol == 1?"X":"O";
			System.out.println("Your symbol is: " + sSymbol);
		}
		
		int turn = 0;
		while(turn!=1 && turn!=2){
			Scanner aScanner = new Scanner(System.in);
			System.out.println("Choose your turn (1) player goes first or (2) computer goes first: ");
			turn = aScanner.nextInt();
		}
		
		if(turn==1){
			System.out.println("Player goes fist");
			aGamePlay_player_first(aBoard,aMinimax,symbol);
		} else {
			System.out.println("Computer goes fist");
			aGamePlay_computer_first(aBoard,aMinimax,symbol);
		}
	}

	public static void aGamePlay_player_first(Board aBoard,TMinimax aMinimax,int player){

		int Computer;
		if(player == _X){
			Computer = _O;
		} else {
			Computer = _X;
		}
		
		while(!aBoard.isGameOver()){
			int [] location = ParseLocation(getInput());
			while(aBoard.checkAvaibility(location[0],location[1])){
				System.out.println("You cannot move to that position.");
				location = ParseLocation(getInput());
			}
			aBoard.place(location[0],location[1],player);
			aBoard.drawBoard();
			
			/*Computer move */
			aBoard.PlayerTurn= player;
			ScoreAndMove aTmp = aMinimax.minimax(aBoard,Computer,Integer.MAX_VALUE,0);
			Cell aMove = aTmp.aMove;
			
			aMinimax.count = 0;
			if(!aBoard.isGameOver()){
				aBoard.place(aMove.x,aMove.y,Computer);
				aBoard.drawBoard();	
			}
		}
	}
	
	public static void aGamePlay_computer_first(Board aBoard,TMinimax aMinimax,int player){
		
		int Computer;
		if(player == _X){
			Computer = _O;
		} else {
			Computer = _X;
		}
		
		while(!aBoard.isGameOver()){
			
			
			/*Computer move */
			aBoard.PlayerTurn= player;
			ScoreAndMove aTmp = aMinimax.minimax(aBoard,Computer,Integer.MAX_VALUE,0);
			Cell aMove = aTmp.aMove;
			
			aMinimax.count = 0;
			if(!aBoard.isGameOver()){
				aBoard.place(aMove.x,aMove.y,Computer);
				aBoard.drawBoard();	
			}
			
			/*player move */
			if(!aBoard.isGameOver()){
				int [] location = ParseLocation(getInput());
				while(aBoard.checkAvaibility(location[0],location[1])){
					System.out.println("You cannot move to that position.");
					location = ParseLocation(getInput());
				}
				aBoard.place(location[0],location[1],player);
				aBoard.drawBoard();
			}
		}
	}
	
	/*Get input from user */
	static public String getInput(){
		int x,y;
		Scanner aScanner = new Scanner(System.in);
		System.out.println("Enter your move (row and column): ");
		String input = aScanner.nextLine();
		return input;
	}
	
	/*Parse input to coordinators */
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
