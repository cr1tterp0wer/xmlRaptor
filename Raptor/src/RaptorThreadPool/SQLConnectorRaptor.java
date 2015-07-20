package RaptorThreadPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.PreparedStatement;

import MAIN.RaptorThreadPoolManager;
import SQL.SQLConnector;

public class SQLConnectorRaptor extends SQLConnector{


	HashMap<String, PreparedStatement> statments;
	ArrayList<String>                  tables;
	RaptorThreadPoolManager            parentPoolManager;
	CommandBuilder                            tree;
	ArrayList<String> columns;
    ArrayList<String> values;
	
	public SQLConnectorRaptor(){super();}
	public SQLConnectorRaptor(RaptorThreadPoolManager parent){
		super();
		parentPoolManager = parent;
		tables            = new ArrayList();
		createStatements();
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

	
	
	//TODO: XML THREADS SHOULD CREATE STATEMENTS FOR THE SQL THREAD
	public void    injectXmlBlob(CommandBuilder xmlBlob){
//		tree = xmlBlob;
//		String insertStatement = "";
//		
//		for(int i=0;i<tree.size();i++){
//			String table = tree.getElementAt(i).getElementName(); //table name
//			insertStatement = "CREATE TABLE IF NOT EXISTS " + tree.getElementAt(i).getElementName()
//					        + " (pid INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY(pid));";
//
//			//Create table if not exists
//			try {
//				this.executeUpdate(conn, insertStatement);
//			} catch (SQLException e) {e.printStackTrace();}
//		}//end for(i)
	}
	
	
	//TODO:optimize this 
	private String insertStatementBuilder(ArrayList<String> c, ArrayList<String> v, String tableName){
	  
	    String statement = "INSERT INTO " + tableName + " ( ";
	    for(int i=0;i<c.size();i++){
	        statement += c.get(i);
	        if((c.size() - 1) >i )
	            statement+= " ,";
	        else
	            statement+= " ) VALUES ('";
	    }
	    for(int i=0;i<v.size();i++){
	        statement+= v.get(i);
	        if((v.size() - 1) >i )
	            statement+= "' ,'";
            else
                statement+= "' );";
	    }
	    
	    return statement;
	}
	
	public String  getParentObject(){ return this.credentials[1];}
	public boolean getIsConnected(){return this.isConnected;}
}

//
//private void printAllObjects(){
//	for(int i=0;i<tree.size();i++){
//		System.out.println(" NAME::"+tree.getElementAt(i).getElementName() );
//		for( int k=0;k< tree.getDataList().size();k++){	
//			 Iterator it =  tree.getElementAt(i).getElements().entrySet().iterator();
//			while(it.hasNext()){
//				Map.Entry pair = (Map.Entry)it.next();
//				System.out.println("  -->" + pair.getKey() + " = " + pair.getValue());
//		        it.remove();
//			}
//		}
//	}
//}



//
//for( int k=0;k< tree.getDataList().size();k++){ //inside specific table
//   Iterator it =  tree.getElementAt(i).getElements().entrySet().iterator();
//   //  columns = new ArrayList<String>();
//   //  values  = new ArrayList<String>();
//     
//   while(it.hasNext()){
//      Map.Entry pair = (Map.Entry)it.next();
//  
//      //check if column exists
//      try {
//          DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
//          ResultSet r;
//          r = md.getColumns(null, null, table, (String)pair.getKey());
//          
//          //!exists
//           if (!r.next()) {
//             //check the length of value, make LONG,MEDIUM,SMALL-TEXT
//                 System.out.println("column does not exist::Creating column->" + pair.getKey() + "::table->" + table );
//                // insertStatement = "ALTER TABLE " + table + " ADD COLUMN " + pair.getKey() + " LONGTEXT ;"; 
//                 
//               //  this.executeUpdate(conn, insertStatement);
//                 
//           }
//           //columns.add((String) pair.getKey());
//             //values.add((String)  pair.getValue());
//            
//      } catch (SQLException e) {e.printStackTrace();}
//      
//      //it.remove();
//  }//end while
     
     //insert to db
//   try {
//       // this.executeUpdate(conn, insertStatementBuilder(columns, values, table));
//    } catch (SQLException e) {e.printStackTrace();}  
//     
//     
//}//end for(k)
//
//
