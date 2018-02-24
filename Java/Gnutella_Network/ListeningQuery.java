/*
 * @author Thien
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Model of Listening Query 
 */
public class ListeningQuery implements Runnable{

	/**
	 * Information of a Listening Query
	 */
	public DatagramSocket ListenSocket;
	public ServentData data;
	
	/**
	 * Information of message type in network
	 */
	public static final int QUERY = 2;
	public static final int QUERY_HIT = 3;
	public static final int GET = 4;
	public static final int PUSH = 5;
	
	/**
	 * Constructs a Listening Query based on given data
	 * @param dataInput, given data input
	 * @param ListenSocket, give socket information to listen to
	 */
	public ListeningQuery(ServentData dataInput,DatagramSocket ListenSocket){
		data = dataInput;
		this.ListenSocket = ListenSocket;	
	}
	
	
	/**
	 * Method that performs listening to query
	 * Depends on type of message, there will be corresponding action  
	 */
	@Override
	public void run() {
			while(true){
				byte[] messageRecieveByte = new byte[1024]; 
				DatagramPacket receivePing = new DatagramPacket(messageRecieveByte, messageRecieveByte.length);
				try {
					ListenSocket.receive(receivePing);
					byte []recieve = receivePing.getData();
				    String message = new String(receivePing.getData(),0, receivePing.getLength());
				    int MessageType = (int)recieve[0];
				    switch(MessageType){
				    	case ListeningQuery.QUERY: {
					    	ServentInfo previous = getServentFromPing(recieve);
					    	String filename = getFileFromRequest(recieve).trim();

					    	int TTL = getTTLFromRequest(recieve);
					    	if(data.isContainFile(filename)){/*Found file in local*/
					    		ServentInfo original = getServentFromPing(recieve);
					    		ServentInfo filehost = new ServentInfo(data.localPort,data.IPAddress,data.localPort);
								SearchFile searchFile = new SearchFile(data,previous,filename,original,filehost,SearchFile.QUERY_HIT);
								searchFile.TimeToLive = TTL - 1;
								Thread thread = new Thread(searchFile);
								thread.start();
					    	} else {
					    		/*Forward query*/
					    		if(TTL!=0){
							    	for(int i=0;i<data.serventList.size();i++){
										if(!data.serventList.get(i).IP.equals(previous.IP) || data.serventList.get(i).port!=previous.port){
											SearchFile searchFile = new SearchFile(data,data.serventList.get(i),filename,previous,null,SearchFile.REQUEST_FORWARD);
											Thread thread = new Thread(searchFile);
											thread.start();
										}
									}
					    		}
					    	}
				    	}
				    	break;
				    	case ListeningQuery.QUERY_HIT: {
					    	ServentInfo hostfile = getServentFromPing(recieve);
					    	ServentInfo origin = getOriginalServentFromHit(recieve);
					    	String filename = getFileFromQueryHit(recieve).trim();
					    	int TTL = getTTLFromRequest(recieve);
					    	if(origin.IP.equals(data.IPAddress) && origin.port == data.localPort){ //Original node
					    		SearchFile searchFile = new SearchFile(data,hostfile,filename,origin,hostfile,SearchFile.GET_REQUEST);
								Thread thread = new Thread(searchFile);
								thread.start();
					    	} else {
					    		/*Forward queryHit*/
					    		if(TTL!=0){
							    	for(int i=0;i<data.serventList.size();i++){
										if(!data.serventList.get(i).IP.equals(hostfile.IP) || data.serventList.get(i).port!=hostfile.port){
											SearchFile searchFile = new SearchFile(data,data.serventList.get(i),filename,origin,hostfile,SearchFile.HIT_FORWARD);
											Thread thread = new Thread(searchFile);
											thread.start();
										}
									}
					    		}
					    	}
					    }
				    	break;
				    	case GET: {
					    	ServentInfo requester = getServentFromPing(recieve);
					    	ServentInfo filehost = new ServentInfo(data.localPort,data.IPAddress,data.localPort);;
					    	String filename = getFileFromRequest(recieve).trim();
					    	SearchFile searchFile = new SearchFile(data,requester,filename,filehost,filehost,SearchFile.PUSH);
					    	searchFile.bufferFileContent = data.getFileContent(filename);
							Thread thread = new Thread(searchFile);
							thread.start();
				    	}
				    	break;
				    	case PUSH: {
					    	ServentInfo hostfile = getServentFromPing(recieve);
					    	int lengthOfFilename = getTTLFromRequest(recieve);
					    	String filename = getFileNameFromPush(recieve,lengthOfFilename).trim();
					    	String content = getFileContentFromPush(recieve,lengthOfFilename).trim();
					    	if(!data.filenames.contains(filename)){
						    	System.out.println("Recieve file: ");	
						    	System.out.println("From: " +  hostfile.IP + ":" + hostfile.port);
						    	System.out.println("Request file: " +  filename);
						    	System.out.println("File Content: " +  content);
								data.filenames.add(filename);
								data.files.put(filename, content);
							} 
				    	}
				    	break;
				    	default:
				    		break;
				    }
				} catch (IOException e) {
				//} catch (IOException e) {
					//DO nothing
				}
			}
	}
	
