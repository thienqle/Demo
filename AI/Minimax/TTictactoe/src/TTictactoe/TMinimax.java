/*
* Thien Le
*/
package TTictactoe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class TMinimax {
	
	public class ScoreAndMove {
		public int Score;
		public Cell aMove;
		
		public ScoreAndMove(){
			Score = 0;
			aMove = new Cell();
		}
		
		public ScoreAndMove(int score,Cell aMove){
			Score = score;
			this.aMove = aMove;
		}
		
		public String toString(){
			String output = "";
			output += "Score = " + Score;
			if(aMove!=null){
				output += "; Move = " + aMove.toString();
			}
			return output;
		}
	}
	public int count;
	public static final int _X = 1;
	public static final int _O = 2;
	
	ArrayList<Cell> aUndoList;
	public  TMinimax(){
		this.aUndoList = new ArrayList<Cell>();
		count=0;
	}
	
			
	public ScoreAndMove minimax(Board aBoardGame,int player,int maxDepth, int currentDepth){
		//Base condition / Stop condition
		if(aBoardGame.isGameOver() || currentDepth == maxDepth){
			//Return score and null of move steps
			return new ScoreAndMove(aBoardGame.getScore(aBoardGame.PlayerTurn),null);
		}
		
		ScoreAndMove BestScoreAndMove = new ScoreAndMove();
		BestScoreAndMove.aMove = null;
		
		ScoreAndMove CurrentScoreAndMove = new ScoreAndMove();
		
		if(aBoardGame.PlayerTurn == player){
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
	
		for(int i = 0; i< aBoardGame.aEmptyCellList.size(); i++){
			Board temp_board = new Board(aBoardGame);
			
			/*Move a move - to see if this move have highest score */
			Cell aMove = temp_board.aEmptyCellList.get(i);
			temp_board.place(aMove.y,aMove.x, player);
			
			/*Call minimax for next player*/
			CurrentScoreAndMove = minimax(temp_board,next_player,maxDepth,currentDepth +1);
			temp_board.place(aMove.y, aMove.x, 0);
			
			/*Call this inside for loop */
			if(aBoardGame.PlayerTurn == player){
				if(CurrentScoreAndMove.Score > BestScoreAndMove.Score){
					if(CurrentScoreAndMove.Score > BestScoreAndMove.Score){
						BestScoreAndMove.Score = CurrentScoreAndMove.Score;
						BestScoreAndMove.aMove = new Cell(aMove.x,aMove.y);
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
					BestScoreAndMove.aMove = new Cell(aMove.x,aMove.y);
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
	
	
	public void print(ScoreAndMove aScoreAndMove){
		System.out.println("Score = " + aScoreAndMove.Score);
		if(aScoreAndMove.aMove == null){
			return;
		}
		System.out.println("Move: " + aScoreAndMove.aMove.toString());
	}
	
}
