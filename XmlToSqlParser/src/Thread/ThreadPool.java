package Thread;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import FileIO.FilePool;

public class ThreadPool {
	
	private ExecutorService xmlExecutor,sqlExecutor;
	private String[] args;	
	private FilePool filepool;
	private final int MAX_NUM_THREADS = 4;
	private SQLthread sqlThread;

	public ThreadPool(String[] argv, FilePool filepool){
		this.args = argv;		
		xmlExecutor = Executors.newFixedThreadPool(MAX_NUM_THREADS);
		this.filepool = filepool;
		sqlThread = new SQLthread(args);
	}
	
	public void run(){	
		while(filepool.getFileNameStack().size() > 0){
			Future<String[]> future = xmlExecutor.submit(new XMLThread(new File(filepool.getFileNameStack().pop()), args));
			sqlThread.addToFutures(future);
		}
	}
	public void shutdown(){
		xmlExecutor.shutdown();
		//create sql thread
		//sqlExecutor.submit(new SQLThread(FUTURELIST));
	
		sqlExecutor = Executors.newCachedThreadPool();
		sqlExecutor.submit(sqlThread);
		sqlExecutor.shutdown();	
	}
}