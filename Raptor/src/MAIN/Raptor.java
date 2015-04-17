package MAIN;

import Persistence.FileObject;


public class Raptor {

	static RaptorThreadPoolManager Raptor;
	
	public static void main(String[] args) {
		Raptor = new RaptorThreadPoolManager();
		Raptor.init();
		
		Raptor.begin();
		Raptor.end();
		
	}
}
