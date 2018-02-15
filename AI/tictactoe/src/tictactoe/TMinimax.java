/*
* Thien Le
*/
package tictactoe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

/*
* Model of Minimax operation
*/
public class TMinimax {
	
	/*
	* Model that represent for Score and Move 
	*/
	public class ScoreAndMove {
		public int Score;
		public Cell move;
		
		/**
		* Construct score and move
		*/
		public ScoreAndMove(){
			Score = 0;
			move = new Cell();
		}
		
		/**
		* Construct score and move with given score and move
		* @param: score value
		* @param: move a cell
		*/
		public ScoreAndMove(int score,Cell move){
			Score = score;
			this.move = move;
		}
		
		/**
		* Format score and move as a string
		* @return: a string that can be print out
		*/
		public String toString(){
			String output = "";
			output += "Score = " + Score;
			if(move!=null){
				output += "; Move = " + move.toString();
			}
			return output;
		}
	}

	/*
	* Variables of a minimax
	*/
	public int count;
	public static final int _X = 1;
	public static final int _O = 2;
	ArrayList<Cell> undoList;

	/**
	* Construct a minimax operator
	*/
	public  TMinimax(){
		this.undoList = new ArrayList<Cell>();
		count=0;
	}
	
	
	/**
	* Method that perform minimax on a board game 
	* @param: player - current player
	* @param: maxDepth - maximum level depth of minimax tree 
	* @param: currentDepth - current level depth of minimax tree 
	* @return: return a best Score with move to achieve that best score
	*/		
	public ScoreAndMove minimax(Board aBoardGame,int player,int maxDepth, int currentDepth){
		//Base condition / Stop condition
		if(aBoardGame.isGameOver() || currentDepth == maxDepth){
			//Return score and null of move steps
			return new ScoreAndMove(aBoardGame.getScore(aBoardGame.playerTurn),null);
		}
		
		ScoreAndMove BestScoreAndMove = new ScoreAndMove();
		BestScoreAndMove.move = null;
		
		ScoreAndMove CurrentScoreAndMove = new ScoreAndMove();
		
		if(aBoardGame.playerTurn == player){
			BestScoreAndMove.Score = Integer.MIN_VALUE;
		} else {
			BestScoreAndMove.Score = Integer.MAX_VALUE;
		}
		count++;
		
		/*Swicth player to call */  
		int next_player;
		if(player == _X){
			next_player = _O;
		} else {
			next_player = _X;
		}
	
		for(int i = 0; i< aBoardGame.emptyCellList.size(); i++){
			Board temp_board = new Board(aBoardGame);
			
			/*Move a move - to see if this move have highest score */
			Cell move = temp_board.emptyCellList.get(i);
			temp_board.place(move.y,move.x, player);
			
			/*Call minimax for next player*/
			CurrentScoreAndMove = minimax(temp_board,next_player,maxDepth,currentDepth +1);
			temp_board.place(move.y, move.x, 0);
			
			/*Call this inside for loop */
			if(aBoardGame.playerTurn == player){
				if(CurrentScoreAndMove.Score > BestScoreAndMove.Score){
					if(CurrentScoreAndMove.Score > BestScoreAndMove.Score){
						BestScoreAndMove.Score = CurrentScoreAndMove.Score;
						BestScoreAndMove.move = new Cell(move.x,move.y);
						/*if(BestScoreAndMove.Score == 1){
							break;
						}*/
					} else {
						//Do nothing
					}
				}
			} else {
				if(CurrentScoreAndMove.Score < BestScoreAndMove.Score){
					BestScoreAndMove.Score = CurrentScoreAndMove.Score;
					BestScoreAndMove.move = new Cell(move.x,move.y);
					/*if(BestScoreAndMove.Score == -1){
						break;
					}*/
				} else {
					//Do nothing
				}
			}	
			
		}
		
		
		return BestScoreAndMove;
	}
	
	/**
	* Functions that displays score and move the the screen	
	* @param ScoreAndMove
	*/
	public void print(ScoreAndMove aScoreAndMove){
		System.out.println("Score = " + aScoreAndMove.Score);
		if(aScoreAndMove.move == null){
			return;
		}
		System.out.println("Move: " + aScoreAndMove.move.toString());
	}
	
}
