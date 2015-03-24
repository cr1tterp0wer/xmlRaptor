package RaptorThreadPool;
import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class RaptorSignal extends Signal{
	
	CallableWorkerThread worker;
	
	public RaptorSignal(CallableWorkerThread t){
		worker = t;
	}
	public RaptorSignal(){
		super();
	}
	
	public void setSignalTo(CallableWorkerThread t){worker = t;}
	public CallableWorkerThread getWorkerThread(){return worker;}
	
}
