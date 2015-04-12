package RaptorThreadPool;

import java.util.Random;

import Persistence.FileObject;
import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class RaptorThread extends CallableWorkerThread{
	
	private FileObject raptorFile;
	private String     path;

	public RaptorThread(int id){
		super(id);
	}
	
	public RaptorThread(int workerNumber, Signal s){
		super(workerNumber, s);
		System.out.println(s.toString());
	}
	
	@Override
	public Integer call(){
		testCall();
		
		((RaptorSignal)signal).incrementAndPollThreads();
	
		System.out.println(((RaptorSignal)signal).getNumberFinishedThreads() + ""); 
		return workerNumber;
	}
	
	public void init(String _path){
		path = _path;
	}
	public void setSignal(boolean s){
		signal.setHasDataToProcess(s);
		setNotification();
	}
	
	public void setNotification(){
		((RaptorSignal) signal).setNotify();
	}
	
	public void testCall(){
		Random r    = new Random();
		
		for(int i=0;i< 10;i++){
			int   delta = r.nextInt(1000);
			//System.out.println("xml#"+ workerNumber +" :: " +i);
			try {
				Thread.sleep(delta);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}	
	}
}