	/**
	 * Method that performs listen to a ping
	 * @param recieve, given byte array
	 * @param Servent, given Servent Information
	 */
	public void listenToPing(byte []recieve,ServentInfo s){
		    if(!data.IPAddress.equals(s.IP) || data.localPort!=s.port){
			    if(!data.isContainNode(s) && data.serventList.size()<NetworkConfig.Neighbors){
			    	this.data.serventList.add(s);
			    }
		    }
	}
	
	/**
	 * Method that gets information of Servent from ping package
	 * @param recieve, given message as byte array
	 * @return Servent information
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
	 * Method that gets information of Servent from hit package
	 * @param recieve, given message as byte array
	 * @return Servent information
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
	 * Method that gets file from request
	 * @param recieve, given message as byte array
	 * @return String file content
	 */
	public String getFileFromRequest(byte []recieve){
		String output = "";
		for(int i=13;i<recieve.length;i++){
			output  += (char)recieve[i];
		}

		return output;
	}

	/**
	 * Method that gets file from a hit
	 * @param recieve, given message as byte array
	 * @return String file content
	 */
	public String getFileFromQueryHit(byte []recieve){
		String output = "";
		for(int i=21;i<recieve.length;i++){
			output  += (char)recieve[i];
		}

		return output;
	}
	
	/**
	 * Method that gets file name from a push
	 * @param recieve, given message as byte array
	 * @param give length of of file
	 * @return String file name
	 */
	public String getFileNameFromPush(byte []recieve,int lengthOfFilename){
		String output = "";
		for(int i=13;i<13+lengthOfFilename;i++){
			output  += (char)recieve[i];
		}

		return output;
	}
	
	/**
	 * Method that gets file content from a push 
	 * @param recieve, given message as byte array
	 * @param give length of of file
	 * @return String file content
	 */
	public String getFileContentFromPush(byte []recieve,int lengthOfFilename){
		String output = "";
		for(int i=13+lengthOfFilename;i<recieve.length;i++){
			output  += (char)recieve[i];
		}

		return output;
	}
	
	/**
	 * Method that gets TTL from a request 
	 * @param recieve, given message as byte array
	 * @param give length of of file
	 * @return Integer of TTL
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
	 * Method that convert Byte to Integer 
	 * @param give byte array input
	 * @return Integer
	 */
	public int toInteger(byte []input)
	{
		int tmp = (input[0] << 24) + 
		          (input[1] << 16) + 
		          (input[2] << 8) + 
		          	input[3];
	   
		return tmp;
	}
	
}
