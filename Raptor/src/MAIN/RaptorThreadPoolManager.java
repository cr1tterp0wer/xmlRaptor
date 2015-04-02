package MAIN;

import java.util.Scanner;

import RaptorThreadPool.*;
import ThreadPool.Signal;
import ThreadPool.ThreadPool;

public class RaptorThreadPoolManager {

	private final int        NUMBER_OF_WORKERS = 4;
	RaptorSignal             signal;
	private RaptorThreadPool threadPool;
	private SqlThread        sqlThread;
	private RaptorThread     xml01, xml02, xml03;
	private String[]         credentials;
	private final int        NUM_OF_CREDS = 7;
	private boolean          validInput   = false;
	
	public RaptorThreadPoolManager(){
		signal     = new RaptorSignal(NUMBER_OF_WORKERS);
		threadPool = new RaptorThreadPool(NUMBER_OF_WORKERS);
		credentials = new String[NUM_OF_CREDS];
	}
	
	public void init(){
		
		
		threadPool = new RaptorThreadPool();
		sqlThread  = new SqlThread(0, signal, this);
		inputCredentials();
		
		signal.setActiveThread(sqlThread);
		xml01      = new RaptorThread(1, signal);
		xml01.init(credentials[0]);
		xml02      = new RaptorThread(2, signal);
		xml02.init(credentials[0]);
		xml03      = new RaptorThread(3, signal);
		xml03.init(credentials[0]);
		
		threadPool.addWorker(sqlThread);
		threadPool.addWorker(xml01);
		threadPool.addWorker(xml02);
		threadPool.addWorker(xml03);
	}
	
	public void begin(){
		threadPool.submitAll();
	}
	public void end(){
		threadPool.shutdown();
	}
	
	public void inputCredentials(){
		
		
		while( !validInput){
			System.out.println("Would you like to launch xmlRaptor y/n");
			
			Scanner scan = new Scanner(System.in);
			String input = scan.nextLine();
			if(input.charAt(0) != 'y'){
				System.out.println("Exiting...Goodbye");
				System.exit(1);
			}
			
			System.out.println("please enter PATH(skip if DROPPING ALL):");
			credentials[0]          = scan.nextLine();
			System.out.println("please enter XML OBJECT(skip if DROPPING ALL):");
			credentials[1]          = scan.nextLine();
			System.out.println("please enter SERVER ADDRESS:");
			credentials[2]          = scan.nextLine();
			System.out.println("please enter PORT:");
			credentials[3]          = scan.nextLine();
			System.out.println("please enter USERNAME:");
			credentials[4]          = scan.nextLine();
			System.out.println("please enter PASS:");
			credentials[5]          = scan.nextLine();
			System.out.println("please enter DATABASENAME:");
			credentials[6]          = scan.nextLine();
			
			sqlThread.init(credentials);
		}
		
	}
	
	public void setValidInput(boolean b){validInput = b;}
	public void setRaptorSignal(Signal s){signal = (RaptorSignal)s;}
	public void setTheadPool(ThreadPool tp){threadPool = (RaptorThreadPool)tp;}
	
}
