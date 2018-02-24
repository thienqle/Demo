/**
 * Interface of a RMI Beacon Listener, that require implemented class define deposit() 
 */
public interface RMIBeaconListener extends java.rmi.Remote {
	public int deposit(Beacon b)  throws java.rmi.RemoteException;; // put b to the list
}
