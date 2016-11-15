package Thread;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import FileIO.FilePool;

public class ThreadPool {
	
	private ExecutorService xmlExecutor,sqlExecutor;
	private String[] credentials;	
	private FilePool filepool;
	private final int MAX_NUM_THREADS = 8;
	private SQLthread sqlThread;

	public ThreadPool(String[] creds, FilePool filepool){
		this.credentials = creds;		
		xmlExecutor = Executors.newFixedThreadPool(MAX_NUM_THREADS);
		this.filepool = filepool;
		sqlThread = new SQLthread(credentials);
	}
	
	public void run(){	
		
		Future<String[]> future;
		sqlExecutor = Executors.newFixedThreadPool(MAX_NUM_THREADS);
		while(filepool.getFileNameStack().size() > 0){
			
			future = xmlExecutor.submit(new XMLThread(new File(filepool.getFileNameStack().pop()), credentials) );
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				sqlThread.addToFutures(future);
				sqlExecutor.submit(sqlThread);
			}	
		}
	}
	
	public void shutdown(){
		xmlExecutor.shutdown();
		//create sql thread
		//sqlExecutor.submit(new SQLThread(FUTURELIST));
	
		
		sqlExecutor.shutdown();	
	}
}