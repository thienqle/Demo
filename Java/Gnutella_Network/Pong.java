/**
 * Student: Thien Le
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Iterator;

/**
 * Model of a Pong  
 */
public class Pong implements Runnable{

	/**
	 * Fields of pong  
	 */
	public DatagramSocket ListenSocket;
	public int localPort = 0;
	public String localIP = "localhost";
	public ServentData data;
	
	/**
	 * Constructs a Pong with given dataIput
	 * @param dataInput
	 */
	public Pong(ServentData dataInput){

		data = dataInput;
		/*Find avaliable port*/
		for(int i=NetworkConfig.MINIMUM;i<NetworkConfig.MAXIMUM;i++){

			try {
				ListenSocket = new DatagramSocket(i);
				localPort = ListenSocket.getLocalPort();
				data.localPort = ListenSocket.getLocalPort();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				continue;
			}
		}
	}
	
	/**
	 * Method that run pong process
	 * @param dataInput
	 */
	@Override
	public void run() {
		synchronized(this.data.serventList){
			while(true){
				byte[] messageRecieveByte = new byte[1024]; 
				DatagramPacket receivePing = new DatagramPacket(messageRecieveByte, messageRecieveByte.length);
				try {
					ListenSocket.receive(receivePing);
					byte []recieve = receivePing.getData();
				    String message = new String(receivePing.getData(),0, receivePing.getLength());
				    if((int)recieve[0] == 1){ /*Recieve ping*/
				    	ServentInfo s = getServentFromPing(recieve);
				    	listenToPing(recieve,s);
				    	int TTL = getTTLFromRequest(recieve);

				    	/*Pong back*/
				    	if(TTL!=0){
					    	ServentInfo content = new ServentInfo(data.localPort,data.IPAddress,data.localPort);
					    	Responding respondingPong = new Responding(s,content,"Pong");
					    	respondingPong.TimeToLive = TTL - 1;
							Thread theardPong = new Thread(respondingPong);
							theardPong.start();
							theardPong.join();
					    }

				    	/*Forward ping to peer*/
						if(TTL!=0){
							for(int i=0;i<data.serventList.size();i++){
								if(!data.serventList.get(i).IP.equals(s.IP) || data.serventList.get(i).port!=s.port){
									/* Forward start */	
									Responding respondingForwad = new Responding(data.serventList.get(i),s,"Forward ping");
									/* Sending */
									respondingForwad.TimeToLive = TTL - 1;
									Thread theardForwading = new Thread(respondingForwad);
									theardForwading.start();
								}
							}
						}
					
				    }
				} catch (IOException | InterruptedException e) {
				//} catch (IOException e) {
					//DO nothing
				}
			}
		}
	}
	
	/**
	 * Method that performs listen a ping
	 * @param recieve byte array
	 * @param Servent Info
	 */
	public void listenToPing(byte []recieve,ServentInfo s){
		    if(!data.IPAddress.equals(s.IP) || data.localPort!=s.port){
			    if(!data.isContainNode(s) && data.serventList.size()<NetworkConfig.Neighbors){
			    	this.data.serventList.add(s);
			    	data.lastPong.put(s,System.currentTimeMillis());
			    }
		    } 
		    long newPacketTime =System.currentTimeMillis(); 
		    udpateHashMap(s,newPacketTime);
		    //Concurrent Modify HashMap
		    Iterator<ServentInfo> iterator = data.lastPong.keySet().iterator();
		    while (iterator.hasNext()) {
		    	ServentInfo t = iterator.next();
		    	long currentPacketTime = System.currentTimeMillis();
		        if ((currentPacketTime-data.lastPong.get(t))/1000>=NetworkConfig.deadtime){
		        	System.out.println("One neighbor has left!" );
		        	//Concurrent Modify ArrayList
		        	Iterator<ServentInfo> iterator1 = data.serventList.iterator();
		        	while(iterator1.hasNext()){
		        		ServentInfo t1 = iterator1.next();
		        		if(t1.IP.equals(t.IP) && t1.port == t.port){
		        			if(data.serventList.size()>1){
		        				iterator1.remove();
		        			}
		        		}
					}
        	
		        	iterator.remove();
		        }
		    }
	}
	
	/**
	 * Method that perform listen a ping
	 * @param recieve byte array
	 * @param Servent Info
	 */
	public void udpateHashMap(ServentInfo a,long time){
		 Iterator<ServentInfo> iterator = data.lastPong.keySet().iterator();
		while (iterator.hasNext()) {
			 ServentInfo t = iterator.next();
			 if(t.IP.equals(a.IP) && t.port == a.port){
				 data.lastPong.put(t,time);
			 }
		 }
	}
	
	/**
	 * Method that performs get servent a ping
	 * @param recieve byte array
	 * @return Servent Info
	 */
	public ServentInfo getServentFromPing(byte []recieve){
		ServentInfo output = new ServentInfo();
		output.IP = (int) recieve[1] + "." + (int) recieve[2] + "." + (int) recieve[3] + "." + (int) recieve[4];
		
		/*Get port info*/
		byte []tmp = new byte[4];
		tmp[0] = recieve[5];
		tmp[1] = recieve[6];
		tmp[2] = recieve[7];
		tmp[3] = recieve[8];
		output.port = toInteger(tmp);
		output.ID = output.port;
		return output;
	}
	
	/**
	 * Method that performs get servent sent a hit
	 * @param recieve byte array
	 * @return Servent Info
	 */
	public ServentInfo getOriginalServentFromHit(byte []recieve){
		ServentInfo output = new ServentInfo();
		output.IP = (int) recieve[13] + "." + (int) recieve[14] + "." + (int) recieve[15] + "." + (int) recieve[16];
	
		/*Get port info*/
		byte []tmp = new byte[4];
		tmp[0] = recieve[17];
		tmp[1] = recieve[18];
		tmp[2] = recieve[19];
		tmp[3] = recieve[20];
		output.port = toInteger(tmp);
		output.ID = output.port;
		return output;
	}
	
	/**
	 * Method that performs get file from a request
	 * @param recieve byte array
	 * @return file name
	 */
	public String getFileFromRequest(byte []recieve){
		String output = "";
		for(int i=13;i<recieve.length;i++){
			output  += (char)recieve[i];
		}

		return output;
	}

	/**
	 * Method that performs get TTL from a request
	 * @param recieve byte array
	 * @return TTL, time to live
	 */
	public int getTTLFromRequest(byte []recieve){
		byte []output = new byte[4];
		output[0] = recieve[9];
		output[1] = recieve[10];
		output[2] = recieve[11];
		output[3] = recieve[12];

		return toInteger(output);
	}
	
	/**
	 * Method that converts byte to integer
	 * @param input, byte array
	 * @return Integer
	 */
	/*Byte to Integer*/
	public int toInteger(byte []input)
	{
		int tmp = (input[0] << 24) + 
		          (input[1] << 16) + 
		          (input[2] << 8) + 
		          	input[3];
	   
		return tmp;
	}
	
}
