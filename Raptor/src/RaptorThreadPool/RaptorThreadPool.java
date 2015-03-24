package RaptorThreadPool;

import ThreadPool.CallableWorkerThread;
import ThreadPool.ThreadPool;


public class RaptorThreadPool extends ThreadPool{

	static final int NUMBER_OF_WORKERS = 4;
	RaptorSignal signal;
	
	//arg1 size of threadpool, arg2 the signal thread
	public RaptorThreadPool(int numWork, CallableWorkerThread t){
		super(numWork);
		signal = new RaptorSignal(t);
	}
	public RaptorThreadPool(int numWork){
		super(numWork);
	}
	public RaptorThreadPool(){
		super(NUMBER_OF_WORKERS);
	}
	
	public void setSignalTo(CallableWorkerThread t){signal = new RaptorSignal(t);}
	
	
	
}
