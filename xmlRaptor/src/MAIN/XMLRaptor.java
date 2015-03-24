package MAIN;

import RaptorThread.RaptorThreadHandler;
import RaptorSQL.SQLController;

public class XMLRaptor {
	
	static SQLController sqlController;
	static RaptorThreadHandler raptor;
	static long start;
	
	public static void main(String[] args) throws Exception {
		
		raptor = new RaptorThreadHandler();
		
		raptor.initDefaultInput();
		raptor.initDir();         //creates threads
		raptor.parseAndInject();
		System.gc();
		
	
    }
}
