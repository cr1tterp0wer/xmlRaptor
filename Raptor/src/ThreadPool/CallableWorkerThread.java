package ThreadPool;

import java.util.concurrent.Callable;

public class CallableWorkerThread implements Callable<Integer> {

	protected int workerNumber;
	public CallableWorkerThread(int id){
		workerNumber = id;
	}
	
	
	//Use the call method much like 'run()'
	//returns an Integer that represents the tasks number
	@Override
	public Integer call() throws Exception {
		return(workerNumber);
	}
}
