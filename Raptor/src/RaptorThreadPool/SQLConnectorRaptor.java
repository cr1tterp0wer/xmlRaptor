package RaptorThreadPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.PreparedStatement;

import MAIN.RaptorThreadPoolManager;
import SQL.SQLConnector;

public class SQLConnectorRaptor extends SQLConnector{
	
    
    HashMap<String, PreparedStatement> statments;
    ArrayList<String>                  tables;
    
    
	RaptorThreadPoolManager parentPoolManager;
	
	public SQLConnectorRaptor(){super();}
	public SQLConnectorRaptor(RaptorThreadPoolManager parent){
		super();
		parentPoolManager = parent;
		tables            = new ArrayList();
		createStatements();
		
		/// hashmap ("createTable", preparedStatement)
		/// hashmap.get("createTable").setString( someValue, someOtherValue, EvenMoreValues);
	    /// 
		
	}
	
	private void createStatements(){
	    
	    
	    
	}
	
	@Override
	protected void errorConnecting(){
	    isConnected = false;
		System.out.println("THERE WAS AN ERROR CONNECTING");
		parentPoolManager.setValidInput(false);
	}
	@Override
	protected void successfulConnection(){
	    System.out.println("SUCCESS::Connected to db!");
        isConnected = true;
		parentPoolManager.setValidInput(true);
	}
	
	public void    injectXmlBlob(XmlData xmlBlob){
	    
	    
	}
	
	public String  getParentObject(){ return this.credentials[1];}
	public boolean getIsConnected(){return this.isConnected;}
	    
	
	
	
}

//public void createTable(String _name){
//    // Create a table
//    String tableName = _name;
//    try {
//        DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
//        ResultSet rs = md.getTables(null, null, tableName, null);
//        if (rs.next()) {
//            System.out.println("TABLE EXISTS");
//        }else{
//            String sql =  "CREATE TABLE " +
//                    tableName +
//                    "(id INTEGER not NULL AUTO_INCREMENT, " +
//                    " PRIMARY KEY ( id ))"; 
//
//            this.executeUpdate(conn, sql);
//            System.out.println("Created a table:" + tableName);
//        }
//    } catch (SQLException e) {
//        System.out.println("ERROR: Could not create the table");
//        e.printStackTrace();
//        return;
//    }
//}
