package RaptorThreadPool;

import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class RaptorSignal extends Signal{
	
	private CallableWorkerThread t;
	
	public RaptorSignal(){
		super();
	}
	
	public void setActiveThread(CallableWorkerThread cwt){t = cwt;}
	public CallableWorkerThread getActiveThread(){return t;}
	public void setNotify(){
		if(this.hasDataToProcess){
				try {
					t.notify();		
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.hasDataToProcess=false;
				
		}
	}
}
