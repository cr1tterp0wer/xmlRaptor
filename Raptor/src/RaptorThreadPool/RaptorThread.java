package RaptorThreadPool;

import java.util.Random;

import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class RaptorThread extends CallableWorkerThread{

	public RaptorThread(int id){
		super(id);
	}
	
	public RaptorThread(int workerNumber, Signal s){
		super(workerNumber, s);
	}
	
	@Override
	public Integer call(){
		
		testCall();
		setSignal();
		((RaptorSignal)signal).incrementFinishedThreads();
		
		return workerNumber;
	}
	
	public void setSignal(){
		signal.setHasDataToProcess(true);
		setNotification();
		
	}
	
	public void setNotification(){
		((RaptorSignal) signal).setNotify();
		
	}
	
	public void testCall(){
		Random r    = new Random();
		
		for(int i=0;i< 100;i++){
			int   delta = r.nextInt(1000);
			System.out.println("xml#"+ workerNumber +" :: " +i);
			try {
				Thread.sleep(delta);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}	
		((RaptorSignal)signal).incrementFinishedThreads();
	}
}
