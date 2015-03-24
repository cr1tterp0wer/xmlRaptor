package RaptorThreadPool;

import java.util.Random;

import ThreadPool.CallableWorkerThread;

public class RaptorThread extends CallableWorkerThread{

	public RaptorThread(int id){
		super(id);
	}
	
	@Override
	public Integer call(){
		
		
		testCall();
		
		return workerNumber;
	}
	
	public void testCall(){
		Random r    = new Random();
		
		for(int i=0;i< 1000;i++){
			int   delta = r.nextInt(1000);
			System.out.println("xml#"+ workerNumber +" :: " +i);
			try {
				Thread.sleep(delta);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
