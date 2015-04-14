package ThreadPool;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.*;



public class ThreadPool {

    protected ArrayList<CallableWorkerThread> workers; //callable returns a Future obj
//    private Future futures[];
    private Stack<Future> futures;
    private ExecutorService executor;
    
	public ThreadPool(int numWork){
		
		futures = new Stack<Future>();
		workers = new ArrayList<CallableWorkerThread>(numWork);
		executor = Executors.newFixedThreadPool(numWork);
		
	}

	public Stack<Future> submitAll(){
		for(int i =0;i<workers.size();i++){
		   futures.add( executor.submit(workers.get(i))); //submit the workers into the executor
		}
		return futures;
	}

	public Future<?> submit(CallableWorkerThread w){return executor.submit(w);}

	public CallableWorkerThread addWorker(CallableWorkerThread wt){workers.add(wt); return wt;}
	public void removeWorkerAt(int i){ workers.remove(i);}
	public void shutdown(){executor.shutdown();}
	public ExecutorService getExecutor(){return executor;}
	
	public int getNumWorkers()    { return workers.size();     }
	public ArrayList<CallableWorkerThread> getWorkers(){ return workers;}	
	
}
