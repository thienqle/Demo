 /**
  *  @author Thien
  */
import java.util.Scanner;

/**
 * Model that implements thread of display menu and get input from user
 */
public class InputController implements Runnable{

	/**
	 * Fields that stores data of servent
	 */
	public ServentData data;
	public String filename;
	
	/**
	 * Constructs InputController with given ServentData
	 * @param input, given ServentData
	 */
	public InputController(ServentData input){
		data = input;
		filename = "";
	}
	
	/**
	 * Method that displays menu and getting input from user
	 * @see input menu from console
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("This Servent is binding to port: " + data.localPort);
		data.printFilenames();
		while(true){
			Scanner scanner = new Scanner(System.in);
			System.out.println("\n======================================");
			System.out.println("Choose following action by input number");
			System.out.println("(1) View local information");
			System.out.println("(2) View local files list");
			System.out.println("(3) View file content");
			System.out.println("(4) Search for file");
			System.out.println("Enter your selection number: ");
			int select = scanner.nextInt();
			switch(select){
				case 1:
					System.out.println("This Servent is binding to port: " + data.localPort);
					data.printNode();
					break;
				case 2:
					data.printFilenames();
					break;
				case 3:
					viewFileContent();
					break;
				case 4:
					searchFile();
					break;
				default:
					System.out.println("This input is invalid");
					break;						
			}
			scanner.close();
		}
	
	}

	/**
	 * Method that search file on network file will be searched on local system first
	 * If the file does not exist, it will be search on the servent of network
	 */
	public void searchFile(){
		String filename = "";
		Scanner scanner = new Scanner(System.in);
		System.out.println("DISCLAMER: This search file process on implement using UDP connection; UDP is unreliable, the packets could loss sometime. "
							+ "If there no file has been found for a while. Please try again to search again.");
		System.out.println("Enter file name you want to search:");
		filename = scanner.nextLine();

		//Check file on local first
		if(data.isContainFile(filename)){
			System.out.println("File " + filename + " exsits in local, no further action require.");
			scanner.close();
			return;
		}
		for(int i=0;i<data.serventList.size();i++){
			SearchFile searchFile = new SearchFile(data,data.serventList.get(i),filename,null,null,SearchFile.REQUEST);
			Thread thread = new Thread(searchFile);
			thread.start();
		}
		scanner.close();
	}
	
	/**
	 * Method that displays a file content
	 */
	public void viewFileContent(){
		String filename = "";
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter file name you want to view content:");
		filename = scanner.nextLine();
		
		//Check file on local first
		if(data.isContainFile(filename)){
			System.out.println("File content:");
			System.out.println(data.getFileContent(filename));
		} else {
			System.out.println("File does not exist in local folder");
		}
		scanner.close();
	}
	
	
	
}
