/**
 * @Thien
 */
import java.util.ArrayList;

/*
* Model of Simple Path Finding
*/
public class SimplePathFinding {

	/*
	* Define max size of the map
	*/
	public static final int MAX_X = 5; 
	public static final int MAX_Y = 10;

	/*
	* Queue that stores path
	*/
	private ArrayList<Node> queue = new ArrayList<Node>();

	/*
	* 2d array represents for map
	*/
	private int [][]grid = new int[5][10];

	/*
	* 2d array represents for visited node on map
	*/
	private int [][]visit = new int[5][10];
	
	/**
	* Constructs an path finding 
	*/
	public SimplePathFinding(){
		for(int i=0;i<5;i++){
			for(int j=0;j<10;j++){
				visit[i][j] = 0;
			}
		}
		
		/*Set obstacles of the map */
		grid[1][1]=1000;
		grid[1][0]=1000;
		grid[2][1]=1000;
		grid[2][2]=1000;
		grid[3][3]=1000;
		grid[3][4]=1000;
		grid[3][5]=1000;
		grid[2][5]=1000;
		grid[1][5]=1000;
		
	}
	
	/**
	* Function that find path from source coordinator to destination coordinator;
	* And display to the shell
	* @param sourceX: x coordinator of source node
	* @param sourceY: y coordinator of source node
	* @param desX: x coordinator of destination node
	* @param desX: x coordinator of destination node
	*   the diameter for this Basketball
	*/
	public void findPath(int sourceX,int sourceY,int desX, int desY){	

		int currentY = sourceY;
		int currentX = sourceX;
		grid[sourceX][sourceY]=0;
		visit[sourceX][sourceY]=1;
		
		printMap(sourceX,sourceY,desX,desY);
		
		/*Print out the source's coordinates*/
		System.out.println("\nStart point: x=" + sourceX + " y=" +  sourceY);
		
		/*Print out the destination's coordinates*/
		System.out.println("Destination: x=" + desX + " y=" +  desY);
		
		Node aNode = new Node(currentX,currentY);
		queue.add(aNode);


		while(queue.size()>0) {
			ArrayList<Node> temp = new ArrayList<Node>();
			temp = findNeighBor(new Node(queue.get(0).getX(),queue.get(0).getY()));
			while(temp.size()>0){
				visitNode(temp.get(0),queue.get(0));
				temp.remove(0);
			}
			visit[queue.get(0).getX()][queue.get(0).getY()]=1;
			queue.remove(0);
		}
		
		
		//Get path
		int currentValue=grid[desX][desY];
		queue.add(new Node(desX,desY));

		//File minimum cost Node in the neiborList
		int count=0;
		while(grid[queue.get(queue.size()-1).getX()][queue.get(queue.size()-1).getY()]!=0 && count<50){
			queue.add(findMinArray((findNeighBor(queue.get(queue.size()-1)))));
		}
		
		/*Print the path*/
		for(int i=queue.size()-1;i>0;i--){
			System.out.println("Move to x=" + queue.get(i).getX() + " y=" + queue.get(i).getY()+";");
		}
		
		printMapWithPath(sourceX,sourceY,desX,desY);
	}

	/** 
	* Function that display a map to the screen with given source and destination (without display path)
	* @param sourceX: x coordinator of source node
	* @param sourceY: y coordinator of source node
	* @param desX: x coordinator of destination node
	* @param desX: x coordinator of destination node
	*/
	public void printMap(int sourceX, int sourceY, int desX, int desY){
		for(int i=0;i<5;i++){
			for(int j=0;j<10;j++){
				if(i==sourceX && j==sourceY){
					System.out.print("S");
				} else if (i==desX && j==desY){
					System.out.print("D");
				} else if(grid[i][j]==1000){
					System.out.print("#");
				} else {
					System.out.print("0");
				}
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}
	
	/** 
	* Function that display a map to the screen with given source and destination (without display path)
	* @param sourceX: x coordinator of source node
	* @param sourceY: y coordinator of source node
	* @param desX: x coordinator of destination node
	* @param desX: x coordinator of destination node
	*/
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
				} else if(grid[i][j]==1000){
					System.out.print("#");
				} else {
					System.out.print("0");
				}
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}
	
