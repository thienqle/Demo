import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Model of SearchFile thread 
 */
public class SearchFile implements Runnable{
	
	/**
	 * Field of a search file
	 */
	public ServentData data;
	public ServentInfo destination;
	public String filename;
	public String bufferFileContent;
	public ServentInfo original;
	public ServentInfo filehost;
	public int type;
	public int TimeToLive;
	public static final int REQUEST = 1;
	public static final int REQUEST_FORWARD = 2;
	public static final int QUERY_HIT = 3;
	public static final int HIT_FORWARD = 4;
	public static final int GET_REQUEST = 5;
	public static final int PUSH = 6;
	
	/**
	 * Constructs searchFile with given ServentInfo(s), and type of message
	 * @param dest, Destination Servent Information
	 * @param input, original Servent Information
	 * @param filehost, Servent that hosts given file
	 * @param filename, given filename as String
	 */
	public SearchFile(ServentData input,ServentInfo dest,String filename,ServentInfo original,ServentInfo filehost,int type){
		data = input;
		destination = dest;
		this.filename = filename;
		this.original = original;
		this.filehost = filehost;
		this.type = type;
		bufferFileContent = "";
		TimeToLive = NetworkConfig.TTL;
	}
	
	/**
	 * Creates a query (A, S, N, T ), where A is the requesting node, S search string,  T  Time-to-Live,N unique request ID
	 * @param destination Servent Information
	 * @param given filename as String 
	 */
	public void request(ServentInfo destination,String filename){
		
		DatagramSocket pongSocket;
		try {
			pongSocket = new DatagramSocket();
			InetAddress ip = InetAddress.getByName(destination.IP);
			/*Repair first byte data*/
			byte []type = new byte[1];
			type[0] = (byte)2;
			
			/*Repair IP address byte data*/
			byte []IPAddress = NetworkConfig.getByteIPAddress(data.IPAddress);
			
			/*Repair port data*/
			byte [] port = NetworkConfig.integerToByte(data.localPort);
			byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					
			
			/*Repair Time-to-Live N*/
			byte [] TTL = NetworkConfig.integerToByte(NetworkConfig.TTL);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,TTL);
			
			/*Repair Filename A*/
			byte []searchfile = filename.getBytes(); 
			messagePingByte = NetworkConfig.addTwoByteArray(messagePingByte,searchfile);
			
	        DatagramPacket requestPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ip,destination.port);
			pongSocket.send(requestPacket);
			
			//System.out.println("Send request: " + messagePingByte);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Creates a request Forward package
	 * @param original Servent Information
	 * @param destination Servent Information
	 * @param given filename as String 
	 */
	public void requestForward(ServentInfo original,ServentInfo destination,String filename){
		
		DatagramSocket pongSocket;
		try {
			pongSocket = new DatagramSocket();
			InetAddress ip = InetAddress.getByName(destination.IP);
			/*Repair first byte data*/
			byte []type = new byte[1];
			type[0] = (byte)2;
			
			/*Repair IP address byte data*/
			byte []IPAddress = NetworkConfig.getByteIPAddress(original.IP);
			
			/*Repair port data*/
			byte [] port = NetworkConfig.integerToByte(original.port);
			byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					
			
			/*Repair Time-to-Live N*/
			byte [] TTL = NetworkConfig.integerToByte(TimeToLive);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,TTL);
			
			/*Repair Filename A*/
			byte []searchfile = filename.getBytes(); 
			messagePingByte = NetworkConfig.addTwoByteArray(messagePingByte,searchfile);
			
	        DatagramPacket requestPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ip,destination.port);
			pongSocket.send(requestPacket);
			
			//System.out.println("Send request: " + messagePingByte);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a request query hit package
	 * @param original Servent Information
	 * @param destination Servent Information
	 * @param given filename as String 
	 */
	public void queryHit(ServentInfo original,ServentInfo destination,String filename){
		
		DatagramSocket pongSocket;
		try {
			pongSocket = new DatagramSocket();
			InetAddress ip = InetAddress.getByName(destination.IP);

			/*Repair first byte data*/
			byte []type = new byte[1];
			type[0] = (byte)3;
			
			
			/*Repair IP address byte data*/
			byte []IPAddress = NetworkConfig.getByteIPAddress(data.IPAddress);
			
			
			/*Repair port data*/
			byte [] port = NetworkConfig.integerToByte(data.localPort);
			byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					
			
			
			/*Repair Time-to-Live N*/
			byte [] TTL = NetworkConfig.integerToByte(TimeToLive);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,TTL);
			
			
			/*Repair IP address byte data*/
			byte []IPAddressOriginal = NetworkConfig.getByteIPAddress(original.IP);
			
			
			/*Repair port data*/
			byte [] portOriginal = NetworkConfig.integerToByte(original.port);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,NetworkConfig.addTwoByteArray(IPAddressOriginal,portOriginal));
			
			
			/*Repair Filename A*/
			byte []searchfile = filename.getBytes(); 
			messagePingByte = NetworkConfig.addTwoByteArray(messagePingByte,searchfile);
			
