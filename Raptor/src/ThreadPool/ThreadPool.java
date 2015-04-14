package ThreadPool;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.*;



public class ThreadPool {

    protected ArrayList<CallableWorkerThread> workers; //callable returns a Future obj
    private LinkedList<Future> futures;
    protected ExecutorService executor;
    
	public ThreadPool(int numWork){
		
		futures = new LinkedList<Future>();
		workers = new ArrayList<CallableWorkerThread>(numWork);
		executor   = Executors.newCachedThreadPool();
	}

	public LinkedList<Future> submitAll(){
		for(int i =0;i<workers.size();i++){
		   futures.add( executor.submit(workers.get(i))); //submit the workers into the executor
		}
		return futures;
	}

	public void submit(CallableWorkerThread w){
		workers.add(w);
		futures.add(executor.submit(w));
	}

	public CallableWorkerThread addWorker(CallableWorkerThread wt){workers.add(wt);return wt;}
	public void removeWorkerAt(int i){ workers.remove(i);}
	public void shutdown(){executor.shutdown();}
	public ExecutorService getExecutor(){return executor;}
	
	public int getNumWorkers()    { return workers.size();     }
	public ArrayList<CallableWorkerThread> getWorkers(){ return workers;}
	public LinkedList<Future> getFutures(){return futures;}
	
}
