package ThreadPool;
import java.util.ArrayList;
import java.util.concurrent.*;



public class ThreadPool {

    private ArrayList<CallableWorkerThread> workers; //callable returns a Future obj
    private Future futures[];
    private ExecutorService executor;
    
	public ThreadPool(int numWork){
		
		futures        = new Future[numWork];
		workers        = new ArrayList<CallableWorkerThread>(numWork);
		
		executor = Executors.newCachedThreadPool();
		
	}

	public Future[] submitAll(){
		for(int i =0;i<workers.size();i++){executor.submit(workers.get(i));}
		return null;
	}
	
	
	public Future<?> submit(CallableWorkerThread w){return executor.submit(w);}

	public void addWorker(CallableWorkerThread wt){workers.add(wt);}
	public void removeWorkerAt(int i){ workers.remove(i);}
	public void shutdown(){executor.shutdown();}
	
	public int getNumWorkers()    { return workers.size();     }
	public ArrayList<CallableWorkerThread> getWorkerThread(){ return workers;}	
	
}
