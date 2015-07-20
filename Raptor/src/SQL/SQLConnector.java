package SQL;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;


public class SQLConnector {
    
    PreparedStatement ps;
    HashMap<String,PreparedStatement> preparedStatements;
    Scanner    scan;
    Properties connProperties;
    protected Connection conn;
    protected boolean  isConnected = false;
    protected String[] credentials;
    private  final int NUM_CREDS = 7;

    public SQLConnector(){
        credentials        = new String[NUM_CREDS];
        preparedStatements = new HashMap<String, PreparedStatement>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {e.printStackTrace();}
    }

    public void connect(String path, String xmlObject, String serverAddress,
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

            successfulConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorConnecting();
        }
    }

    public void closeConnection(){
        try{
            conn.close();
            System.out.println("Disconnected from database");
            isConnected = false;
        }catch(SQLException e){
            System.out.println("Unable to close connection!");
            e.printStackTrace();
        }
    }

    //Do not use for Insertions!
    public boolean executeUpdate(Connection conn, String command) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(command); 
            return true;
        } finally {
            // This will run whether we throw an exception or not
            if (stmt != null) { stmt.close(); }
        }
    }

    //Drop Single Table
    public void dropTable(String table){
        try{
            String createString = "DROP TABLE " + table;

            this.executeUpdate(conn,createString );
            System.out.println("Dropped table: " + table);

        }catch(SQLException e){
            System.out.println("ERROR: Could not drop the table");
            e.printStackTrace();
            return;
        }
    }

    //Drop All Tables
    public void dropAllTables(){
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

    public void createTable(String _name){
        // Create a table
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

                this.executeUpdate(conn, sql);
                System.out.println("Created a table:" + tableName);
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Could not create the table");
            e.printStackTrace();
            return;
        }
    }

    protected void errorConnecting(){    }
    protected void successfulConnection(){  }
    protected String[] getCreds(){return credentials;}
    public    Connection getConnection(){return conn;}

}

//
//package sqlPopper;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;
//
//import com.mysql.jdbc.DatabaseMetaData;
//
//public class SQLPopHandler {
//
//	private  String userName   = "";
//	private  String password   = "";
//	private  String serverName = "";
//	private  int    portNumber = 0;
//	private  String dbName     = "";
//	private  String tableName  = "newTable";
//	private Connection conn = null;
//	Properties connectionProps;
//	
//	public SQLPopHandler() throws SQLException{
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		connectionProps = new Properties();
//		
//		
//	}
//	
//	public boolean executeUpdate(Connection conn, String command) throws SQLException {
//	    Statement stmt = null;
//	    try {
//	        stmt = conn.createStatement();
//	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
//	        return true;
//	    } finally {
//
//	    	// This will run whether we throw an exception or not
//	        if (stmt != null) { stmt.close(); }
//	    }
//	}
//	
//	/**
//	 * Connect to MySQL and do some stuff.
//	 */
//	
//	public void connect(){
//		// Connect to MySQL
//		conn = this.getConnection();
//		connectionProps.put("user", userName);
//		connectionProps.put("password", password);
//		try {
//			conn = DriverManager.getConnection("jdbc:mysql://" + serverName +":"+ portNumber + "/" + dbName,
//					connectionProps);
//			System.out.println("Connected to database");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void createTable(String _name){
//		// Create a table
//		this.tableName = _name;
//		try {
//			DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
//			ResultSet rs = md.getTables(null, null, tableName, null);
//			if (rs.next()) {
//			  System.out.println("TABLE EXISTS");
//			}else{
//			    String createString =
//				        "CREATE TABLE " + this.tableName + " ( " +
//				        "qID INTEGER NOT NULL AUTO_INCREMENT, " +
//				        "PRIMARY KEY (qID))";
//				this.executeUpdate(conn, createString);
//				System.out.println("Created a table:" + this.tableName);
//			}
//	    } catch (SQLException e) {
//			System.out.println("ERROR: Could not create the table");
//			e.printStackTrace();
//			return;
//		}
//	}
//	
//	public void dropTable(String _tableName){
//		try{
//			String table = _tableName;
//			String createString = 
//					"DROP TABLE " + table;
//			
//			this.executeUpdate(conn,createString );
//			System.out.println("Dropped table: " + table);
//			
//		}catch(SQLException e){
//			System.out.println("ERROR: Could not drop the table");
//			e.printStackTrace();
//			return;
//			
//		}
//	}
//	
//	public void dropAllTables(){
//		try{
//			DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
//			ResultSet rs = md.getTables(null, null, "%", null);
//			while (rs.next()) {
//			  dropTable(rs.getString(3));
//			  System.out.println(rs.getString(3) + " dropped!");
//			}
//		}catch(SQLException e){
//			System.out.println("ERROR: Could not drop the table");
//			e.printStackTrace();
//			return;
//		}
//	}
//	
//	public void listTables(){
//		try {
//			DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
//			ResultSet rs = md.getTables(null, null, "%", null);
//			while (rs.next()) {
//			  System.out.println(rs.getString(3));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	//add a column
//	//ALTER TABLE contacts ADD email VARCHAR(60);
//	public void addColumn(String _tableName,String _columnName, String _dataType){
//		try{
//			String table = _tableName;
//			String createString = 
//					"ALTER TABLE " + table + " ADD " + _columnName + " " + _dataType;
//			DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
//			ResultSet rs = md.getColumns(null, null, table, _columnName);
//			 if (rs.next()) {
//				System.out.println("COLUMN EXISTS");
//			 }
//			 else{
//				 this.executeUpdate(conn,createString );
//				 System.out.println("Added column " + _columnName);
//			 }
//		}catch(SQLException e){
//			System.out.println("ERROR: Could not add the column:");
//			e.printStackTrace();
//			return;
//		}
//	}
//	
//	//insert data
//	// mysql_query("INSERT INTO phonebook(phone, firstname, lastname, address) VALUES('+1 123 456 7890', 'John', 'Doe', 'North America')"); 
//	public void insertData(String _table, String _column, String _values){
//		try{
//			String table = _table;
//			
//			//Check if column exists
//			DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
//			ResultSet rs = md.getColumns(null, null, _table, _column);
//			 if (!rs.next()) {
//			      System.out.println("Doesn't exist");
//			      addColumn(_table, _column, "VARCHAR(512)");
//			 }
//			
//		//	 String createString = "Update " + _table + " SET " + _column + " " + _values + " WHERE id=" + _id+";";
//			String createString = 
//					"INSERT INTO " + table + " ( " + _column + " ) VALUES ( " + _values + " );"; 
//			System.out.println(createString);
//			this.executeUpdate(conn, createString);
//			System.out.println("added item:" + _column);
//		}catch(SQLException e){
//			System.out.println("ERROR: COULD NOT INSERT ITEM");
//			e.printStackTrace();
//		}	
//	}  
//	
//	public void insertData(String _table, String[] _column, String[] _values){
//		try{
//			String table = _table;
//			DatabaseMetaData md = (DatabaseMetaData) conn.getMetaData();
//			ResultSet rs;
//				
//			for(int i=0;i<_column.length;i++)
//			{
//			    rs = md.getColumns(null, null, _table, _column[i]);
//			    if (!rs.next()) {
//				      System.out.println("Doesn't exist");
//				      addColumn(_table, _column[i], "VARCHAR(512)");
//				 }
//			}
//			String statement = SQLstatementBuilder.buildSingleInsertionStatements(_column, _values, _table);
//			System.out.println(statement);
//			this.executeUpdate(conn, statement);
//			System.out.println("added item:" + _column);
//		}catch(SQLException e){
//			System.out.println("ERROR: COULD NOT INSERT ITEM");
//			e.printStackTrace();
//		}	
//	}  
//	
//	public void closeConnection(){
//		try {
//			conn.close();
//			System.out.println("Disconnected from database");
//		} catch (SQLException e) {e.printStackTrace();}
//	}
//
//    public void   setServerName(String _server){ serverName = _server;}
//    public String getServerName(){return serverName;}
//	public String getUserName(){ return userName;}
//	public void   setUserName(String _user){ userName = _user;}
//	public String getPassword(){ return password;}
//	public void   setPassword(String _pass){ password = _pass;}
//	public int    getPortNumber(){return portNumber;}
//	public void   setPortNumber(int _p){portNumber = _p;}
//	public String getDbName(){return dbName;}
//	public void   setDbName(String _name){dbName = _name;}
//	public String getTableName(){return tableName;}
//	public void   setTableName(String _name){ tableName = _name;}
//	public Connection getConnection(){return conn;}
//	public void   setConnection(Connection _c){conn = _c;}
//	
//	
//}



