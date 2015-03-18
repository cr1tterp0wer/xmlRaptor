package RaptorSQL;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;


public class SQLController {
	
	Connection conn;
	PreparedStatement ps;
	Scanner    scan;
	Properties connProperties;
	
	
	public SQLController(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connect(String path, String xmlObject,String serverAddress,
						String port, String username,
						String pass, String dbName){
		
		assert username != null;
		assert pass     != null;
		
		connProperties = new Properties();
		connProperties.put("user", username);
		connProperties.put("password", pass);
		
		// Connect to MySQL
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://" + serverAddress
					                          +":"+ port + "/" + dbName, connProperties);
			System.out.println("Connected to database");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConnection(){
		try{
			conn.close();
			System.out.println("Disconnected from database");
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

}
