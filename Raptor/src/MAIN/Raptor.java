package MAIN;

import Persistence.FileObject;


public class Raptor {

	static RaptorThreadPoolManager pool;
	
	public static void main(String[] args) {
		pool = new RaptorThreadPoolManager();
		pool.init();
		
		pool.begin();
		pool.end();
		
	}
}