			DatagramPacket requestPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ip,destination.port);
			pongSocket.send(requestPacket);

			//System.out.println("Done");
			//System.out.println("Send request: " + messagePingByte);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Creates a request query hit forwared package
	 * @param original Servent Information
	 * @param destination Servent Information
	 * @param given filename as String 
	 */
	public void queryHitForward(ServentInfo original,ServentInfo filehost,ServentInfo destination,String filename){
		
		DatagramSocket pongSocket;
		try {
			pongSocket = new DatagramSocket();
			InetAddress ip = InetAddress.getByName(destination.IP);
			/*Repair first byte data*/
			byte []type = new byte[1];
			type[0] = (byte)3;
			
			/*Repair IP address byte data*/
			byte []IPAddress = NetworkConfig.getByteIPAddress(filehost.IP);
			
			/*Repair port data*/
			byte [] port = NetworkConfig.integerToByte(filehost.port);
			byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					
			
			/*Repair Time-to-Live N*/
			byte [] TTL = NetworkConfig.integerToByte(TimeToLive);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,TTL);
			
			/*Repair IP address byte data*/
			byte []IPAddressOriginal = NetworkConfig.getByteIPAddress(original.IP);
			
			/*Repair port data*/
			byte [] portOriginal = NetworkConfig.integerToByte(original.port);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,NetworkConfig.addTwoByteArray(IPAddressOriginal,portOriginal));
			
			/*Repair Filename A*/
			byte []searchfile = filename.getBytes(); 
			messagePingByte = NetworkConfig.addTwoByteArray(messagePingByte,searchfile);
			
	        DatagramPacket requestPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ip,destination.port);
			pongSocket.send(requestPacket);
			
			//System.out.println("Send request: " + messagePingByte);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a push
	 * @param original Servent Information
	 * @param destination Servent Information
	 * @param given filename as a String 
	 * @param given content as a String
	 */
	public void push(ServentInfo original,ServentInfo destination,String filename,String fileContent){
		
		DatagramSocket pongSocket;
		try {
			pongSocket = new DatagramSocket();
			InetAddress ipDest = InetAddress.getByName(destination.IP);
			/*Repair first byte data*/ //1byte
			byte []type = new byte[1];
			type[0] = (byte)5;
			
			/*Repair IP address byte data*///4bytes
			byte []IPAddress = NetworkConfig.getByteIPAddress(filehost.IP);
			
			/*Repair port data*/ //4bytes
			byte [] port = NetworkConfig.integerToByte(filehost.port);
			byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					
			
			/*Repair Time-to-Live N*///4bytes
			//System.out.println(filename.getBytes().length + " vs " + filename.length());
			byte [] LengthOfFilename = NetworkConfig.integerToByte(filename.getBytes().length);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,LengthOfFilename);
			
			/*Repair Filename A*/
			byte []searchfile = filename.getBytes(); 
			messagePingByte = NetworkConfig.addTwoByteArray(messagePingByte,searchfile);
			
			/*Repair Filename content*/
			byte []searchfileContent = fileContent.getBytes(); 
			messagePingByte = NetworkConfig.addTwoByteArray(messagePingByte,searchfileContent);
			
	        DatagramPacket requestPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ipDest,destination.port);
			pongSocket.send(requestPacket);
			
			//System.out.println("Send request: " + messagePingByte);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a Request
	 * @param original Servent Information
	 * @param destination Servent Information
	 * @param given filename as a String 
	 * @param given content as a String
	 */
	public void getRequest(ServentInfo original,ServentInfo destination,String filename){
		
		DatagramSocket pongSocket;
		try {
			pongSocket = new DatagramSocket();
			InetAddress ipDest = InetAddress.getByName(destination.IP);
			/*Repair first byte data*/
			byte []type = new byte[1];
			type[0] = (byte)4;
			
			/*Repair IP address byte data*/
			byte []IPAddress = NetworkConfig.getByteIPAddress(original.IP);
			
			/*Repair port data*/
			byte [] port = NetworkConfig.integerToByte(original.port);
			byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					
			
			/*Repair Time-to-Live N*/
			byte [] LengthOfFilename = NetworkConfig.integerToByte(filename.getBytes().length);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,LengthOfFilename);
			
			/*Repair Filename A*/
			byte []searchfile = filename.getBytes(); 
			messagePingByte = NetworkConfig.addTwoByteArray(messagePingByte,searchfile);
			
	        DatagramPacket requestPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ipDest,destination.port);
			pongSocket.send(requestPacket);
			
			//System.out.println("Send request: " + messagePingByte);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that performs search file in network
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		switch(type){
			case REQUEST:
				request(destination,filename);
				//System.out.println("Send request message to " + destination.IP + ":"+ destination.port);
				break;
			case REQUEST_FORWARD:
				requestForward(original,destination,filename);
				//System.out.println("Forward request message to " + destination.IP + ":"+ destination.port);
				break;
			case QUERY_HIT:
				queryHit(original,destination,filename);
				//System.out.println("Hit message to " + destination.IP + ":"+ destination.port);
				break;
			case HIT_FORWARD:
				queryHitForward(original,filehost,destination,filename);
				//System.out.println("Forward Hit message to " + destination.IP + ":"+ destination.port);
				break;
			case GET_REQUEST:
				getRequest(original,destination,filename);
				//System.out.println("Request file of " + destination.IP + ":"+ destination.port);
				break;
			case PUSH:
				push(filehost,destination,filename,this.bufferFileContent);
				//System.out.println("push file to " + destination.IP + ":"+ destination.port);
				break;
			default:
				break;
		}
	}
	
}
