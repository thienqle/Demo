package PathFinding;
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
public class MainActivity {
	public static void main(String []agrs){
		System.out.println("Simple Path finding");
		SimplePathFinding simplePathFinding = new SimplePathFinding();
		
		int startX = -1;
		int startY = -1;
		while((startX < 0 || startX > 5) || (startY < 0 || startY > 8) ){
			Scanner scanner = new Scanner(System.in);
			System.out.println("Choose your start point X: ");
			startX = scanner.nextInt();
			System.out.println("Choose your start point Y: ");
			startY = scanner.nextInt();
		}
		
		int DesX = -1;
		int DesY = -1;
		while((DesX < 0 || DesX > 5) || (DesY < 0 || DesY > 8) ){
			Scanner scanner = new Scanner(System.in);
			System.out.println("Choose your destination point X: ");
			DesX = scanner.nextInt();
			System.out.println("Choose your destination point Y: ");
			DesY = scanner.nextInt();
		}
		
		simplePathFinding.findPath(startX,startY,DesX,DesY);
	}
}
