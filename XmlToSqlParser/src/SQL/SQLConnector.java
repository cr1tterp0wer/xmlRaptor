package SQL;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class SQLConnector {

	private static Properties connProperties;
	private static Connection conn;
	private static boolean    isConnected = false;
	private static String[]   credentials;
	private static final int  NUM_CREDS = 7;

	public SQLConnector(){
		credentials        = new String[NUM_CREDS];

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	}

	//Overloaded connect wrapper
	public static void connect(String[] args){
		connect(args[0],args[1],args[2],args[3],args[4],args[5],args[6]);
	}
	
	public static void connect(String path, String xmlObject, String serverAddress,
			                                   String port, String username,
			                                   String pass, String dbName){
		credentials [0] = path;
		credentials [1] = xmlObject;
		credentials [2] = serverAddress;
		credentials [3] = port;
		credentials [4] = username;
		credentials [5] = pass;
		credentials [6] = dbName;

		connProperties = new Properties();
		connProperties.put("user", username);
		connProperties.put("password", pass);
		System.out.println("Connecting to '" + dbName + "'...");
		// Connect to MySQL
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + serverAddress
					+":"+ port + "/" + dbName, connProperties);
			
			if(!conn.isClosed())
			  successfulConnection();
			else
			  unsuccessfulConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorConnecting();
		}
	}

	public static void closeConnection(){
		try{
			conn.close();
		}catch(SQLException e){
			System.out.println("Unable to close connection!");
			e.printStackTrace();
		}
		
		try{
		if(conn.isClosed())
			unsuccessfulConnection();
		}
	    catch(Exception e){e.printStackTrace();}	
	}

	//Drop Single Table
	public static void dropTable(String table){
		if(isConnected()){
			try{
				String createString = "DROP TABLE " + table;

				executeUpdate(createString );
				System.out.println("Dropped table: " + table);

			}catch(SQLException e){
				System.out.println("ERROR: Could not drop the table");
				e.printStackTrace();
				return;
			}
		}
	}

	//Drop All Tables
	public static void dropAllTables(){
		if(isConnected()){
			try{
				DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
				ResultSet rs        = md.getTables(null, null, "%", null);
				while (rs.next()) {
					dropTable(rs.getString(3));
					System.out.println(rs.getString(3) + " dropped!");
				}
			}catch(SQLException e){
				System.out.println("ERROR: Could not drop the table");
				e.printStackTrace();
				return;
			}
		}
	}

	public static void createTable(String _name){
		// Create a table
		if(isConnected()){
			String tableName = _name;
			try {
				DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
				ResultSet rs = md.getTables(null, null, tableName, null);
				if (rs.next()) {
					System.out.println("TABLE EXISTS");
				}else{
					String sql =   "CREATE TABLE " +
							tableName +
							"(id INTEGER not NULL AUTO_INCREMENT, " +
							" PRIMARY KEY ( id ))"; 

					executeUpdate( sql );
					System.out.println("Created a table:" + tableName);
				}
			} catch (SQLException e) {
				System.out.println("ERROR: Could not create the table");
				e.printStackTrace();
				return;
			}
		}
	}
	
	public static boolean executeUpdate(String command) throws SQLException {
		if(isConnected()){
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(command); 
				return true;
			}catch(SQLException e1){
				int code = e1.getErrorCode();
				switch(code){
				case 1146 : createTable(command.split(" ")[2]);executeUpdate(command);break;
				}
			}
			finally {
				// This will run whether we throw an exception or not
				if (stmt != null) { stmt.close(); }
			}
		}
		return false;
	}
	
	

	private static void errorConnecting(){    }
	private static void successfulConnection(){ 
		isConnected = true;
		System.out.println("CONNECTED TO DATABASE!**************************");
	}
	private static void unsuccessfulConnection(){
		isConnected = false;
		System.out.println("DISCONNECTED FROM DATABASE!**************************");
	}
	
	public Connection getConn(){return conn;}
	public static String[] getCreds(){return credentials;}
	public static boolean isConnected(){return isConnected;}

}
