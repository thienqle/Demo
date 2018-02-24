/**
 * @author Thien
 */

/**
* Model that for Agent Information
*/		
public class AgentInfo {
	
	/**
	 * Fields that store information of agent
	 */
	public int startUpTime;
	public String localOS;
	public String cmdAgentID;
	public double lastBeacon;
	
	/**
	 * Constructs of agent information  
	 */
	public AgentInfo(){
		startUpTime = 0;
		lastBeacon = 0;
		localOS = "";
	}
	
	/**
	 * Constructs of agent information with given local time and agent ID
	 * @param localtime, given system time
	 * @param Agent ID, given agent ID from user
	 */
	public AgentInfo(int localtime,String cmdAgentID){
		this.startUpTime = localtime;
		localOS = "";
		this.cmdAgentID = cmdAgentID;
	}

	

}
