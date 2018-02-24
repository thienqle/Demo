import java.rmi.*; 
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Thien
 * Manager class that has main method of Manager on network 
 */
public class Manager {
	
	/**
	 * Main method that does following algorithm
	 * Create Agent List
	 * Loop:
	 * RMI Binding to registration for BeaconLisnter process
	 * 		If new Agent appear:
	 * 			Update Agent List and message for new Beacon
	 * 			Send RMI request information of agent to Agent side using registrationCmdAgent's Beacon information
	 * 		Check each beacon from RMI for StartUpTime
	 * 			If StartUpTime change
	 * 				Message = This Agent just restarted
	 * 		Check duration of beacon
	 * 		Message Agent Dead if no Beacon receive in given period time
	 */
	public static void main(String args[]) {
		try{
			HashMap<Integer,AgentInfo> agentList = new HashMap<Integer,AgentInfo>(); 
			int currentID = 0;
			double MaximumDelayTime = 30000; //30 seconds
			ArrayList<Integer> deadAgent = new ArrayList<Integer>();
			
			RMIBeaconListenerImpl beaconListener = new RMIBeaconListenerImpl();
		
			RemoteRef location = beaconListener.getRef();
			System.out.println (location.remoteToString());
			String registry = "localhost"; // where the registry server locates
			
		   if (args.length >=1) {
			    registry = args[0];
		   }
			
		   String registration = "rmi://" + registry + "/RMIBeaconListener";
		   while(true){
			   Naming.rebind(registration, beaconListener);
			   if(beaconListener.getBeacon()==null){
				   System.out.println("\nNull beacon");
			   }
		   
			   if(!agentList.containsKey(beaconListener.getBeacon().ID) && beaconListener.getBeacon().ID!=0){
				   
				   AgentInfo temp = new AgentInfo(beaconListener.getBeacon().StartUpTime,beaconListener.getBeacon().CmdAgentID);
				   temp.lastBeacon = getCurrentTime();
				   agentList.put(beaconListener.getBeacon().ID,temp);
				   //beaconListener.reset();
				   System.out.println("\nAgent " + beaconListener.getBeacon().ID + " has been started");
				   //System.out.println("\nAgent " + beaconListener.getBeacon().toString());
				   
				   /*Start to rmi cmdAgent*/
				   CmdAgent cmdAgent = new CmdAgent();
				   Remote remoteService = Naming.lookup(beaconListener.getBeacon().CmdAgentID);
				   RMICmdAgent rmiCmdAgent = (RMICmdAgent) remoteService;
				   System.out.println("Agent " + beaconListener.getBeacon().ID);
				   System.out.println("OS name: " + rmiCmdAgent.execute("GetLocalOS"));
				   /*End of rmi cmdAgent*/
				   
			   } else {
				  if(!agentList.isEmpty()){
					  if(agentList.get(beaconListener.getBeacon().ID).startUpTime!= beaconListener.getBeacon().StartUpTime){
						   System.out.println("\nAgent " + beaconListener.getBeacon().ID + " has been restarted");
						   agentList.remove(beaconListener.getBeacon().ID);
						   AgentInfo temp = new AgentInfo(beaconListener.getBeacon().StartUpTime,beaconListener.getBeacon().CmdAgentID);
						   temp.lastBeacon = getCurrentTime();
						   agentList.put(beaconListener.getBeacon().ID,temp);
					  }	   
				  }
			   }
			   if(agentList.isEmpty()){
					//return;
				} else {
					for(int key : agentList.keySet()){
						if((getCurrentTime() - agentList.get(key).lastBeacon) > MaximumDelayTime){
							if(!deadAgent.contains(key)){
								deadAgent.add(key);
							}
						}
					}
					updateActiveList(agentList,deadAgent,beaconListener);
				}
		   }
		   
		} catch (Exception e){
			System.err.println ("Error - " + e);
		}
	}
	
	/**
	 * Method thats maintain the list of active agents on network
	 * @param agentList
	 * @param deadAgent
	 * @param beaconListener
	 */
	public static void updateActiveList(HashMap<Integer,AgentInfo> agentList,ArrayList<Integer> deadAgent,RMIBeaconListenerImpl beaconListener){
		for(int i=0;i<deadAgent.size();i++){
			if(agentList.containsKey(deadAgent.get(i))){
				System.out.println("\nAgent " + deadAgent.get(i) + " is dead");
				agentList.remove(deadAgent.get(i));
				beaconListener.reset();
			}
		}
		deadAgent.clear();
	}
	
	/**
	 * Method that get CurrentTime of system
	 * @return current time as double
	 */
	public static double getCurrentTime(){
		return System.currentTimeMillis();
	}
}
