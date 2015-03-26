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
		//addData();
		setSignal();
		
		return workerNumber;
	}
	
	public void setSignal(){
		signal.setHasDataToProcess(true);
		setNotification();
		
	}
	public void setNotification(){
		((RaptorSignal) signal).setNotify();
		System.out.println("setSignal()");
	}
	
	public void testCall(){
		Random r    = new Random();
		
		for(int i=0;i< 100;i++){
			int   delta = r.nextInt(10);
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
