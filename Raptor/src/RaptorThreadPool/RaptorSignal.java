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
					t.call();
					this.hasDataToProcess=false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