//
///////////////////////////////////////////////////////////////////
//
//
//
//
//
//package sqlPopper;
//
//public class SQLstatementBuilder {
//
//	//BUILDS MULTIPLE STATEMENTS
//	public static String[] buildAllInsertionStatements(String[] _key, String[] _value, String _table){
//		String[] key   = _key;
//		String[] value = _value;
//		String   table = _table;
//		String[] statements = new String[key.length];
//		
//		appends(value, ", ");
//        appends(key,   ", ");
//        
//        for(int i=0;i<key.length;i++)
//        	statements[i] = "INSERT INTO " + table + " ( " + key[i] + " ) VALUES ( " + value[i] + " );"; 
//        
//		return statements;
//	}
//	
//	//CREATES ONE SINGLE INSERSTION
//	public static String buildSingleInsertionStatements(String[] _key, String[] _value, String _table){
//		String[] key   = _key;
//		String[] value = _value;
//		String   table = _table;
//		turnNullToNA(value);
//		turnNullToNA(key);
//		
//		String keys   = Join(key,   ",");
//		cutCharacterFromStrings(value, "[';#*@!]");
//		value[0] = "'"+value[0].trim();
//		value[value.length-1] = value[value.length-1].trim() + "'";
//		String values = Join(value, "','");
//
//        String statement = "INSERT INTO " + table + " ( " + keys + " ) VALUES ( " + values + " );";
//        
//		return statement;
//	}
//	public static void trimSize(String[] _value)
//	{
//		for(int i=0;i<_value.length;i++){
//			if(_value[i].length() > 512){
//				System.out.println(_value[i]);
//			    _value[i]=_value[i].substring(0,(_value[i].length()-512));
//			}
//		}
//	}
//	
//	public static void cutCharacterFromStrings(String[] _str, String _char){
//		for(int i=0;i<_str.length;i++)
//			_str[i]=_str[i].replaceAll(_char,"");
//	}
//	
//	public static String appends(String[] _a, String _delim){
//		String[] str  =  _a;
//		for(int i=0;i<str.length-1;i++)
//		{
//			str[i].concat(_delim);
//		}
//		return null;
//	}
//	
//	public static String Join(String[] _str, String _delim){
//		String joined ="";
//		String[] str  =  _str;
//		String delim  =  _delim;
//		
//		for(int i=0;i<str.length;i++)
//		{
//			joined+=str[i];
//			if(i<str.length-1)
//				joined+=delim;
//		}
//		return joined;
//	}
//	
//	public static void turnNullToNA(String[] _str){
//		for(int i=0;i<_str.length;i++)
//		{
//        	if(_str[i]==null || _str[i] == "")
//        		_str[i]= "N/A";
//		}
//	}
//	
//}
