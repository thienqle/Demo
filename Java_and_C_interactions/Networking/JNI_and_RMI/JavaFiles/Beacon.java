import java.io.Serializable;

/**
 * Model of a beacon that send from agent to manager 
 */
public class Beacon implements Serializable {
	
	/**
	 * Fields of Beacon information such as, beacon ID, and start up time of a system,
	 * and agent ID
	 */
	int ID; /* which is randomly generated at startup */
	int StartUpTime; /* the time when this client starts */
	String CmdAgentID;	/* the registry string of the CmdAgent */
	
	/**
	 * method that presents a beacon as string
	 * @return String, all information of a agent
	 */
	public String toString(){
		String output = ID + " " + StartUpTime + " " + CmdAgentID;
		return output;
	}
	
	/**
	 * Constructs of beacon with given ID, start up time of system, and agent ID  
	 * @param ID, ID of beacon
	 * @param StartUpTime, given start up time of system
	 * @param CmdAgentID, given agent ID
	 */
	public Beacon(int ID, int StartUpTime, String CmdAgentID){
		this.ID = ID;
		this.StartUpTime = StartUpTime;
		this.CmdAgentID = CmdAgentID;
	}
	
	/**
	 * Constructs of beacon with given Beacon information  
	 * @param beacon
	 */
	public Beacon(Beacon input){
		this.ID = input.ID;
		this.StartUpTime = input.StartUpTime;
		this.CmdAgentID = input.CmdAgentID;
	}
	
	/**
	 * method resets beacon value  
	 */
	public void reset(){
		this.ID = 0;
		this.StartUpTime = 0;
		this.CmdAgentID = "";
	}
}
