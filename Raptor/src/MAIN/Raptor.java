package MAIN;

import Persistence.FileObject;
import RaptorThreadPool.XmlWorkerCommandBuilder;
import SQL.SQLConnector;


public class Raptor {

	static RaptorThreadPoolManager Raptor;
	static SQLConnector conn;
	static XmlWorkerCommandBuilder builder;
	
	public static void main(String[] args) {
//		Raptor = new RaptorThreadPoolManager();
//		Raptor.init();
//		
//		Raptor.begin();
//		Raptor.end();
//	    connect(String path, String xmlObject, String serverAddress,
//                String port, String username,
//                String pass, String dbName){
    	String[] keys = {"Type","Year","Received","Amount","Period","ID"};
    	String[] values={"Complex","2012","today","20111","12th","23lkj-kj234-4234"};	    
     	conn = new SQLConnector();    
        conn.connect("./", "xmlObject", "localhost", "3306", "root", "critterpower", "test");
        builder = new XmlWorkerCommandBuilder(); 
    	builder.setConnection(conn.getConnection());
    	builder.buildInsertionStatement("filing", keys, values);
    	builder.executeStatement();
	    
	    //1.)use xmlWorkers to store String table, String[]columns, String[]values
    	//2.)pass values to SQL thread
    	//2.)use Builder to build and execute precompiled statements
    	//3.)rinse repeat.
    	
	}
}
