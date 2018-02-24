/*
 * Thien Le
 */

import java.rmi.*; 

/**
 *  CmdAgent which perform rmi get information from remote object
 */
public class CmdAgent extends java.rmi.server.UnicastRemoteObject implements  RMICmdAgent{
	
	protected CmdAgent() throws RemoteException {
		//super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Function that gets local Info from CmdID
	 * @param CmdID
	 * @return String value of local OS information
	 */
	public String execute(String CmdID) throws RemoteException {
		if (CmdID.equals("GetLocalOS")){
			//Get value from JNI
			return new GetLocalInfo().getLocalOS();
		}
		return "";
	}
}