	/**
	* Function that check if queue contain a node with coordinator x,y
	* @param x: coordinator x
	* @param y: coordinator y
	* @return boolean
	*	return true if queue contains this Node (x,y)
	*	return false if not
	*/
	public boolean isContainQueue(int x,int y){
		for(int i=queue.size()-1;i>0;i--){
			if(queue.get(i).getX() == x && queue.get(i).getY() == y){
				return true;
			}
		}
		return false;
	}
	
	/**
	* This function will add given Node A -which is neighbor of B- to queue if it has not been visit in the map 
	* @param Node A: given node	
	* @param Node B: originial node
	* 
	*/	
	public void visitNode(Node A, Node B) {
		if(grid[A.getX()][A.getY()]==0 && visit[A.getX()][A.getY()]==0){
			grid[A.getX()][A.getY()]=grid[B.getX()][B.getY()]+1;
			queue.add(A);
		}
	}
	
	/**
	* Find all neighbor nodes of a node and add it to a List
	* @param Node A: given a node to find neighbor	
	* @return a list neighbor of nodes of given node A
	*/
	public ArrayList<Node> findNeighBor(Node A){
		ArrayList<Node> queueNeigbor = new ArrayList<Node>();
		if(A.getX()==0){
					if(A.getY()==0){
						queueNeigbor.add(new Node(A.getX()+1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()+1));
					} else if (A.getY()==(MAX_Y -1)) {
						queueNeigbor.add(new Node(A.getX()+1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()-1));
					} else {
						queueNeigbor.add(new Node(A.getX()+1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()+1));
						queueNeigbor.add(new Node(A.getX(),A.getY()-1));
					}
				} else if (A.getX()==(MAX_X-1)) {
					if(A.getY()==0){
						queueNeigbor.add(new Node(A.getX()-1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()+1));
					} else if (A.getY()==(MAX_Y -1)) {
						queueNeigbor.add(new Node(A.getX()-1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()-1));
					} else {
						queueNeigbor.add(new Node(A.getX()-1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()+1));
						queueNeigbor.add(new Node(A.getX(),A.getY()-1));
					}
				} else {
					if(A.getY()==0){
						queueNeigbor.add(new Node(A.getX()+1,A.getY()));
						queueNeigbor.add(new Node(A.getX()-1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()+1));
					} else if (A.getY()==(MAX_Y -1)) {
						queueNeigbor.add(new Node(A.getX()+1,A.getY()));
						queueNeigbor.add(new Node(A.getX()-1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()-1));
					} else {
						queueNeigbor.add(new Node(A.getX()+1,A.getY()));
						queueNeigbor.add(new Node(A.getX()-1,A.getY()));
						queueNeigbor.add(new Node(A.getX(),A.getY()+1));
						queueNeigbor.add(new Node(A.getX(),A.getY()-1));
					}
				}
		return queueNeigbor;
	}
	
	/**
	* Function that finds a neighbor that has minimum cost 
	* @param NeiborList list of neighbor nodes
	* @return node
	*	return node that has minimum cost
	*/
	public Node findMinArray(ArrayList<Node> NeiborList){
		int min = grid[NeiborList.get(0).getX()][NeiborList.get(0).getY()];
		Node temp = NeiborList.get(0);
		for(int i=0;i<NeiborList.size();i++){
			if(grid[NeiborList.get(i).getX()][NeiborList.get(i).getY()]<=min){
				min=grid[NeiborList.get(i).getX()][NeiborList.get(i).getY()];
				temp= new Node(NeiborList.get(i).getX(),NeiborList.get(i).getY());
			}
		}
		return temp;
	}
}

