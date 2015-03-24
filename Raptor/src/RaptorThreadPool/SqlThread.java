package RaptorThreadPool;

import java.util.Random;

import ThreadPool.CallableWorkerThread;


public class SqlThread extends CallableWorkerThread{
	public SqlThread(int workerNumber){
		super(workerNumber);
	}
	public Integer call(){
		
		testCall();
		
		return workerNumber;
	}
	
	
	
	
	public void testCall(){
		Random r    = new Random();
		
		
		for(int i=0;i< 4000;i++){
			int   delta = r.nextInt(1000);
			System.out.println("SQL#"+ workerNumber +" :: " +i);
			try {
				Thread.sleep(delta);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	
}
