import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Model of responding 
 */
public class Responding implements Runnable{
	
	/**
	 * Field of a responding 
	 */
	public ServentInfo content;
	public ServentInfo destination;
	public String debugText;
	public int TimeToLive;
	
	/**
	 * Constructs responding with given ServentInfo(s), and type of message
	 * @param dest, Destination Servent Information
	 * @param input, original Servent Information
	 * @param String, type of package
	 */
	public Responding(ServentInfo dest,ServentInfo input,String type){
		content = input;
		destination = dest;
		debugText = type;
		TimeToLive = NetworkConfig.TTL;
	}

	/**
	 * Methods that performs responding
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DatagramSocket pongSocket;
		try {
			pongSocket = new DatagramSocket();
			InetAddress ip = InetAddress.getByName(destination.IP);
			/*Repair first byte data*/
			byte []type = new byte[1];
			type[0] = (byte)1;
			
			/*Repair IP address byte data*/
			byte []IPAddress = NetworkConfig.getByteIPAddress(content.IP);
			
			/*Repair port data*/
			byte [] port = NetworkConfig.integerToByte(content.port);
			byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					
			
			/*Repair Time-to-Live N*/
			byte [] TTL = NetworkConfig.integerToByte(TimeToLive);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,TTL);
			
	        DatagramPacket pingPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ip,destination.port);
			pongSocket.send(pingPacket);
	
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(NetworkConfig.delayDuration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Methods that performs sending
	 */
	public void sending(){
		DatagramSocket pongSocket;
		try {
			pongSocket = new DatagramSocket();
			InetAddress ip = InetAddress.getByName(destination.IP);
			/*Repair first byte data*/
			byte []type = new byte[1];
			type[0] = (byte)1;
			
			/*Repair IP address byte data*/
			byte []IPAddress = NetworkConfig.getByteIPAddress(content.IP);
			
			/*Repair port data*/
			byte [] port = NetworkConfig.integerToByte(content.port);
			byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					

			/*Repair Time-to-Live N*/
			byte [] TTL = NetworkConfig.integerToByte(TimeToLive);
			messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,TTL);
			
	        DatagramPacket pingPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ip,destination.port);
			pongSocket.send(pingPacket);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
