/**
 * @author Thien
 * Model of configuration of network
 */
public class NetworkConfig {
	
	/**
	 * Fields that information of ports, timing of system
	 */
	public static final int MINIMUM = 1030;
	public static final int MAXIMUM = 1030+12;
	public static final int Neighbors = 3;
	public static final int delayDuration = 500;
	public static final int deadtime = 30;
	public static final int TTL = 10;

	/**
	 * method that converts given number to byte
	 * @param input, number
	 * @return byte 
	 */
	public static byte[] integerToByte(int input){
    	byte[] output = new byte[4];
    	output[0] = (byte)(input >> 24);
    	output[1] = (byte)(input >> 16);
    	output[2] = (byte)(input >> 8);
    	output[3] = (byte)(input & 0xFF) ;
    	return output;
    }
	
	/**
	 * method that converts given number to IP address
	 * @param input, IP address
	 * @return byte
	 */
	public static byte[] getByteIPAddress(String input){
    	byte[] output = new byte[4];
    	String []IP = input.split("\\.");
    	output[0] = (byte)Integer.parseInt(IP[0]);
    	output[1] = (byte)Integer.parseInt(IP[1]);
    	output[2] = (byte)Integer.parseInt(IP[2]);
    	output[3] = (byte)Integer.parseInt(IP[3]);
    	return output;
    }
	
	/**
	 * Method that adds two given bytes together 
	 * @param a1, given byte array
	 * @param a2, given byte array
	 * @return output, a bytes array concatenate together
	 */
	public static byte[] addTwoByteArray(byte[] a1,byte []a2){
		byte[] output = new byte[a1.length + a2.length];
		for(int i = 0;i<a1.length;i++){
			output[i] = a1[i];
		}
		int current = a1.length;
		for(int i = current;i<current + a2.length;i++){
			output[i] = a2[i-current];
		}
		return output;
	}
	
	/**
	 * Method that displays given byte array
	 * @see byte array 
	 */
    public static void printByteArray(byte[] input){
    	for(int i=0;i<input.length;i++){
    		System.out.print(input[i] + " ");
    	}
    	System.out.println("");
    }
}
