/*
 * @Thien
 * 
 */
import java.util.ArrayList;

public class SimplePathFinding {
	private ArrayList<Node> aQueue = new ArrayList<Node>();
	private int [][]aGrid = new int[5][10]; //This is the map
	private int [][]mVisit = new int[5][10]; //This store status of a Node in the map 
	
	
	public SimplePathFinding(){
		for(int i=0;i<5;i++){
			for(int j=0;j<10;j++){
				mVisit[i][j] = 0;
			}
		}
		
		/*Set obstacles of the map */
		aGrid[1][1]=1000;
		aGrid[1][0]=1000;
		aGrid[2][1]=1000;
		aGrid[2][2]=1000;
		aGrid[3][3]=1000;
		aGrid[3][4]=1000;
		aGrid[3][5]=1000;
		aGrid[2][5]=1000;
		aGrid[1][5]=1000;
		
	}
	
	public void FindPath(int SourceX,int SourceY,int DesX, int DesY){	
		int maxX = 5;
		int maxY=10;
		
		/*Print out the source's coordinates of the path*/
		//int SourceX = 0;
		//int SourceY = 0;
		
		/*Print out the destination's coordinates of the path*/
		//int DesX = 4;
		//int DesY = 8;
		
		int CurrentY = SourceY;
		int CurrentX = SourceX;
		aGrid[SourceX][SourceY]=0;
		mVisit[SourceX][SourceY]=1;
		
		printMap(SourceX,SourceY,DesX,DesY);
		
		/*Print out the source's coordinates*/
		System.out.println("\nStart point: x=" + SourceX + " y=" +  SourceY);
		
		/*Print out the destination's coordinates*/
		System.out.println("Destination: x=" + DesX + " y=" +  DesY);
		
		Node aNode = new Node(CurrentX,CurrentY);
		aQueue.add(aNode);


		while(aQueue.size()>0) {
			//System.out.print("Current " + aQueue.get(0).getX() + " " + aQueue.get(0).getY()+";");
			ArrayList<Node> temp = new ArrayList<Node>();
			temp = FindNeighBor(new Node(aQueue.get(0).getX(),aQueue.get(0).getY()),aGrid,maxX,maxY);
			while(temp.size()>0){
				VisitNode(temp.get(0),aQueue.get(0),aGrid);
				temp.remove(0);
			}
			mVisit[aQueue.get(0).getX()][aQueue.get(0).getY()]=1;
			aQueue.remove(0);
		}
		
		
		//Get path
		int currentValue=aGrid[DesX][DesY];
		aQueue.add(new Node(DesX,DesY));
		//File minimum cost Node in the neiborList
		int count=0;
		while(aGrid[aQueue.get(aQueue.size()-1).getX()][aQueue.get(aQueue.size()-1).getY()]!=0 && count<50){
			aQueue.add(findMinArray((FindNeighBor(aQueue.get(aQueue.size()-1),aGrid,maxX,maxY)),aGrid));
		}
		
		/*Print the path*/
		for(int i=aQueue.size()-1;i>0;i--){
			System.out.println("Move to x=" + aQueue.get(i).getX() + " y=" + aQueue.get(i).getY()+";");
		}
		
		printMapWithPath(SourceX,SourceY,DesX,DesY);
	}

	/* Display a map to the screen */
	public void printMap(int sourceX, int sourceY, int desX, int desY){
		for(int i=0;i<5;i++){
			for(int j=0;j<10;j++){
				if(i==sourceX && j==sourceY){
					System.out.print("S");
				} else if (i==desX && j==desY){
					System.out.print("D");
				} else if(aGrid[i][j]==1000){
					System.out.print("#");
				} else {
					System.out.print("0");
				}
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}
	
	/* Display a map to the screen */
	public void printMapWithPath(int sourceX, int sourceY, int desX, int desY){
		System.out.println("");
		for(int i=0;i<5;i++){
			for(int j=0;j<10;j++){
				if (this.isContainQueue(i, j)){
					if(i==sourceX && j==sourceY){
						System.out.print("S");
					} else {
						System.out.print("*");
					}
				} else if (i==desX && j==desY){
					System.out.print("D");
				} else if(aGrid[i][j]==1000){
					System.out.print("#");
				} else {
					System.out.print("0");
				}
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}
	
	public boolean isContainQueue(int x,int y){
		for(int i=aQueue.size()-1;i>0;i--){
			if(aQueue.get(i).getX() == x && aQueue.get(i).getY() == y){
				return true;
			}
		}
		return false;
	}
	
	/* This function will add Node A to queue if it has not been visit in the map */	
	public void VisitNode(Node A, Node B, int[][] aGrid) {
		if(aGrid[A.getX()][A.getY()]==0 && mVisit[A.getX()][A.getY()]==0){
			aGrid[A.getX()][A.getY()]=aGrid[B.getX()][B.getY()]+1;
			aQueue.add(A);
			//System.out.print("Added " + A.getX() + " " + A.getY() + ", ");
		}
	}
	
	/*Find all neighbor nodes of a node and add it to a List*/
	public ArrayList<Node> FindNeighBor(Node A, int[][] aGrid, int maxX, int maxY){
		ArrayList<Node> aQueueNeigbor = new ArrayList<Node>();
		if(A.getX()==0){
					if(A.getY()==0){
						aQueueNeigbor.add(new Node(A.getX()+1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()+1));
					} else if (A.getY()==(maxY -1)) {
						aQueueNeigbor.add(new Node(A.getX()+1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()-1));
					} else {
						aQueueNeigbor.add(new Node(A.getX()+1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()+1));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()-1));
					}
				} else if (A.getX()==(maxX-1)) {
					if(A.getY()==0){
						aQueueNeigbor.add(new Node(A.getX()-1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()+1));
					} else if (A.getY()==(maxY -1)) {
						aQueueNeigbor.add(new Node(A.getX()-1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()-1));
					} else {
						aQueueNeigbor.add(new Node(A.getX()-1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()+1));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()-1));
					}
				} else {
					if(A.getY()==0){
						aQueueNeigbor.add(new Node(A.getX()+1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX()-1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()+1));
					} else if (A.getY()==(maxY -1)) {
						aQueueNeigbor.add(new Node(A.getX()+1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX()-1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()-1));
					} else {
						aQueueNeigbor.add(new Node(A.getX()+1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX()-1,A.getY()));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()+1));
						aQueueNeigbor.add(new Node(A.getX(),A.getY()-1));
					}
				}
		return aQueueNeigbor;
	}
	
	/* Find a neighbor that has minimum cost */
	public Node findMinArray(ArrayList<Node> NeiborList, int [][]aGrid){
		int min = aGrid[NeiborList.get(0).getX()][NeiborList.get(0).getY()];
		Node temp = NeiborList.get(0);
		for(int i=0;i<NeiborList.size();i++){
			if(aGrid[NeiborList.get(i).getX()][NeiborList.get(i).getY()]<=min){
				min=aGrid[NeiborList.get(i).getX()][NeiborList.get(i).getY()];
				temp= new Node(NeiborList.get(i).getX(),NeiborList.get(i).getY());
			}
		}
		return temp;
	}
}

