/**
 * Model of Servent Info
 */
public class ServentInfo {
	
	/**
	 * Fields of Servent Info
	 */
	public int ID;
	public String IP;
	public int port;
	
	/**
	 * Constructs Servent Info
	 */
	public ServentInfo(){
		ID = 0;
		IP = "";
		port = 0;
	}
	
	/**
	 * Constructs Servent Info based on given ID, IP, and port
	 * @param ID of servent
	 * @param IP address of servent
	 * @param port number of servent
	 */
	public ServentInfo(int ID, String IP, int port){
		this.ID = ID;
		this.IP = IP;
		this.port = port;
	}
}
