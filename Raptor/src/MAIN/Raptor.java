package MAIN;

import java.util.ArrayList;
import java.util.List;

import Persistence.FileObject;
import RaptorThreadPool.CommandBuilder;
import SQL.SQLConnector;


public class Raptor {

	static RaptorThreadPoolManager Raptor;
	static SQLConnector conn;
	static CommandBuilder builder;
	
	public static void main(String[] args) {
		Raptor = new RaptorThreadPoolManager();
		Raptor.init();
		
		Raptor.begin();
		Raptor.end();

	    
//		String error = "Unknown column 'ClientName' in 'field list'";
//		String[] ar  = error.split("'");
//	    String column = ar[1];
//	    //we need table name
//	    System.out.println("column::" + column + " value::" + value);
//		for(String r : ar)
//		{
//		    System.out.println(r);
//		}
//	    
	    
	    
		
		
//	    connect(String path, String xmlObject, String serverAddress,
//                String port, String username,
//                String pass, String dbName){
//    	String[] keys = {"Type","Year","Received","Amount","Period","ID"};
//    	String[] values={"Complex","2012","today","20111","12th","23lkj-kj234-4234"};	    
//     	conn = new SQLConnector();    
//        conn.connect("./", "xmlObject", "localhost", "3306", "root", "critterpower", "test");
//        builder = new CommandBuilder(); 
//    	builder.setConnection(conn.getConnection());
//    	builder.buildInsertionStatement("filing", keys, values);
//    	builder.executeStatement();
	    
	    //1.)use xmlWorkers to store String table, String[]columns, String[]values
    	//2.)pass values to SQL thread
    	//2.)use Builder to build and execute precompiled statements
    	//3.)rinse repeat.

//	    ArrayList<String> alStrings = new ArrayList<String>();	    
//	    alStrings.add("one");
//	    alStrings.add("two");
//	    alStrings.add("three");
//	    alStrings.add("four");
//	    
//	    String[] strs = alStrings.toArray(new String[alStrings.size()]);
//	    
//	    for(int i =0;i<alStrings.size();i++)
//	        System.out.println(strs[i]);
	    
	}
}
