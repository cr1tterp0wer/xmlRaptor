package ThreadPool;

import java.util.concurrent.Callable;

public class CallableWorkerThread implements Callable<Integer> {

	protected Signal signal;
	protected int workerNumber;
	public CallableWorkerThread(int id){
		workerNumber = id;
	}
	public CallableWorkerThread(int id, Signal s){
		signal = s;
		workerNumber = id;
	}
	
	//Use the call method much like 'run()'
	//returns an Integer that represents the tasks number
	@Override
	public Integer call() throws Exception {
		return(workerNumber);
	}
	public int getWorkerNumber(){return workerNumber;}
}
