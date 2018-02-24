import java.rmi.*; 

/**
 * @author Thien
 * Agent class that has main method of Manager on network 
 */
public class Agent {
	
	/**
	 * Main method that does following algorithm
	 * First, Using JNI to request localTime from C program
	 * Then, Loop following steps:
	 * 	Binding to registrationCmdAgent if manager waiting for request information from Manager
	 * 	Lookup to send Beacon to Manager via RMI
	 */
	public static void main(String args[]) throws RemoteException {
		int ID = 1;
		int MaximumDelayTime = 100;
		

		String registry = "localhost"; // the registry server’s IP
		if (args.length >=1) {
			registry = args[0];
		}
		
		if (args.length >=2) {
			registry = args[0];
			ID = Integer.parseInt(args[1]);
		}
		//Send Beacon part
		String registrationBeacon = "rmi://" + registry + "/RMIBeaconListener";
		
		//Receive CmdAgent part
		String registrationCmdAgent = "rmi://" + registry + "/RMICmdAgent" + ID;
		CmdAgent cmdAgent = new CmdAgent();
		
		while(true) {
			try {
			//Register rmi - Receive CmdAgent part
			Naming.rebind(registrationCmdAgent, cmdAgent);
				
			Remote remoteService = Naming.lookup(registrationBeacon);
			RMIBeaconListener beaconListener = (RMIBeaconListener) remoteService;
			
			/*Using JNI to get local time*/
			int time = new GetLocalInfo().getLocalTime();
			System.out.println(time);
			
			Beacon b = new Beacon(ID,time,registrationCmdAgent);
			beaconListener.deposit(b);

			System.out.println("update");
			} catch (NotBoundException nbe) {
				System.out.println ("No Beacon listener service available in registry!");
			} catch (RemoteException re) { System.out.println ("RMI - " + re);
			} catch (Exception e) { 
				System.out.println ("Error - " + e); 
			}
			try {
				Thread.sleep(MaximumDelayTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
