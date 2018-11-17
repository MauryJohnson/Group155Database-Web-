package QHandle;

import java.util.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.sql.*;

//Will hold info about the query to be given to MySQL Query page
//Any query from queryState will be one query only
public class QS {
	public static Hashtable<String,String> Queries = new Hashtable<String,String>();
	/**
	 * If key exists, delete it and add new one
	 * @param qry
	 * @param ip
	 */
	public QS() {
		
	}
	public QS(String qry,String ip) {
		try {
		Queries.put(ip, qry);
		}
		catch(Exception e) {
			
		}
	}
	/**
	 * Get Query by user ip
	 * @param ip
	 * @return
	 */
	public String GetQuery(String ip) {
		return Queries.get(ip);
	}
	/**
	 * Sets query given criteria..
	 * @param Param
	 * @param type
	 * @return
	 */
	public static String SetQuery(String Param,int type) {
		if(Param==null) {
			return "";
		}
		if(Param.length()==0) {
			return "";
		}
		String ret = "";
		if(type==0) {
			ret+="SELECT T.bar,T.drinker,T.consumable,T.price,T.tip,T.date_of_transaction,T.time_of_transaction FROM\r\n" + 
					"(\r\n" + 
					"(\r\n" + 
					"##Drinker1 Transactions\r\n" + 
					"SELECT BT1.bar,BT1.drinker,BT1.beer AS consumable,BT1.price,BT1.tip,BT1.date_of_transaction,BT1.time_of_transaction FROM BBDP.beer_transactions\r\n" + 
					"AS BT1\r\n" + 
					"JOIN\r\n" + 
					"(SELECT * FROM BBDP.beer_transactions\r\n" + 
					"##Append Drinker Name HERE!!\r\n" + 
					
					"WHERE drinker =" + "\"" + Param + "\""+"\r\n" + 
					
					"##Append Drinker NAme HERE!!\r\n" + 
					"ORDER BY date_of_transaction+time_of_transaction ASC)\r\n" + 
					"AS BT2\r\n" + 
					"ON BT1.bar=BT2.bar AND BT1.drinker = BT2.drinker\r\n" + 
					"AND BT1.beer=BT2.beer AND BT1.price=BT2.price\r\n" + 
					"AND BT1.date_of_transaction=BT2.date_of_transaction\r\n" + 
					"AND BT1.time_of_transaction=BT2.time_of_transaction\r\n" + 
					"ORDER BY BT1.bar,BT1.date_of_transaction,BT1.time_of_transaction\r\n" + 
					")\r\n" + 
					"UNION ALL\r\n" + 
					"(\r\n" + 
					"SELECT BT1.bar,BT1.drinker,BT1.food,BT1.price,BT1.tip,BT1.date_of_transaction,BT1.time_of_transaction FROM BBDP.food_transactions\r\n" + 
					"AS BT1\r\n" + 
					"JOIN\r\n" + 
					"(SELECT * FROM BBDP.food_transactions\r\n" + 
					"##Append Drinker Name HERE!!\r\n" + 
					
					"WHERE drinker =" + "\"" + Param + "\""+"\r\n" + 
					
					"##Append Drinker NAme HERE!!\r\n" + 
					"ORDER BY date_of_transaction+time_of_transaction ASC)\r\n" + 
					"AS BT2\r\n" + 
					"ON BT1.bar=BT2.bar AND BT1.drinker = BT2.drinker\r\n" + 
					"AND BT1.food=BT2.food AND BT1.price=BT2.price\r\n" + 
					"AND BT1.date_of_transaction=BT2.date_of_transaction\r\n" + 
					"AND BT1.time_of_transaction=BT2.time_of_transaction\r\n" + 
					"ORDER BY BT1.bar,BT1.date_of_transaction,BT1.time_of_transaction\r\n" + 
					")\r\n" + 
					"UNION ALL\r\n" + 
					"(\r\n" + 
					"SELECT BT1.bar,BT1.drinker,BT1.soft_drink,BT1.price,BT1.tip,BT1.date_of_transaction,BT1.time_of_transaction FROM BBDP.soft_drink_transactions\r\n" + 
					"AS BT1\r\n" + 
					"JOIN\r\n" + 
					"(SELECT * FROM BBDP.soft_drink_transactions\r\n" + 
					"##Append Drinker Name HERE!!\r\n" + 
					
					"WHERE drinker =" + "\"" + Param + "\""+"\r\n" + 
					
					"##Append Drinker NAme HERE!!\r\n" + 
					"ORDER BY date_of_transaction+time_of_transaction ASC)\r\n" + 
					"AS BT2\r\n" + 
					"ON BT1.bar=BT2.bar AND BT1.drinker = BT2.drinker\r\n" + 
					"AND BT1.soft_drink=BT2.soft_drink AND BT1.price=BT2.price\r\n" + 
					"AND BT1.date_of_transaction=BT2.date_of_transaction\r\n" + 
					"AND BT1.time_of_transaction=BT2.time_of_transaction\r\n" + 
					"ORDER BY BT1.bar,BT1.date_of_transaction,BT1.time_of_transaction\r\n" + 
					")\r\n" + 
					")\r\n" + 
					"AS T\r\n" + 
					"ORDER BY bar,date_of_transaction,time_of_transaction"
					+" LIMIT 10";
					;
		}
		else if(type==1) {
			ret+="# Set of maximum bought beers from Drinker\r\n" + 
					"SELECT T.beer FROM beer_transactions T\r\n" + 
					"WHERE T.drinker =" + "\"" + Param + "\"" + "\r\n" + 
					"AND NOT EXISTS(\r\n" + 
					"(\r\n" + 
					"SELECT T3.beer FROM beer_transactions T3\r\n" + 
					"WHERE T3.drinker=T.drinker AND T3.beer!=T.beer\r\n" + 
					"AND(\r\n" + 
					"\r\n" + 
					"(SELECT COUNT(T2.beer) FROM beer_transactions T2\r\n" + 
					"WHERE T2.drinker=T.drinker AND T2.beer=T.beer\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"<\r\n" + 
					"\r\n" + 
					"(SELECT COUNT(T2.beer) FROM beer_transactions T2\r\n" + 
					"WHERE T2.drinker=T.drinker AND T2.beer=T3.beer\r\n" + 
					")\r\n" + 
					"\r\n" + 
					")\r\n" + 
					")\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"";
		}
		else if(type==2) {
			ret+="(\r\n" + 
					"(SELECT T.bar,T.drinker,T.beer,T.price,DAYOFYEAR(T.date_of_transaction) AS DOY FROM beer_transactions T\r\n" + 
					"WHERE T.drinker = " + "\""+Param+"\"" +"\r\n" + 
					"ORDER BY DOY\r\n" + 
					")\r\n" + 
					"UNION\r\n" + 
					"(\r\n" + 
					"SELECT T.bar,T.drinker,T.food,T.price,DAYOFYEAR(T.date_of_transaction) AS DOY FROM food_transactions T\r\n" + 
					"WHERE T.drinker = "+ "\""+Param+"\"" + "\r\n" + 
					"ORDER BY bar,DOY\r\n" + 
					")\r\n" + 
					"UNION(\r\n" + 
					"SELECT T.bar,T.drinker,T.soft_drink,T.price,DAYOFYEAR(T.date_of_transaction) AS DOY FROM soft_drink_transactions T\r\n" + 
					"WHERE T.drinker = "+"\""+Param+"\"" + "\r\n" + 
					"ORDER BY DOY\r\n" + 
					")\r\n" + 
					"ORDER BY DOY\r\n" + 
					")\r\n" + 
					"";
		}
		else if(type==3) {
			ret+="(\r\n" + 
					"(\r\n" + 
					"SELECT T.bar,T.drinker,T.beer,T.price,\r\n" + 
					"CASE\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=1\r\n" + 
					"	THEN \"Monday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=2\r\n" + 
					"	THEN \"Tuesday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=3\r\n" + 
					"	THEN \"Wednesday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=4\r\n" + 
					"	THEN \"Thursday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=5\r\n" + 
					"	THEN \"Friday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=6\r\n" + 
					"	THEN \"Saturday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=7\r\n" + 
					"	THEN \"Sunday\"\r\n" + 
					"END\r\n" + 
					"AS DOW\r\n" + 
					" FROM beer_transactions T\r\n" + 
					"WHERE T.drinker = \"Aimee Curtis\"\r\n" + 
					"ORDER BY FIELD(DOW, 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')\r\n" + 
					")\r\n" + 
					"UNION\r\n" + 
					"(\r\n" + 
					"SELECT T.bar,T.drinker,T.food,T.price,\r\n" + 
					"CASE\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=1\r\n" + 
					"	THEN \"Monday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=2\r\n" + 
					"	THEN \"Tuesday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=3\r\n" + 
					"	THEN \"Wednesday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=4\r\n" + 
					"	THEN \"Thursday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=5\r\n" + 
					"	THEN \"Friday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=6\r\n" + 
					"	THEN \"Saturday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=7\r\n" + 
					"	THEN \"Sunday\"\r\n" + 
					"END\r\n" + 
					"AS DOW\r\n" + 
					" FROM food_transactions T\r\n" + 
					"WHERE T.drinker = \"Aimee Curtis\"\r\n" + 
					"ORDER BY FIELD(DOW, 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')\r\n" + 
					")\r\n" + 
					"UNION(\r\n" + 
					"SELECT T.bar,T.drinker,T.soft_drink,T.price,\r\n" + 
					"CASE\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=1\r\n" + 
					"	THEN \"Monday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=2\r\n" + 
					"	THEN \"Tuesday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=3\r\n" + 
					"	THEN \"Wednesday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=4\r\n" + 
					"	THEN \"Thursday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=5\r\n" + 
					"	THEN \"Friday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=6\r\n" + 
					"	THEN \"Saturday\"\r\n" + 
					"WHEN (DAYOFWEEK(T.date_of_transaction))=7\r\n" + 
					"	THEN \"Sunday\"\r\n" + 
					"END\r\n" + 
					"AS DOW\r\n" + 
					" FROM soft_drink_transactions T\r\n" + 
					"WHERE T.drinker = \"Aimee Curtis\"\r\n" + 
					"ORDER BY FIELD(DOW, 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')\r\n" + 
					")\r\n" + 
					"ORDER BY bar,FIELD(DOW, 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')\r\n" + 
					")\r\n" + 
					"";
		}
		else if(type==4) {
			ret+="(SELECT T.bar,T.drinker,T.beer,T.price,MONTH(T.date_of_transaction) AS MOY FROM beer_transactions T\r\n" + 
					"WHERE T.drinker=\"Aimee Curtis\"\r\n" + 
					")\r\n" + 
					"UNION(\r\n" + 
					"SELECT T.bar,T.drinker,T.food,T.price,MONTH(T.date_of_transaction) AS MOY FROM food_transactions T\r\n" + 
					"WHERE T.drinker=\"Aimee Curtis\"\r\n" + 
					")\r\n" + 
					"UNION(\r\n" + 
					"SELECT T.bar,T.drinker,T.soft_drink,T.price,MONTH(T.date_of_transaction) AS MOY FROM soft_drink_transactions T\r\n" + 
					"WHERE T.drinker=\"Aimee Curtis\"\r\n" + 
					")\r\n" + 
					"ORDER BY bar,MOY\r\n" + 
					"";
		}
		else if(type==5) {
			
			
			
		}
		
		
		return ret;
	}
	
