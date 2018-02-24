class GetLocalInfo
{ 
	/**
	 * method getLocalTime thats implement in native code using C
	 */
    public native int getLocalTime();
    
    /**
	 * method getLocalLocalOS thats implement in native code using C
	 */
    public native String getLocalOS(); 

    static { 
    	System.loadLibrary("GetLocalInfo");
    } 

}