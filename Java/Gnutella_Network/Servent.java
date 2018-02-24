
/**
 * @author Thien
 */

/**
 * Model of Servent
 */
public class Servent {
	
	/**
	 * Main method of that operate servent
	 * @param argv
	 * @throws Exception
	 */
	public static void main(String []argv) throws Exception{
		
		if(argv.length<3){
			System.out.println("Missing local IP,input IP, and port of known host in network");
			System.out.println("For example: java servent 127.0.0.1 127.0.0.1 1030");
			return;
		}
		
		ServentData data = new ServentData();
		ServentInfo initial = new ServentInfo(Integer.parseInt(argv[2]),argv[1],Integer.parseInt(argv[2]));
		data.serventList.add(initial);
		data.lastPong.put(initial,System.currentTimeMillis());
		data.IPAddress =  argv[0];
		data.printNode();
		
		
		Pong pong = new Pong(data);
		Thread theardListen = new Thread(pong);
		theardListen.start();
		data.localPort = pong.localPort;
		
		/*Only start ping if their localPort ready*/
		if(data.localPort!=0){
			Ping ping = new Ping(data);
			Thread theardAnnonce = new Thread(ping);
			theardAnnonce.start();
		}
		
		/*Start listening query*/
		ListeningQuery listeningQuery = new ListeningQuery(data,pong.ListenSocket);
		Thread theardListenQuery = new Thread(listeningQuery);
		theardListenQuery.start();
		
		if(data.localPort!=0){
			InputController input = new InputController(data);
			Thread theardInput = new Thread(input);
			theardInput.start();
			theardInput.join();
		}
		
	}
	
}
