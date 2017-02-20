import java.util.Scanner;

/*
 * Thien Le
 */

/*
 * 
	0 0 0 * * * * 0 0 0  
	# # 0 * 0 # * 0 0 0  
	0 # # S 0 # * 0 0 0  
	0 0 0 # # # * 0 0 0  
	0 0 0 0 0 0 * * D 0  
 */
public class mainActivity {
	public static void main(String []agrs){
		System.out.println("Simple Path finding");
		SimplePathFinding aSimplePathFinding = new SimplePathFinding();
		
		int startX = -1;
		int startY = -1;
		while((startX < 0 || startX > 5) || (startY < 0 || startY > 8) ){
			Scanner aScanner = new Scanner(System.in);
			System.out.println("Choose your start point X: ");
			startX = aScanner.nextInt();
			System.out.println("Choose your start point Y: ");
			startY = aScanner.nextInt();
		}
		
		int DesX = -1;
		int DesY = -1;
		while((DesX < 0 || DesX > 5) || (DesY < 0 || DesY > 8) ){
			Scanner aScanner = new Scanner(System.in);
			System.out.println("Choose your start point X: ");
			DesX = aScanner.nextInt();
			System.out.println("Choose your start point Y: ");
			DesY = aScanner.nextInt();
		}
		
		//aSimplePathFinding.FindPath(0,0,4,8);
		aSimplePathFinding.FindPath(startX,startY,DesX,DesY);
	}
}
