package SQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;

public class SQLstatementAction {
	
	private static HashMap<String,String> psMap = new HashMap<String,String>();
	
	public static String buildStatement(String tableName, String ID, ArrayList<String> insertFirstHalf, ArrayList<String> insertSecondHalf){
		String[]  firstHalf  = new String[insertFirstHalf.size()];
		String[]  secondHalf = new String[insertSecondHalf.size()];
		String    table  = tableName;
	
		firstHalf  = insertFirstHalf.toArray(firstHalf);
		secondHalf = insertSecondHalf.toArray(secondHalf);
		final int LENGTH = secondHalf.length;
		
		String insert01 = "INSERT INTO " + tableName + " (";
		String insert02 = " VALUE (";
		
		
		for(int i=0;i<LENGTH;i++)
		{
			insert01 += firstHalf[i] + ",";
            insert02 += "'"+ secondHalf[i] + "',";
		}
		insert01 += "qID)";
        insert02 += "'" + ID + "');";
		
		return insert01 + insert02;
	}
	
	public static String[] sanitize(String[] dirtyValues){

		String tempValue = "";
		String[] newValues = new String[dirtyValues.length];
		ArrayList<String> values = new ArrayList<String>();
		if(dirtyValues.length >0){ 
			for(int i=0;i<dirtyValues.length;i++){
				if(dirtyValues[i] == null)
					continue;
				tempValue = dirtyValues[i];
				tempValue =  tempValue.replaceAll(",","").replaceAll("'", "^").replaceAll("\\\\","-");
				tempValue = tempValue.replaceAll("\\r\\n|\\r|\\n", "-").replaceAll(",","-").replaceAll(";",".");

				values.add(tempValue);   
			}
			newValues = values.toArray(new String[values.size()]);
		}
		return newValues;
	}
	
	public static void prepareMyStatement(String raw, SQLConnector c){
		String tableName = raw.split(" ")[2];
		int length=0;
		String[] values = new String[0];
		String   pStatement="";
		String   insert01 = "";
		String   insert02 = "";
		String[] temp = raw.split("\\) VALUE \\(" );
	
		//target the table name eg. INSERT INTO EMPLOYEE*
		if(!(psMap.containsKey(tableName) )){
			
			temp = raw.split("\\) VALUE \\(" );
			insert01 = temp[0] + ") ";
			values = temp[1].replace(");","").split(",");		
			insert02 += " VALUE (";
			
			for(int i =0;i<values.length;i++){
			  insert02 += "?,";
			}	
			insert02 = insert02.substring(0, insert02.length()-1) +");";
			
			psMap.put(tableName,  insert01+insert02);
		}
		else{
			//IF psMap contains the string already
			//get temp vals ('age','name','date,...)
            values = temp[1].replace(");","").split(",");	
		}
		
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) c.getConn().prepareStatement(psMap.get(tableName));
			for(int i =0;i<values.length;i++){
				pstmt.setString(i+1, values[i]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			int code = e.getErrorCode();
			switch(code){
			case 1146 : c.createTable(psMap.get(tableName).split(" ")[2]);
			    try {
			    	pstmt = (PreparedStatement) c.getConn().prepareStatement(psMap.get(tableName));
					pstmt.executeUpdate(psMap.get(tableName));
				}catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}break;
			}
		}
	}
}

