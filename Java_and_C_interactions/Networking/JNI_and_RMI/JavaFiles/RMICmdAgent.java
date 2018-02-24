import java.rmi.RemoteException;

/**
 * Interface of a RMI on agent side, that require implemented class define deposit() 
 */
public interface RMICmdAgent extends java.rmi.Remote{
	public abstract String execute(String CmdID) throws RemoteException;

}
