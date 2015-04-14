package RaptorThreadPool;

import ThreadPool.CallableWorkerThread;
import ThreadPool.ThreadPool;


public class RaptorThreadPool extends ThreadPool{

	static final int NUMBER_OF_WORKERS = 4;
	private int capacity;
	
	//arg1 size of threadpool, arg2 the signal thread
	public RaptorThreadPool(int numWork, CallableWorkerThread t){
		super(numWork);
		capacity = numWork;
	}
	public RaptorThreadPool(int numWork){
		super(numWork);
	}
	public RaptorThreadPool(){
		super(NUMBER_OF_WORKERS);
	}
	
	public CallableWorkerThread getThreadFromID(int k){
		
		for(int i =0;i<workers.size(); i++){
			if(workers.get(i).getWorkerNumber() == k)
				return workers.get(i);
			else{
				try {throw new Exception("Cannot find workerThread with ID::" + k);
				} catch (Exception e) {}
			}
		}
		return null;
	}
	
	public void removeWorker(CallableWorkerThread worker){workers.remove(worker);}
	public int getCapacity(){return capacity;}
}
