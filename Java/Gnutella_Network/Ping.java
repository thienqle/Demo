/**
 * @author Thien
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * Model of a Ping  
 */
public class Ping implements Runnable{

	/**
	 * Fields of ping  
	 */
	public ServentData data;
	public int TimeToLive;
	
	/**
	 * Constructs a Ping with given dataIput
	 * @param dataInput
	 */
	public Ping(ServentData dataInput){
		data = dataInput;
		data.localPort = dataInput.localPort;
		TimeToLive = NetworkConfig.TTL;
	}
	
	/**
	 * Method that run ping process
	 */
	@Override
	public void run() {

		while(true){
			ping();
			try {
				Thread.sleep(NetworkConfig.delayDuration);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method that perform a ping
	 */
	public void ping(){
		if(!data.serventList.isEmpty()){
			/*Loop through neigbor to send ping*/
				for(ServentInfo a : this.data.serventList){
					try {
						DatagramSocket pingSocket = new DatagramSocket();
						InetAddress ip = InetAddress.getByName(a.IP);
						/*Repair first byte data*/
						byte []type = new byte[1];
						type[0] = (byte)1;
						
						/*Repair IP address byte data*/
						byte []IPAddress = NetworkConfig.getByteIPAddress(data.IPAddress);
						
						/*Repair port data*/
						byte [] port = NetworkConfig.integerToByte(data.localPort);
						byte[] messagePingByte  = NetworkConfig.addTwoByteArray(type,NetworkConfig.addTwoByteArray(IPAddress,port));					
						//printByteArray(messagePingByte);
						
						/*Repair Time-to-Live N*/
						byte [] TTL = NetworkConfig.integerToByte(TimeToLive);
						messagePingByte  = NetworkConfig.addTwoByteArray(messagePingByte,TTL);
						
				        DatagramPacket pingPacket = new DatagramPacket(messagePingByte,messagePingByte.length,ip,a.port);
				        try {
							pingSocket.send(pingPacket);
							//System.out.println("Ping to " + a.port);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        pingSocket.close();
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						System.out.println("Failed to send message to " + a.port);
						//e.printStackTrace();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						System.out.println("Failed to send message to " + a.port);
						//e.printStackTrace();
					}
				} 
			} 
	}
	
	
}