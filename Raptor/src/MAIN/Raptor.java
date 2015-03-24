package MAIN;


public class Raptor {

	static RaptorThreadPoolManager pool;
	
	public static void main(String[] args) {
		pool = new RaptorThreadPoolManager();		
		pool.begin();
	}

}
