package RaptorThreadPool;

import java.util.Random;

import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;


public class SqlThread extends CallableWorkerThread{
	
	public SqlThread(int workerNumber){
		super(workerNumber);
	}
	
	public SqlThread(int workerNumber, Signal s){
		super(workerNumber, s);
	}
	
	public Integer call(){
		//wait until there is something to do
		while(!signal.hasDataToProcess()){
				System.out.println("SQL#"+ workerNumber +" :: " +" is waiting for signal");
				try {this.wait();} catch (InterruptedException e) {e.printStackTrace();}
		}
		
		System.out.println("SQL#"+ workerNumber +" :: READY!!!!!!!!!!!!!");
		//testCall();
		
		
		//should wait on init for the xmlObjects to populate the list<Futures> xmlobjects
		
		return workerNumber;
	}
	
	//just a test method to simulate work
	public void testCall(){
		Random r    = new Random();
		
		for(int i=0;i< 100;i++){
			int   delta = r.nextInt(100);
			System.out.println("SQL#"+ workerNumber +" :: " +i);
			try {
				Thread.sleep(delta);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