	public static String GetIp(String ip,HttpServletRequest R) {
		
		HttpServletRequest request = (HttpServletRequest)R;
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }
        
        return ip;
	}
	// From: http://blog.efftinge.de/2008/10/multi-line-string-literals-in-java.html
	// Takes a comment (/**/) and turns everything inside the comment to a string that is returned from S()
	public static String S() {
	    StackTraceElement element = new RuntimeException().getStackTrace()[1];
	    String name = element.getClassName().replace('.', '/') + ".java";
	    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	    InputStream in = classLoader.getResourceAsStream(name);
	    String s = convertStreamToString(in, element.getLineNumber());
	    return s.substring(s.indexOf("/*")+2, s.indexOf("*/"));
	}

	public static Connection GetConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
		    String connectionURL = "jdbc:mysql://bar-beer-drinker-plus.cqyzjclyvard.us-east-2.rds.amazonaws.com:3306/BBDP";
		    Connection connection = null; 
		    connection = DriverManager.getConnection(connectionURL, "TestUser", "TestUser");
			return connection;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	// From http://www.kodejava.org/examples/266.html
	private static String convertStreamToString(InputStream is, int lineNum) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null; int i = 1;
	    try {
	        while ((line = reader.readLine()) != null) {
	            if (i++ >= lineNum) {
	                sb.append(line + "\n");
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    return sb.toString();
	}
}