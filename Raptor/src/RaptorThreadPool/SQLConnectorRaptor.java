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
	XmlData                            tree;

	public SQLConnectorRaptor(){super();}
	public SQLConnectorRaptor(RaptorThreadPoolManager parent){
		super();
		parentPoolManager = parent;
		tables            = new ArrayList();
		createStatements();

		/// hashmap ("createTable", preparedStatement)
		/// hashmap.get("createTable").setString( someValue, someOtherValue, EvenMoreValues);
		/// 
		//		
		//		NAME::Registrant                                  # the table name
		//		  -->RegistrantCountry = USA                      # column::REGISTRANTCOUNTRY, value::USA
		//		  -->RegistrantName = Liebman & Associates, Inc.  # column::REGISTRANTNAME,    value::LIEBMAN & ASSOCIATES, INC.
		//		  -->Address = 1250 24th Street, NW, Suite 300    # column::ADDRESS,           value:: 1250 24th STREET, NW, SUITE 300


		//
		/// hashmap ("createTable", preparedStatement)
		/// hashmap.get("createTable").setString( someValue, someOtherValue, EvenMoreValues);
		/// hashmap.get("insertDataRegistrant").setString(  theCorrectTable, 

		//		
		//		String sqlCreate = "CREATE TABLE IF NOT EXISTS " + this.getTableName()
		//	            + "  (brand           VARCHAR(10),"
		//	            + "   year            INTEGER,"
		//	            + "   number          INTEGER,"
		//	            + "   value           INTEGER,"
		//	            + "   card_count           INTEGER,"
		//	            + "   player_name     VARCHAR(50),"
		//	            + "   player_position VARCHAR(20))";

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
		tree = xmlBlob;
		String insertStatement = "";
		
		
		for(int i=0;i<tree.size();i++){
			String table = tree.getElementAt(i).getElementName(); //table name
			insertStatement = "CREATE TABLE IF NOT EXISTS " + tree.getElementAt(i).getElementName()
					        + " (pid INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY(pid));";
		    
			//execute the update, create table if not exists
			try {
				System.out.println(insertStatement);
				this.executeUpdate(conn, insertStatement);
			} catch (SQLException e) {e.printStackTrace();}
			
			for( int k=0;k< tree.getDataList().size();k++){	
				 Iterator it =  tree.getElementAt(i).getElements().entrySet().iterator();
				 
				 while(it.hasNext()){
					Map.Entry pair = (Map.Entry)it.next();
					//System.out.println("  -->" + pair.getKey() + " = " + pair.getValue()); //key = column, value = value;
					
					
					//check if column exists
					try {
						DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
						ResultSet r;
						r = md.getColumns(null, null, "table_name", "column_name");
						
						//build the columns
						//get list of the values
						//get list of the keys
						// create insert statement after you gather them
						// execute insertion
						//!exists
						 if (r.next()) {
							 //check the length of value, make LONG,MEDIUM,SMALL-TEXT
							 System.out.println("column does not exist::Creating column" );
							 insertStatement = "ALTER TABLE " + table + " ADD COLUMN " + pair.getKey() + " LONGTEXT ;"; 
						     this.executeUpdate(conn, insertStatement);
						     System.out.println(insertStatement);
						 }
						 else{
							
						 }
						 
//						 insertStatement = "INSERT INTO " + table + "( " + pair.getKey() + ") VALUES ( '" 
//			                              + pair.getValue() + "' );";
//						 this.executeUpdate(conn, insertStatement);
					} catch (SQLException e) {e.printStackTrace();}
					
			           it.remove();
				}
				 
				 
				 
			}
		}
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

