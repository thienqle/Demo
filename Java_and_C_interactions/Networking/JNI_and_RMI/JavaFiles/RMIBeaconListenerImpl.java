/**
 * @author: Thien Le
 */

/**
 * Model that implement of RMIBeaconListener
 */
public class RMIBeaconListenerImpl extends java.rmi.server.UnicastRemoteObject implements RMIBeaconListener{

	/**
	 * Field of RMIBeaconListenerImpl
	 */
	Beacon beacon;
	
	/** 
	 * constructs a beacon Listener with default beacon
	 */
	public RMIBeaconListenerImpl()  throws java.rmi.RemoteException{
		beacon = new Beacon(0,0,"");
	}
	
	/** 
	 * constructs a beacon Listener with given ID, statup time, and CmdAgentID
	 * @param ID
	 * @param starup time
	 * @param CmdAgentID
	 */
	public void CreatBeacon(int ID, int StartUpTime, String CmdAgentID)  throws java.rmi.RemoteException {
		beacon.ID = ID;
		beacon.StartUpTime = StartUpTime;
		beacon.CmdAgentID = CmdAgentID;
	}
	
	/** 
	 * set value for beacon from given message
	 * @param Beacon, given beacon message
	 */
	public void setBeacon(Beacon message)  throws java.rmi.RemoteException {
		beacon.ID = message.ID;
		beacon.StartUpTime = message.StartUpTime;
		beacon.CmdAgentID = message.CmdAgentID;
	}
	
	
	/** 
	 * set value for beacon from given message
	 * @param Beacon, given beacon message
	 */
	public Beacon getBeacon()  throws java.rmi.RemoteException {
		return this.beacon;
	}
	
	
	/** 
	 * method that sends beacon from agent to manager
	 * @param Beacon, given beacon information b
	 */
	public int deposit(Beacon b)  throws java.rmi.RemoteException {
		beacon.ID = b.ID;
		beacon.StartUpTime = b.StartUpTime;
		beacon.CmdAgentID = b.CmdAgentID;
		return 1;
	}
	
	/** 
	 * method that reset beacon information to default
	 */
	public void reset(){
		beacon.ID =0;
		beacon.StartUpTime = 0;
		beacon.CmdAgentID = "";
	}
	
	/** 
	 * method that presents RMIBeaconListenerImpl as a String
	 */
	public String toString(){
		return beacon.ID + "; " + beacon.StartUpTime + "; "  + beacon.CmdAgentID;  
	}
}
