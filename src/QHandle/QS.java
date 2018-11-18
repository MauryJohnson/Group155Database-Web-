package QHandle;

import java.util.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mysql.cj.protocol.Resultset;

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
	/**
	 * 
	 * @param Type
	 * @param Data
	 */
	
	public void PutEntry(int Type, String Data) {
		
		
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
					"WHERE T.drinker =" + "\"" + Param + "\"" + "\r\n" + 
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
					"WHERE T.drinker =" + "\"" + Param + "\"" + "\r\n" + 
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
					"WHERE T.drinker =" + "\"" + Param + "\"" + "\r\n" + 
					"ORDER BY FIELD(DOW, 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')\r\n" + 
					")\r\n" + 
					"ORDER BY bar,FIELD(DOW, 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')\r\n" + 
					")\r\n" + 
					"";
		}
		else if(type==4) {
			ret+="(SELECT T.bar,T.drinker,T.beer,T.price,MONTH(T.date_of_transaction) AS MOY FROM beer_transactions T\r\n" + 
					"WHERE T.drinker =" + "\"" + Param + "\"" + "\r\n" + 
					")\r\n" + 
					"UNION(\r\n" + 
					"SELECT T.bar,T.drinker,T.food,T.price,MONTH(T.date_of_transaction) AS MOY FROM food_transactions T\r\n" + 
					"WHERE T.drinker =" + "\"" + Param + "\"" + "\r\n" + 
					")\r\n" + 
					"UNION(\r\n" + 
					"SELECT T.bar,T.drinker,T.soft_drink,T.price,MONTH(T.date_of_transaction) AS MOY FROM soft_drink_transactions T\r\n" + 
					"WHERE T.drinker =" + "\"" + Param + "\"" + "\r\n" + 
					")\r\n" + 
					"ORDER BY bar,MOY\r\n" + 
					"";
		}
		else if(type==5) {
			ret+="SELECT DISTINCT T.bar,T.drinker,\r\n" + 
					"(\r\n" + 
					"(\r\n" + 
					"SELECT SUM(T2.price) FROM beer_transactions T2\r\n" + 
					"WHERE T.bar=T2.bar AND T.drinker = T2.drinker\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT SUM(T2.price) FROM food_transactions T2\r\n" + 
					"WHERE T.bar=T2.bar AND T.drinker = T2.drinker\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT SUM(T2.price) FROM soft_drink_transactions T2\r\n" + 
					"WHERE T.bar=T2.bar AND T.drinker = T2.drinker\r\n" + 
					") \r\n" + 
					")\r\n" + 
					"AS AllSpending\r\n" + 
					"FROM beer_transactions T\r\n" + 
					"WHERE T.bar=" + "\"" + Param + "\""
					+ "\r\nORDER BY AllSpending DESC";
		}
		else if(type==6) {
			ret+="SET @SameBeer:='0';\r\n" + 
					"SELECT DISTINCT T.bar,T.beer,@SameBeer FROM beer_transactions T\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"WHERE \r\n" + 
					"\r\n" + 
					"\r\n" + 
					"T.bar = " + "\"" + Param + "\"" + "\r\n" + 
					"\r\n" + 
					"AND\r\n" + 
					"\r\n" + 
					"(SELECT @SameBeer:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE T.bar=T2.bar AND T2.beer=T.beer\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"ORDER BY @SameBeer DESC\r\n" + 
					"";
		}
		else if(type==7) {
			ret+="SET @SameBeer:=0;\r\n" + 
					"SET @Manf:='0';\r\n" + 
					"SET @Beer:='0';\r\n" + 
					"\r\n" + 
					"SELECT DISTINCT T.bar,T.beer,@SameBeer,@Manf FROM beer_transactions T\r\n" + 
					"WHERE \r\n" + 
					"T.bar = " + "\"" + Param + "\"" + "\r\n" + 
					"AND(\r\n" + 
					"#Hack to set variables to desired results\r\n" + 
					"(SELECT @SameBeer:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE T.bar=T2.bar AND T2.beer=T.beer\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @Manf:=B.manf FROM beers B\r\n" + 
					"WHERE B.name=T.beer\r\n" + 
					")\r\n" + 
					")#########################################\r\n" + 
					"ORDER BY @SameBeer DESC\r\n" + 
					"";
			
		}
		else if(type==8) {
			ret+="#Time distribution of sales, THIS WILL BE BAR CHART\r\n" + 
					"#OF THESE TIMES...\r\n" + 
					"#THE time distribution of sales for this BAR\r\n" + 
					"(\r\n" + 
					"SELECT time_of_transaction,T.beer,T.price FROM beer_transactions T\r\n" + 
					"WHERE T.bar=" + "\"" + Param + "\"" +
					")\r\n" + 
					"UNION\r\n" + 
					"(\r\n" + 
					"SELECT time_of_transaction,T.food,T.price FROM food_transactions T\r\n" + 
					"WHERE T.bar=" + "\"" + Param + "\"" +
					")\r\n" + 
					"UNION\r\n" + 
					"(\r\n" + 
					"SELECT time_of_transaction,T.soft_drink,T.price FROM soft_drink_transactions T\r\n" + 
					"WHERE T.bar=" + "\"" + Param + "\"" +
					")\r\n" + 
					"ORDER BY time_of_transaction ASC\r\n" + 
					"";
			
		}
		else if(type==9) {
			ret+="#Busiest periods of day..... TIME, within 1-12 hour periods\r\n" + 
					"#Busiest period for beer, then food, then soft drink\r\n" + 
					"#transactions for givne BAR\r\n" + 
					"SET @HourCount1:=0;\r\n" + 
					"SET @HourCount2:=0;\r\n" + 
					"SET @HourCount3:=0;\r\n" + 
					"SET @HourCount4:=0;\r\n" + 
					"SET @HourCount5:=0;\r\n" + 
					"SET @HourCount6:=0;\r\n" + 
					"SET @HourCount7:=0;\r\n" + 
					"SET @HourCount8:=0;\r\n" + 
					"SET @HourCount9:=0;\r\n" + 
					"SET @HourCount10:=0;\r\n" + 
					"SET @HourCount11:=0;\r\n" + 
					"SET @HourCount12:=0;\r\n" + 
					"SET @MaxTimeWindow:=0;\r\n" + 
					"SET @MaxTimeWindowOccurrence:=0;\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"#Will look at 1,...,12 hour periods\r\n" + 
					"#To find busiest period of the day\r\n" + 
					"SELECT DISTINCT HOUR(T.time_of_transaction) AS HOUR,@MaxTimeWindow:=\r\n" + 
					"(CASE\r\n" + 
					"	\r\n" + 
					"	WHEN @HourCount1>=@HourCount2 AND @HourCount1>=@HourCount3 AND @HourCount1>=@HourCount4 AND @HourCount1>=@HourCount5 AND @HourCount1>=@HourCount6 AND @HourCount1>=@HourCount7 AND @HourCount1>=@HourCount8 AND @HourCount1>=@HourCount9 AND @HourCount1>=@HourCount10 AND @HourCount1>=@HourCount11 AND @HourCount1>=@HourCount12 \r\n" + 
					" THEN 1\r\n" + 
					"WHEN @HourCount2>=@HourCount1 AND @HourCount2>=@HourCount3 AND @HourCount2>=@HourCount4 AND @HourCount2>=@HourCount5 AND @HourCount2>=@HourCount6 AND @HourCount2>=@HourCount7 AND @HourCount2>=@HourCount8 AND @HourCount2>=@HourCount9 AND @HourCount2>=@HourCount10 AND @HourCount2>=@HourCount11 AND @HourCount2>=@HourCount12 \r\n" + 
					" THEN 2\r\n" + 
					"WHEN @HourCount3>=@HourCount1 AND @HourCount3>=@HourCount2 AND @HourCount3>=@HourCount4 AND @HourCount3>=@HourCount5 AND @HourCount3>=@HourCount6 AND @HourCount3>=@HourCount7 AND @HourCount3>=@HourCount8 AND @HourCount3>=@HourCount9 AND @HourCount3>=@HourCount10 AND @HourCount3>=@HourCount11 AND @HourCount3>=@HourCount12 \r\n" + 
					" THEN 3\r\n" + 
					"WHEN @HourCount4>=@HourCount1 AND @HourCount4>=@HourCount2 AND @HourCount4>=@HourCount3 AND @HourCount4>=@HourCount5 AND @HourCount4>=@HourCount6 AND @HourCount4>=@HourCount7 AND @HourCount4>=@HourCount8 AND @HourCount4>=@HourCount9 AND @HourCount4>=@HourCount10 AND @HourCount4>=@HourCount11 AND @HourCount4>=@HourCount12 \r\n" + 
					" THEN 4\r\n" + 
					"WHEN @HourCount5>=@HourCount1 AND @HourCount5>=@HourCount2 AND @HourCount5>=@HourCount3 AND @HourCount5>=@HourCount4 AND @HourCount5>=@HourCount6 AND @HourCount5>=@HourCount7 AND @HourCount5>=@HourCount8 AND @HourCount5>=@HourCount9 AND @HourCount5>=@HourCount10 AND @HourCount5>=@HourCount11 AND @HourCount5>=@HourCount12 \r\n" + 
					" THEN 5\r\n" + 
					"WHEN @HourCount6>=@HourCount1 AND @HourCount6>=@HourCount2 AND @HourCount6>=@HourCount3 AND @HourCount6>=@HourCount4 AND @HourCount6>=@HourCount5 AND @HourCount6>=@HourCount7 AND @HourCount6>=@HourCount8 AND @HourCount6>=@HourCount9 AND @HourCount6>=@HourCount10 AND @HourCount6>=@HourCount11 AND @HourCount6>=@HourCount12 \r\n" + 
					" THEN 6\r\n" + 
					"WHEN @HourCount7>=@HourCount1 AND @HourCount7>=@HourCount2 AND @HourCount7>=@HourCount3 AND @HourCount7>=@HourCount4 AND @HourCount7>=@HourCount5 AND @HourCount7>=@HourCount6 AND @HourCount7>=@HourCount8 AND @HourCount7>=@HourCount9 AND @HourCount7>=@HourCount10 AND @HourCount7>=@HourCount11 AND @HourCount7>=@HourCount12 \r\n" + 
					" THEN 7\r\n" + 
					"WHEN @HourCount8>=@HourCount1 AND @HourCount8>=@HourCount2 AND @HourCount8>=@HourCount3 AND @HourCount8>=@HourCount4 AND @HourCount8>=@HourCount5 AND @HourCount8>=@HourCount6 AND @HourCount8>=@HourCount7 AND @HourCount8>=@HourCount9 AND @HourCount8>=@HourCount10 AND @HourCount8>=@HourCount11 AND @HourCount8>=@HourCount12 \r\n" + 
					" THEN 8\r\n" + 
					"WHEN @HourCount9>=@HourCount1 AND @HourCount9>=@HourCount2 AND @HourCount9>=@HourCount3 AND @HourCount9>=@HourCount4 AND @HourCount9>=@HourCount5 AND @HourCount9>=@HourCount6 AND @HourCount9>=@HourCount7 AND @HourCount9>=@HourCount8 AND @HourCount9>=@HourCount10 AND @HourCount9>=@HourCount11 AND @HourCount9>=@HourCount12 \r\n" + 
					" THEN 9\r\n" + 
					"WHEN @HourCount10>=@HourCount1 AND @HourCount10>=@HourCount2 AND @HourCount10>=@HourCount3 AND @HourCount10>=@HourCount4 AND @HourCount10>=@HourCount5 AND @HourCount10>=@HourCount6 AND @HourCount10>=@HourCount7 AND @HourCount10>=@HourCount8 AND @HourCount10>=@HourCount9 AND @HourCount10>=@HourCount11 AND @HourCount10>=@HourCount12 \r\n" + 
					" THEN 10\r\n" + 
					"WHEN @HourCount11>=@HourCount1 AND @HourCount11>=@HourCount2 AND @HourCount11>=@HourCount3 AND @HourCount11>=@HourCount4 AND @HourCount11>=@HourCount5 AND @HourCount11>=@HourCount6 AND @HourCount11>=@HourCount7 AND @HourCount11>=@HourCount8 AND @HourCount11>=@HourCount9 AND @HourCount11>=@HourCount10 AND @HourCount11>=@HourCount12 \r\n" + 
					" THEN 11\r\n" + 
					"WHEN @HourCount12>=@HourCount1 AND @HourCount12>=@HourCount2 AND @HourCount12>=@HourCount3 AND @HourCount12>=@HourCount4 AND @HourCount12>=@HourCount5 AND @HourCount12>=@HourCount6 AND @HourCount12>=@HourCount7 AND @HourCount12>=@HourCount8 AND @HourCount12>=@HourCount9 AND @HourCount12>=@HourCount10 AND @HourCount12>=@HourCount11 \r\n" + 
					" THEN 12\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"        \r\n" + 
					"END\r\n" + 
					")\r\n" + 
					"AS HandleAllHoursCase\r\n" + 
					",@MaxTimeWindowOccurrence:=\r\n" + 
					"(\r\n" + 
					"CASE\r\n" + 
					"\r\n" + 
					"WHEN @HourCount1>=@HourCount2 AND @HourCount1>=@HourCount3 AND @HourCount1>=@HourCount4 AND @HourCount1>=@HourCount5 AND @HourCount1>=@HourCount6 AND @HourCount1>=@HourCount7 AND @HourCount1>=@HourCount8 AND @HourCount1>=@HourCount9 AND @HourCount1>=@HourCount10 AND @HourCount1>=@HourCount11 AND @HourCount1>=@HourCount12 \r\n" + 
					" THEN @HourCount1\r\n" + 
					"WHEN @HourCount2>=@HourCount1 AND @HourCount2>=@HourCount3 AND @HourCount2>=@HourCount4 AND @HourCount2>=@HourCount5 AND @HourCount2>=@HourCount6 AND @HourCount2>=@HourCount7 AND @HourCount2>=@HourCount8 AND @HourCount2>=@HourCount9 AND @HourCount2>=@HourCount10 AND @HourCount2>=@HourCount11 AND @HourCount2>=@HourCount12 \r\n" + 
					" THEN @HourCount2\r\n" + 
					"WHEN @HourCount3>=@HourCount1 AND @HourCount3>=@HourCount2 AND @HourCount3>=@HourCount4 AND @HourCount3>=@HourCount5 AND @HourCount3>=@HourCount6 AND @HourCount3>=@HourCount7 AND @HourCount3>=@HourCount8 AND @HourCount3>=@HourCount9 AND @HourCount3>=@HourCount10 AND @HourCount3>=@HourCount11 AND @HourCount3>=@HourCount12 \r\n" + 
					" THEN @HourCount3\r\n" + 
					"WHEN @HourCount4>=@HourCount1 AND @HourCount4>=@HourCount2 AND @HourCount4>=@HourCount3 AND @HourCount4>=@HourCount5 AND @HourCount4>=@HourCount6 AND @HourCount4>=@HourCount7 AND @HourCount4>=@HourCount8 AND @HourCount4>=@HourCount9 AND @HourCount4>=@HourCount10 AND @HourCount4>=@HourCount11 AND @HourCount4>=@HourCount12 \r\n" + 
					" THEN @HourCount4\r\n" + 
					"WHEN @HourCount5>=@HourCount1 AND @HourCount5>=@HourCount2 AND @HourCount5>=@HourCount3 AND @HourCount5>=@HourCount4 AND @HourCount5>=@HourCount6 AND @HourCount5>=@HourCount7 AND @HourCount5>=@HourCount8 AND @HourCount5>=@HourCount9 AND @HourCount5>=@HourCount10 AND @HourCount5>=@HourCount11 AND @HourCount5>=@HourCount12 \r\n" + 
					" THEN @HourCount5\r\n" + 
					"WHEN @HourCount6>=@HourCount1 AND @HourCount6>=@HourCount2 AND @HourCount6>=@HourCount3 AND @HourCount6>=@HourCount4 AND @HourCount6>=@HourCount5 AND @HourCount6>=@HourCount7 AND @HourCount6>=@HourCount8 AND @HourCount6>=@HourCount9 AND @HourCount6>=@HourCount10 AND @HourCount6>=@HourCount11 AND @HourCount6>=@HourCount12 \r\n" + 
					" THEN @HourCount6\r\n" + 
					"WHEN @HourCount7>=@HourCount1 AND @HourCount7>=@HourCount2 AND @HourCount7>=@HourCount3 AND @HourCount7>=@HourCount4 AND @HourCount7>=@HourCount5 AND @HourCount7>=@HourCount6 AND @HourCount7>=@HourCount8 AND @HourCount7>=@HourCount9 AND @HourCount7>=@HourCount10 AND @HourCount7>=@HourCount11 AND @HourCount7>=@HourCount12 \r\n" + 
					" THEN @HourCount7\r\n" + 
					"WHEN @HourCount8>=@HourCount1 AND @HourCount8>=@HourCount2 AND @HourCount8>=@HourCount3 AND @HourCount8>=@HourCount4 AND @HourCount8>=@HourCount5 AND @HourCount8>=@HourCount6 AND @HourCount8>=@HourCount7 AND @HourCount8>=@HourCount9 AND @HourCount8>=@HourCount10 AND @HourCount8>=@HourCount11 AND @HourCount8>=@HourCount12 \r\n" + 
					" THEN @HourCount8\r\n" + 
					"WHEN @HourCount9>=@HourCount1 AND @HourCount9>=@HourCount2 AND @HourCount9>=@HourCount3 AND @HourCount9>=@HourCount4 AND @HourCount9>=@HourCount5 AND @HourCount9>=@HourCount6 AND @HourCount9>=@HourCount7 AND @HourCount9>=@HourCount8 AND @HourCount9>=@HourCount10 AND @HourCount9>=@HourCount11 AND @HourCount9>=@HourCount12 \r\n" + 
					" THEN @HourCount9\r\n" + 
					"WHEN @HourCount10>=@HourCount1 AND @HourCount10>=@HourCount2 AND @HourCount10>=@HourCount3 AND @HourCount10>=@HourCount4 AND @HourCount10>=@HourCount5 AND @HourCount10>=@HourCount6 AND @HourCount10>=@HourCount7 AND @HourCount10>=@HourCount8 AND @HourCount10>=@HourCount9 AND @HourCount10>=@HourCount11 AND @HourCount10>=@HourCount12 \r\n" + 
					" THEN @HourCount10\r\n" + 
					"WHEN @HourCount11>=@HourCount1 AND @HourCount11>=@HourCount2 AND @HourCount11>=@HourCount3 AND @HourCount11>=@HourCount4 AND @HourCount11>=@HourCount5 AND @HourCount11>=@HourCount6 AND @HourCount11>=@HourCount7 AND @HourCount11>=@HourCount8 AND @HourCount11>=@HourCount9 AND @HourCount11>=@HourCount10 AND @HourCount11>=@HourCount12 \r\n" + 
					" THEN @HourCount11\r\n" + 
					"WHEN @HourCount12>=@HourCount1 AND @HourCount12>=@HourCount2 AND @HourCount12>=@HourCount3 AND @HourCount12>=@HourCount4 AND @HourCount12>=@HourCount5 AND @HourCount12>=@HourCount6 AND @HourCount12>=@HourCount7 AND @HourCount12>=@HourCount8 AND @HourCount12>=@HourCount9 AND @HourCount12>=@HourCount10 AND @HourCount12>=@HourCount11 \r\n" + 
					" THEN @HourCount12\r\n" + 
					" \r\n" + 
					" \r\n" + 
					"END\r\n" + 
					")\r\n" + 
					"As MaxTimeWindowOccurrence\r\n" + 
					"FROM beer_transactions T\r\n" + 
					"WHERE \r\n" + 
					"T.bar=" + "\"" + Param + "\"" + "\r\n" + 
					"AND\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount1:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=1\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount2:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=2\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount3:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=3\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount4:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=4\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount5:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=5\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount6:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=6\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount7:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=7\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount8:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=8\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount9:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=9\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount10:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=10\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount11:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=11\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount12:=COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=12\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"#ORDER BY MaxTimeWindowOccurrence DESC  \r\n" + 
					"\r\n" + 
					"\r\n" + 
					"UNION###########################################################\r\n" + 
					"\r\n" + 
					"(\r\n" + 
					"(\r\n" + 
					"#Will look at 1,...,12 hour periods\r\n" + 
					"#To find busiest period of the day\r\n" + 
					"SELECT DISTINCT HOUR(T.time_of_transaction) AS HOUR,@MaxTimeWindow:=\r\n" + 
					"(CASE\r\n" + 
					"	\r\n" + 
					"	WHEN @HourCount1>=@HourCount2 AND @HourCount1>=@HourCount3 AND @HourCount1>=@HourCount4 AND @HourCount1>=@HourCount5 AND @HourCount1>=@HourCount6 AND @HourCount1>=@HourCount7 AND @HourCount1>=@HourCount8 AND @HourCount1>=@HourCount9 AND @HourCount1>=@HourCount10 AND @HourCount1>=@HourCount11 AND @HourCount1>=@HourCount12 \r\n" + 
					" THEN 1\r\n" + 
					"WHEN @HourCount2>=@HourCount1 AND @HourCount2>=@HourCount3 AND @HourCount2>=@HourCount4 AND @HourCount2>=@HourCount5 AND @HourCount2>=@HourCount6 AND @HourCount2>=@HourCount7 AND @HourCount2>=@HourCount8 AND @HourCount2>=@HourCount9 AND @HourCount2>=@HourCount10 AND @HourCount2>=@HourCount11 AND @HourCount2>=@HourCount12 \r\n" + 
					" THEN 2\r\n" + 
					"WHEN @HourCount3>=@HourCount1 AND @HourCount3>=@HourCount2 AND @HourCount3>=@HourCount4 AND @HourCount3>=@HourCount5 AND @HourCount3>=@HourCount6 AND @HourCount3>=@HourCount7 AND @HourCount3>=@HourCount8 AND @HourCount3>=@HourCount9 AND @HourCount3>=@HourCount10 AND @HourCount3>=@HourCount11 AND @HourCount3>=@HourCount12 \r\n" + 
					" THEN 3\r\n" + 
					"WHEN @HourCount4>=@HourCount1 AND @HourCount4>=@HourCount2 AND @HourCount4>=@HourCount3 AND @HourCount4>=@HourCount5 AND @HourCount4>=@HourCount6 AND @HourCount4>=@HourCount7 AND @HourCount4>=@HourCount8 AND @HourCount4>=@HourCount9 AND @HourCount4>=@HourCount10 AND @HourCount4>=@HourCount11 AND @HourCount4>=@HourCount12 \r\n" + 
					" THEN 4\r\n" + 
					"WHEN @HourCount5>=@HourCount1 AND @HourCount5>=@HourCount2 AND @HourCount5>=@HourCount3 AND @HourCount5>=@HourCount4 AND @HourCount5>=@HourCount6 AND @HourCount5>=@HourCount7 AND @HourCount5>=@HourCount8 AND @HourCount5>=@HourCount9 AND @HourCount5>=@HourCount10 AND @HourCount5>=@HourCount11 AND @HourCount5>=@HourCount12 \r\n" + 
					" THEN 5\r\n" + 
					"WHEN @HourCount6>=@HourCount1 AND @HourCount6>=@HourCount2 AND @HourCount6>=@HourCount3 AND @HourCount6>=@HourCount4 AND @HourCount6>=@HourCount5 AND @HourCount6>=@HourCount7 AND @HourCount6>=@HourCount8 AND @HourCount6>=@HourCount9 AND @HourCount6>=@HourCount10 AND @HourCount6>=@HourCount11 AND @HourCount6>=@HourCount12 \r\n" + 
					" THEN 6\r\n" + 
					"WHEN @HourCount7>=@HourCount1 AND @HourCount7>=@HourCount2 AND @HourCount7>=@HourCount3 AND @HourCount7>=@HourCount4 AND @HourCount7>=@HourCount5 AND @HourCount7>=@HourCount6 AND @HourCount7>=@HourCount8 AND @HourCount7>=@HourCount9 AND @HourCount7>=@HourCount10 AND @HourCount7>=@HourCount11 AND @HourCount7>=@HourCount12 \r\n" + 
					" THEN 7\r\n" + 
					"WHEN @HourCount8>=@HourCount1 AND @HourCount8>=@HourCount2 AND @HourCount8>=@HourCount3 AND @HourCount8>=@HourCount4 AND @HourCount8>=@HourCount5 AND @HourCount8>=@HourCount6 AND @HourCount8>=@HourCount7 AND @HourCount8>=@HourCount9 AND @HourCount8>=@HourCount10 AND @HourCount8>=@HourCount11 AND @HourCount8>=@HourCount12 \r\n" + 
					" THEN 8\r\n" + 
					"WHEN @HourCount9>=@HourCount1 AND @HourCount9>=@HourCount2 AND @HourCount9>=@HourCount3 AND @HourCount9>=@HourCount4 AND @HourCount9>=@HourCount5 AND @HourCount9>=@HourCount6 AND @HourCount9>=@HourCount7 AND @HourCount9>=@HourCount8 AND @HourCount9>=@HourCount10 AND @HourCount9>=@HourCount11 AND @HourCount9>=@HourCount12 \r\n" + 
					" THEN 9\r\n" + 
					"WHEN @HourCount10>=@HourCount1 AND @HourCount10>=@HourCount2 AND @HourCount10>=@HourCount3 AND @HourCount10>=@HourCount4 AND @HourCount10>=@HourCount5 AND @HourCount10>=@HourCount6 AND @HourCount10>=@HourCount7 AND @HourCount10>=@HourCount8 AND @HourCount10>=@HourCount9 AND @HourCount10>=@HourCount11 AND @HourCount10>=@HourCount12 \r\n" + 
					" THEN 10\r\n" + 
					"WHEN @HourCount11>=@HourCount1 AND @HourCount11>=@HourCount2 AND @HourCount11>=@HourCount3 AND @HourCount11>=@HourCount4 AND @HourCount11>=@HourCount5 AND @HourCount11>=@HourCount6 AND @HourCount11>=@HourCount7 AND @HourCount11>=@HourCount8 AND @HourCount11>=@HourCount9 AND @HourCount11>=@HourCount10 AND @HourCount11>=@HourCount12 \r\n" + 
					" THEN 11\r\n" + 
					"WHEN @HourCount12>=@HourCount1 AND @HourCount12>=@HourCount2 AND @HourCount12>=@HourCount3 AND @HourCount12>=@HourCount4 AND @HourCount12>=@HourCount5 AND @HourCount12>=@HourCount6 AND @HourCount12>=@HourCount7 AND @HourCount12>=@HourCount8 AND @HourCount12>=@HourCount9 AND @HourCount12>=@HourCount10 AND @HourCount12>=@HourCount11 \r\n" + 
					" THEN 12\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"        \r\n" + 
					"END\r\n" + 
					")\r\n" + 
					"AS HandleAllHoursCase\r\n" + 
					",@MaxTimeWindowOccurrence:=\r\n" + 
					"(\r\n" + 
					"CASE\r\n" + 
					"\r\n" + 
					"WHEN @HourCount1>=@HourCount2 AND @HourCount1>=@HourCount3 AND @HourCount1>=@HourCount4 AND @HourCount1>=@HourCount5 AND @HourCount1>=@HourCount6 AND @HourCount1>=@HourCount7 AND @HourCount1>=@HourCount8 AND @HourCount1>=@HourCount9 AND @HourCount1>=@HourCount10 AND @HourCount1>=@HourCount11 AND @HourCount1>=@HourCount12 \r\n" + 
					" THEN @HourCount1\r\n" + 
					"WHEN @HourCount2>=@HourCount1 AND @HourCount2>=@HourCount3 AND @HourCount2>=@HourCount4 AND @HourCount2>=@HourCount5 AND @HourCount2>=@HourCount6 AND @HourCount2>=@HourCount7 AND @HourCount2>=@HourCount8 AND @HourCount2>=@HourCount9 AND @HourCount2>=@HourCount10 AND @HourCount2>=@HourCount11 AND @HourCount2>=@HourCount12 \r\n" + 
					" THEN @HourCount2\r\n" + 
					"WHEN @HourCount3>=@HourCount1 AND @HourCount3>=@HourCount2 AND @HourCount3>=@HourCount4 AND @HourCount3>=@HourCount5 AND @HourCount3>=@HourCount6 AND @HourCount3>=@HourCount7 AND @HourCount3>=@HourCount8 AND @HourCount3>=@HourCount9 AND @HourCount3>=@HourCount10 AND @HourCount3>=@HourCount11 AND @HourCount3>=@HourCount12 \r\n" + 
					" THEN @HourCount3\r\n" + 
					"WHEN @HourCount4>=@HourCount1 AND @HourCount4>=@HourCount2 AND @HourCount4>=@HourCount3 AND @HourCount4>=@HourCount5 AND @HourCount4>=@HourCount6 AND @HourCount4>=@HourCount7 AND @HourCount4>=@HourCount8 AND @HourCount4>=@HourCount9 AND @HourCount4>=@HourCount10 AND @HourCount4>=@HourCount11 AND @HourCount4>=@HourCount12 \r\n" + 
					" THEN @HourCount4\r\n" + 
					"WHEN @HourCount5>=@HourCount1 AND @HourCount5>=@HourCount2 AND @HourCount5>=@HourCount3 AND @HourCount5>=@HourCount4 AND @HourCount5>=@HourCount6 AND @HourCount5>=@HourCount7 AND @HourCount5>=@HourCount8 AND @HourCount5>=@HourCount9 AND @HourCount5>=@HourCount10 AND @HourCount5>=@HourCount11 AND @HourCount5>=@HourCount12 \r\n" + 
					" THEN @HourCount5\r\n" + 
					"WHEN @HourCount6>=@HourCount1 AND @HourCount6>=@HourCount2 AND @HourCount6>=@HourCount3 AND @HourCount6>=@HourCount4 AND @HourCount6>=@HourCount5 AND @HourCount6>=@HourCount7 AND @HourCount6>=@HourCount8 AND @HourCount6>=@HourCount9 AND @HourCount6>=@HourCount10 AND @HourCount6>=@HourCount11 AND @HourCount6>=@HourCount12 \r\n" + 
					" THEN @HourCount6\r\n" + 
					"WHEN @HourCount7>=@HourCount1 AND @HourCount7>=@HourCount2 AND @HourCount7>=@HourCount3 AND @HourCount7>=@HourCount4 AND @HourCount7>=@HourCount5 AND @HourCount7>=@HourCount6 AND @HourCount7>=@HourCount8 AND @HourCount7>=@HourCount9 AND @HourCount7>=@HourCount10 AND @HourCount7>=@HourCount11 AND @HourCount7>=@HourCount12 \r\n" + 
					" THEN @HourCount7\r\n" + 
					"WHEN @HourCount8>=@HourCount1 AND @HourCount8>=@HourCount2 AND @HourCount8>=@HourCount3 AND @HourCount8>=@HourCount4 AND @HourCount8>=@HourCount5 AND @HourCount8>=@HourCount6 AND @HourCount8>=@HourCount7 AND @HourCount8>=@HourCount9 AND @HourCount8>=@HourCount10 AND @HourCount8>=@HourCount11 AND @HourCount8>=@HourCount12 \r\n" + 
					" THEN @HourCount8\r\n" + 
					"WHEN @HourCount9>=@HourCount1 AND @HourCount9>=@HourCount2 AND @HourCount9>=@HourCount3 AND @HourCount9>=@HourCount4 AND @HourCount9>=@HourCount5 AND @HourCount9>=@HourCount6 AND @HourCount9>=@HourCount7 AND @HourCount9>=@HourCount8 AND @HourCount9>=@HourCount10 AND @HourCount9>=@HourCount11 AND @HourCount9>=@HourCount12 \r\n" + 
					" THEN @HourCount9\r\n" + 
					"WHEN @HourCount10>=@HourCount1 AND @HourCount10>=@HourCount2 AND @HourCount10>=@HourCount3 AND @HourCount10>=@HourCount4 AND @HourCount10>=@HourCount5 AND @HourCount10>=@HourCount6 AND @HourCount10>=@HourCount7 AND @HourCount10>=@HourCount8 AND @HourCount10>=@HourCount9 AND @HourCount10>=@HourCount11 AND @HourCount10>=@HourCount12 \r\n" + 
					" THEN @HourCount10\r\n" + 
					"WHEN @HourCount11>=@HourCount1 AND @HourCount11>=@HourCount2 AND @HourCount11>=@HourCount3 AND @HourCount11>=@HourCount4 AND @HourCount11>=@HourCount5 AND @HourCount11>=@HourCount6 AND @HourCount11>=@HourCount7 AND @HourCount11>=@HourCount8 AND @HourCount11>=@HourCount9 AND @HourCount11>=@HourCount10 AND @HourCount11>=@HourCount12 \r\n" + 
					" THEN @HourCount11\r\n" + 
					"WHEN @HourCount12>=@HourCount1 AND @HourCount12>=@HourCount2 AND @HourCount12>=@HourCount3 AND @HourCount12>=@HourCount4 AND @HourCount12>=@HourCount5 AND @HourCount12>=@HourCount6 AND @HourCount12>=@HourCount7 AND @HourCount12>=@HourCount8 AND @HourCount12>=@HourCount9 AND @HourCount12>=@HourCount10 AND @HourCount12>=@HourCount11 \r\n" + 
					" THEN @HourCount12\r\n" + 
					" \r\n" + 
					" \r\n" + 
					"END\r\n" + 
					")\r\n" + 
					"As MaxTimeWindowOccurrence\r\n" + 
					"FROM food_transactions T\r\n" + 
					"WHERE \r\n" + 
					"T.bar=" + "\"" + Param + "\"" + "\r\n" + 
					"AND\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount1:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=1\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount2:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=2\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount3:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=3\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount4:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=4\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount5:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=5\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount6:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=6\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount7:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=7\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount8:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=8\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount9:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=9\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount10:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=10\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount11:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=11\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount12:=COUNT(*) FROM food_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=12\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"#ORDER BY MaxTimeWindowOccurrence DESC\r\n" + 
					")\r\n" + 
					"\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"UNION#############################################\r\n" + 
					"\r\n" + 
					"(\r\n" + 
					"#Will look at 1,...,12 hour periods\r\n" + 
					"#To find busiest period of the day\r\n" + 
					"SELECT DISTINCT HOUR(T.time_of_transaction) AS HOUR,@MaxTimeWindow:=\r\n" + 
					"(CASE\r\n" + 
					"	\r\n" + 
					"	WHEN @HourCount1>=@HourCount2 AND @HourCount1>=@HourCount3 AND @HourCount1>=@HourCount4 AND @HourCount1>=@HourCount5 AND @HourCount1>=@HourCount6 AND @HourCount1>=@HourCount7 AND @HourCount1>=@HourCount8 AND @HourCount1>=@HourCount9 AND @HourCount1>=@HourCount10 AND @HourCount1>=@HourCount11 AND @HourCount1>=@HourCount12 \r\n" + 
					" THEN 1\r\n" + 
					"WHEN @HourCount2>=@HourCount1 AND @HourCount2>=@HourCount3 AND @HourCount2>=@HourCount4 AND @HourCount2>=@HourCount5 AND @HourCount2>=@HourCount6 AND @HourCount2>=@HourCount7 AND @HourCount2>=@HourCount8 AND @HourCount2>=@HourCount9 AND @HourCount2>=@HourCount10 AND @HourCount2>=@HourCount11 AND @HourCount2>=@HourCount12 \r\n" + 
					" THEN 2\r\n" + 
					"WHEN @HourCount3>=@HourCount1 AND @HourCount3>=@HourCount2 AND @HourCount3>=@HourCount4 AND @HourCount3>=@HourCount5 AND @HourCount3>=@HourCount6 AND @HourCount3>=@HourCount7 AND @HourCount3>=@HourCount8 AND @HourCount3>=@HourCount9 AND @HourCount3>=@HourCount10 AND @HourCount3>=@HourCount11 AND @HourCount3>=@HourCount12 \r\n" + 
					" THEN 3\r\n" + 
					"WHEN @HourCount4>=@HourCount1 AND @HourCount4>=@HourCount2 AND @HourCount4>=@HourCount3 AND @HourCount4>=@HourCount5 AND @HourCount4>=@HourCount6 AND @HourCount4>=@HourCount7 AND @HourCount4>=@HourCount8 AND @HourCount4>=@HourCount9 AND @HourCount4>=@HourCount10 AND @HourCount4>=@HourCount11 AND @HourCount4>=@HourCount12 \r\n" + 
					" THEN 4\r\n" + 
					"WHEN @HourCount5>=@HourCount1 AND @HourCount5>=@HourCount2 AND @HourCount5>=@HourCount3 AND @HourCount5>=@HourCount4 AND @HourCount5>=@HourCount6 AND @HourCount5>=@HourCount7 AND @HourCount5>=@HourCount8 AND @HourCount5>=@HourCount9 AND @HourCount5>=@HourCount10 AND @HourCount5>=@HourCount11 AND @HourCount5>=@HourCount12 \r\n" + 
					" THEN 5\r\n" + 
					"WHEN @HourCount6>=@HourCount1 AND @HourCount6>=@HourCount2 AND @HourCount6>=@HourCount3 AND @HourCount6>=@HourCount4 AND @HourCount6>=@HourCount5 AND @HourCount6>=@HourCount7 AND @HourCount6>=@HourCount8 AND @HourCount6>=@HourCount9 AND @HourCount6>=@HourCount10 AND @HourCount6>=@HourCount11 AND @HourCount6>=@HourCount12 \r\n" + 
					" THEN 6\r\n" + 
					"WHEN @HourCount7>=@HourCount1 AND @HourCount7>=@HourCount2 AND @HourCount7>=@HourCount3 AND @HourCount7>=@HourCount4 AND @HourCount7>=@HourCount5 AND @HourCount7>=@HourCount6 AND @HourCount7>=@HourCount8 AND @HourCount7>=@HourCount9 AND @HourCount7>=@HourCount10 AND @HourCount7>=@HourCount11 AND @HourCount7>=@HourCount12 \r\n" + 
					" THEN 7\r\n" + 
					"WHEN @HourCount8>=@HourCount1 AND @HourCount8>=@HourCount2 AND @HourCount8>=@HourCount3 AND @HourCount8>=@HourCount4 AND @HourCount8>=@HourCount5 AND @HourCount8>=@HourCount6 AND @HourCount8>=@HourCount7 AND @HourCount8>=@HourCount9 AND @HourCount8>=@HourCount10 AND @HourCount8>=@HourCount11 AND @HourCount8>=@HourCount12 \r\n" + 
					" THEN 8\r\n" + 
					"WHEN @HourCount9>=@HourCount1 AND @HourCount9>=@HourCount2 AND @HourCount9>=@HourCount3 AND @HourCount9>=@HourCount4 AND @HourCount9>=@HourCount5 AND @HourCount9>=@HourCount6 AND @HourCount9>=@HourCount7 AND @HourCount9>=@HourCount8 AND @HourCount9>=@HourCount10 AND @HourCount9>=@HourCount11 AND @HourCount9>=@HourCount12 \r\n" + 
					" THEN 9\r\n" + 
					"WHEN @HourCount10>=@HourCount1 AND @HourCount10>=@HourCount2 AND @HourCount10>=@HourCount3 AND @HourCount10>=@HourCount4 AND @HourCount10>=@HourCount5 AND @HourCount10>=@HourCount6 AND @HourCount10>=@HourCount7 AND @HourCount10>=@HourCount8 AND @HourCount10>=@HourCount9 AND @HourCount10>=@HourCount11 AND @HourCount10>=@HourCount12 \r\n" + 
					" THEN 10\r\n" + 
					"WHEN @HourCount11>=@HourCount1 AND @HourCount11>=@HourCount2 AND @HourCount11>=@HourCount3 AND @HourCount11>=@HourCount4 AND @HourCount11>=@HourCount5 AND @HourCount11>=@HourCount6 AND @HourCount11>=@HourCount7 AND @HourCount11>=@HourCount8 AND @HourCount11>=@HourCount9 AND @HourCount11>=@HourCount10 AND @HourCount11>=@HourCount12 \r\n" + 
					" THEN 11\r\n" + 
					"WHEN @HourCount12>=@HourCount1 AND @HourCount12>=@HourCount2 AND @HourCount12>=@HourCount3 AND @HourCount12>=@HourCount4 AND @HourCount12>=@HourCount5 AND @HourCount12>=@HourCount6 AND @HourCount12>=@HourCount7 AND @HourCount12>=@HourCount8 AND @HourCount12>=@HourCount9 AND @HourCount12>=@HourCount10 AND @HourCount12>=@HourCount11 \r\n" + 
					" THEN 12\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"        \r\n" + 
					"END\r\n" + 
					")\r\n" + 
					"AS HandleAllHoursCase\r\n" + 
					",@MaxTimeWindowOccurrence:=\r\n" + 
					"(\r\n" + 
					"CASE\r\n" + 
					"\r\n" + 
					"WHEN @HourCount1>=@HourCount2 AND @HourCount1>=@HourCount3 AND @HourCount1>=@HourCount4 AND @HourCount1>=@HourCount5 AND @HourCount1>=@HourCount6 AND @HourCount1>=@HourCount7 AND @HourCount1>=@HourCount8 AND @HourCount1>=@HourCount9 AND @HourCount1>=@HourCount10 AND @HourCount1>=@HourCount11 AND @HourCount1>=@HourCount12 \r\n" + 
					" THEN @HourCount1\r\n" + 
					"WHEN @HourCount2>=@HourCount1 AND @HourCount2>=@HourCount3 AND @HourCount2>=@HourCount4 AND @HourCount2>=@HourCount5 AND @HourCount2>=@HourCount6 AND @HourCount2>=@HourCount7 AND @HourCount2>=@HourCount8 AND @HourCount2>=@HourCount9 AND @HourCount2>=@HourCount10 AND @HourCount2>=@HourCount11 AND @HourCount2>=@HourCount12 \r\n" + 
					" THEN @HourCount2\r\n" + 
					"WHEN @HourCount3>=@HourCount1 AND @HourCount3>=@HourCount2 AND @HourCount3>=@HourCount4 AND @HourCount3>=@HourCount5 AND @HourCount3>=@HourCount6 AND @HourCount3>=@HourCount7 AND @HourCount3>=@HourCount8 AND @HourCount3>=@HourCount9 AND @HourCount3>=@HourCount10 AND @HourCount3>=@HourCount11 AND @HourCount3>=@HourCount12 \r\n" + 
					" THEN @HourCount3\r\n" + 
					"WHEN @HourCount4>=@HourCount1 AND @HourCount4>=@HourCount2 AND @HourCount4>=@HourCount3 AND @HourCount4>=@HourCount5 AND @HourCount4>=@HourCount6 AND @HourCount4>=@HourCount7 AND @HourCount4>=@HourCount8 AND @HourCount4>=@HourCount9 AND @HourCount4>=@HourCount10 AND @HourCount4>=@HourCount11 AND @HourCount4>=@HourCount12 \r\n" + 
					" THEN @HourCount4\r\n" + 
					"WHEN @HourCount5>=@HourCount1 AND @HourCount5>=@HourCount2 AND @HourCount5>=@HourCount3 AND @HourCount5>=@HourCount4 AND @HourCount5>=@HourCount6 AND @HourCount5>=@HourCount7 AND @HourCount5>=@HourCount8 AND @HourCount5>=@HourCount9 AND @HourCount5>=@HourCount10 AND @HourCount5>=@HourCount11 AND @HourCount5>=@HourCount12 \r\n" + 
					" THEN @HourCount5\r\n" + 
					"WHEN @HourCount6>=@HourCount1 AND @HourCount6>=@HourCount2 AND @HourCount6>=@HourCount3 AND @HourCount6>=@HourCount4 AND @HourCount6>=@HourCount5 AND @HourCount6>=@HourCount7 AND @HourCount6>=@HourCount8 AND @HourCount6>=@HourCount9 AND @HourCount6>=@HourCount10 AND @HourCount6>=@HourCount11 AND @HourCount6>=@HourCount12 \r\n" + 
					" THEN @HourCount6\r\n" + 
					"WHEN @HourCount7>=@HourCount1 AND @HourCount7>=@HourCount2 AND @HourCount7>=@HourCount3 AND @HourCount7>=@HourCount4 AND @HourCount7>=@HourCount5 AND @HourCount7>=@HourCount6 AND @HourCount7>=@HourCount8 AND @HourCount7>=@HourCount9 AND @HourCount7>=@HourCount10 AND @HourCount7>=@HourCount11 AND @HourCount7>=@HourCount12 \r\n" + 
					" THEN @HourCount7\r\n" + 
					"WHEN @HourCount8>=@HourCount1 AND @HourCount8>=@HourCount2 AND @HourCount8>=@HourCount3 AND @HourCount8>=@HourCount4 AND @HourCount8>=@HourCount5 AND @HourCount8>=@HourCount6 AND @HourCount8>=@HourCount7 AND @HourCount8>=@HourCount9 AND @HourCount8>=@HourCount10 AND @HourCount8>=@HourCount11 AND @HourCount8>=@HourCount12 \r\n" + 
					" THEN @HourCount8\r\n" + 
					"WHEN @HourCount9>=@HourCount1 AND @HourCount9>=@HourCount2 AND @HourCount9>=@HourCount3 AND @HourCount9>=@HourCount4 AND @HourCount9>=@HourCount5 AND @HourCount9>=@HourCount6 AND @HourCount9>=@HourCount7 AND @HourCount9>=@HourCount8 AND @HourCount9>=@HourCount10 AND @HourCount9>=@HourCount11 AND @HourCount9>=@HourCount12 \r\n" + 
					" THEN @HourCount9\r\n" + 
					"WHEN @HourCount10>=@HourCount1 AND @HourCount10>=@HourCount2 AND @HourCount10>=@HourCount3 AND @HourCount10>=@HourCount4 AND @HourCount10>=@HourCount5 AND @HourCount10>=@HourCount6 AND @HourCount10>=@HourCount7 AND @HourCount10>=@HourCount8 AND @HourCount10>=@HourCount9 AND @HourCount10>=@HourCount11 AND @HourCount10>=@HourCount12 \r\n" + 
					" THEN @HourCount10\r\n" + 
					"WHEN @HourCount11>=@HourCount1 AND @HourCount11>=@HourCount2 AND @HourCount11>=@HourCount3 AND @HourCount11>=@HourCount4 AND @HourCount11>=@HourCount5 AND @HourCount11>=@HourCount6 AND @HourCount11>=@HourCount7 AND @HourCount11>=@HourCount8 AND @HourCount11>=@HourCount9 AND @HourCount11>=@HourCount10 AND @HourCount11>=@HourCount12 \r\n" + 
					" THEN @HourCount11\r\n" + 
					"WHEN @HourCount12>=@HourCount1 AND @HourCount12>=@HourCount2 AND @HourCount12>=@HourCount3 AND @HourCount12>=@HourCount4 AND @HourCount12>=@HourCount5 AND @HourCount12>=@HourCount6 AND @HourCount12>=@HourCount7 AND @HourCount12>=@HourCount8 AND @HourCount12>=@HourCount9 AND @HourCount12>=@HourCount10 AND @HourCount12>=@HourCount11 \r\n" + 
					" THEN @HourCount12\r\n" + 
					" \r\n" + 
					" \r\n" + 
					"END\r\n" + 
					")\r\n" + 
					"As MaxTimeWindowOccurrence\r\n" + 
					"FROM soft_drink_transactions T\r\n" + 
					"WHERE \r\n" + 
					"T.bar=" + "\"" + Param + "\"" + "\r\n" + 
					"AND\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount1:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=1\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount2:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=2\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount3:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=3\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount4:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=4\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount5:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=5\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount6:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=6\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount7:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=7\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount8:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=8\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount9:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=9\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount10:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=10\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount11:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=11\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"+\r\n" + 
					"(\r\n" + 
					"SELECT @HourCount12:=COUNT(*) FROM soft_drink_transactions T2\r\n" + 
					"WHERE HOUR(T2.time_of_transaction)-HOUR(T.time_of_transaction)=12\r\n" + 
					"AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"ORDER BY MaxTimeWindowOccurrence DESC\r\n" + 
					"";
		}
		else if(type==10) {
			ret+="#Set of maximum bought beers in all bars\r\n" + 
					"SELECT DISTINCT T.bar,T.beer,\r\n" + 
					"(\r\n" + 
					"SELECT COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE T2.beer=T.beer AND T2.bar=T.bar\r\n" + 
					")\r\n" + 
					"AS CountOcc\r\n" + 
					"FROM beer_transactions T\r\n" + 
					"WHERE T.beer=" + "\"" + Param + "\"" + "\r\n" + 
					"ORDER BY CountOcc DESC\r\n" + 
					"";
		}
		else if(type==11) {
			ret+="#Set of maximum bought beers in all bars\r\n" + 
					"SELECT DISTINCT T.drinker,\r\n" + 
					"\r\n" + 
					"(\r\n" + 
					"SELECT COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE T2.beer=T.beer AND T2.bar=T.bar AND HOUR(T2.time_of_transaction)=HOUR(T.time_of_transaction)\r\n" + 
					"AND T2.drinker = T.drinker\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"AS CountOcc\r\n" + 
					"FROM beer_transactions T\r\n" + 
					"WHERE T.beer=" + "\"" + Param + "\"" + "\r\n" + 
					"ORDER BY CountOcc DESC\r\n" + 
					"";
		}
		else if(type==12) {
			ret+="#Set of maximum bought beers in all bars\r\n" + 
					"SELECT DISTINCT HOUR(T.time_of_transaction),\r\n" + 
					"\r\n" + 
					"(\r\n" + 
					"SELECT COUNT(*) FROM beer_transactions T2\r\n" + 
					"WHERE T2.beer=T.beer AND T2.bar=T.bar AND HOUR(T2.time_of_transaction)=HOUR(T.time_of_transaction)\r\n" + 
					")\r\n" + 
					"\r\n" + 
					"AS CountOcc\r\n" + 
					"FROM beer_transactions T\r\n" + 
					"WHERE T.beer=" + "\"" + Param + "\"" + "\r\n" + 
					"ORDER BY T.time_of_transaction,CountOcc DESC\r\n" + 
					"";
		}
		else if(type==13) {
			ret+="SELECT * FROM drinkers"
					+ " LIMIT 30";
		}
		return ret;
	}
	
	//Many queries to parse type from
	public static String[] ParseType(String[]Queries, String Table) {
		String [] Types = new String[Queries.length];
		for(int h=0;h<Types.length;h+=1) {
			Types[h]="";
		}
		
		String Name = "";
		
		for(int i=0; i<Queries.length;i+=1) {
			Name = "";
			int LastPosition = 0;
			int j=0;
			
			for(j=0;j<Queries[i].length();j+=1) {
				if(Queries[i].substring(j, j+1)!=" ") {
					LastPosition = j;
					break;
				}
			}
			
			//This must be where query starts, or else failed
			
			System.out.printf("Start At char: %s\n", Queries[i].substring(LastPosition,LastPosition+1));
			Queries[i].replaceAll(" ", "");
			
			for(j=LastPosition; j<Queries[i].length() && j<6;j+=1) {
			Name+=Queries[i].substring(j, j+1);
			LastPosition = j;
			}
			//Name.replaceAll(" ", "");
			System.out.printf("\n GOT FUNC:%s\n",Name.toLowerCase());
			if(Name.toLowerCase().equals("update")) {
				Types[i]+="UPDATE";
				String MyTable = "";
				
				
				for(int k = LastPosition+1;k<Queries[i].length();k+=1) {
					if(!(Queries[i].substring(k, k+1).toCharArray()[0]==' ')) {
						LastPosition = k;
						break;
					}
				}
				
				
				for(int k=LastPosition;k<Queries[i].length()&&(Character.isAlphabetic(Queries[i].substring(k, k+1).toCharArray()[0]));k+=1) {
					
					if(MyTable.equals(Table) ||!(Character.isAlphabetic(Queries[i].substring(k, k+1).toCharArray()[0]))) {
						break;
					}
					else {
						MyTable+=Queries[i].substring(k, k+1);
					}
				}
				
				System.out.printf("\nMY TABLE YO:%s\n",MyTable);
				
				Types[i]+=" "+MyTable;
			}
			else if(Name.toLowerCase().equals("insert")) {
				Types[i]+="INSERT";
				String MyExpr = "";
				
				
				for(int k = LastPosition+1;k<Queries[i].length();k+=1) {
					if(!(Queries[i].substring(k, k+1).toCharArray()[0]==' ')) {
						LastPosition = k;
						break;
					}
				}
				
				
				for(int k=LastPosition;k<Queries[i].length()&&Queries[i].substring(k, k+1)!=" ";k+=1) {
					System.out.print(Queries[i].substring(k, k+1));
					MyExpr+=Queries[i].substring(k, k+1);
					if(MyExpr.toLowerCase().equals("into") || !(Character.isAlphabetic(Queries[i].substring(k, k+1).toCharArray()[0]))) {
						LastPosition = k;
						break;
					}
					LastPosition = k;
				}
				System.out.printf(" VALID INSERT Expr? GOT:%s\n", MyExpr);
				Types[i]+=" "+MyExpr;
				
				
				for(int k = LastPosition+1;k<Queries[i].length();k+=1) {
					if(!(Queries[i].substring(k, k+1).toCharArray()[0]==' ')) {
						LastPosition = k;
						break;
					}
				}
				
				
				String MyTable = "";
				for(int k=LastPosition;k<Queries[i].length()&&Queries[i].substring(k, k+1)!=" ";k+=1) {
					System.out.print(Queries[i].substring(k, k+1));
					MyTable+=Queries[i].substring(k, k+1);
					if(MyTable.toLowerCase().equals(Table) || (!(Character.isAlphabetic(Queries[i].substring(k, k+1).toCharArray()[0])))) {
						LastPosition = k;
						break;
					}
					LastPosition = k;
				}
				System.out.printf(" VALID INSERT Table? GOT:%s", MyTable);
				Types[i]+=" "+MyTable;
			}
			else if(Name.toLowerCase().equals("delete")) {
				Types[i]+="DELETE";
				String MyExpr = "";
				
				
				for(int k = LastPosition+1;k<Queries[i].length();k+=1) {
					if(!(Queries[i].substring(k, k+1).toCharArray()[0]==' ')) {
						LastPosition = k;
						break;
					}
				}
				
				
				for(int k=LastPosition;k<Queries[i].length()&&Queries[i].substring(k, k+1)!=" ";k+=1) {
					MyExpr+=Queries[i].substring(k, k+1);
					if(MyExpr.toLowerCase().equals("from") || !(Character.isAlphabetic(Queries[i].substring(k, k+1).toCharArray()[0]))) {
						LastPosition = k;
						break;
					}
					LastPosition = k;
				}
				System.out.printf(" VALID DELETE Expr? GOT:%s", MyExpr);
				Types[i]+=" "+MyExpr;
				String MyTable = "";
				
				
				for(int k = LastPosition+1;k<Queries[i].length();k+=1) {
					if(!(Queries[i].substring(k, k+1).toCharArray()[0]==' ')) {
						LastPosition = k;
						break;
					}
				}
				
				
				for(int k=LastPosition;k<Queries[i].length()&&Queries[i].substring(k, k+1)!=" ";k+=1) {
					MyTable+=Queries[i].substring(k, k+1);
					if(MyTable.toLowerCase().equals(Table) || !(Character.isAlphabetic(Queries[i].substring(k, k+1).toCharArray()[0]))) {
						LastPosition = k;
						break;
					}
					LastPosition = k;
				}
				System.out.printf(" VALID DELETE Table? GOT:%s", MyTable);
				Types[i]+=" "+MyTable;
			}
			else {
				return null;
			}
		}
		
		return Types;
	}
	
	public static int IterateCheck(String[] Queries, String Type,int t) {
		
		
		
		for(int i=0; i<Queries.length;i+=1) {
			
		}
		
		return t;
	}
	
	public static int ValidateModification(String qry,String Table) {
		String[] Queries = qry.split(";");
	
		for(int i=0; i<Queries.length;i+=1) {
			System.out.printf("\nSPLIT Q %d:%s\n",i,Queries[i]);
		}
		
		String[] AllTables = ParseType(Queries,Table);
		
		//Invalid Table type
		if(AllTables==null) {
			return -2;
		}
		
		for(int i=0; i<AllTables.length;i+=1) {
			System.out.printf("\nQRY %d:%s VS MY TABLE: %s\n",i,AllTables[i],Table);
			
			/*if(AllTables[i].contains("null")) {
				System.out.println("TABLE CONTAINS NULL,");
				return -3;
			}
			*/
		}
		
		//Bar
		//if(Type==1) {
		int Case = -1;
		for(int i=0; i<AllTables.length;i+=1) {
			
			try {
			
			if(AllTables[i].split(" ")[0].equals("UPDATE")) {
				if(!AllTables[i].split(" ")[1].equals(Table)) {
					System.out.println("\n\nINSERT SYNTAX ERR\n\n");
					return -1;
				}
						
				System.out.println("UPDATE");
				Case = 1;
			}
			else if(AllTables[i].split(" ")[0].equals("INSERT") ) {
				
				System.out.printf("\n\nIS INSERT?:%s IS TABLE? %s==%s\n\n",AllTables[i].split(" ")[1],AllTables[i].split(" ")[2],Table );
				
				if(!AllTables[i].split(" ")[1].toLowerCase().equals("into") || !AllTables[i].split(" ")[2].equals(Table)) {
					System.out.println("\n\nINSERT SYNTAX ERR\n\n");
					return -1;
				}
				
				System.out.println("INSERT");
				
				Case = 2;
			}
			else if(AllTables[i].split(" ")[0].equals("DELETE") || !AllTables[i].split(" ")[2].equals(Table.toLowerCase()) ) {
				
				System.out.printf("\n\nIS INSERT?:%s IS TABLE? %s==%s\n\n",AllTables[i].split(" ")[1],AllTables[i].split(" ")[2],Table );
				
				if(!AllTables[i].split(" ")[1].toLowerCase().equals("from") || !AllTables[i].split(" ")[2].equals(Table)) {
					System.out.println("\nDELETE SYNTAX ERR\n\n");
					return -1;
				}
				
				System.out.println("DELETE");
				Case =  3;
			}
			
			}
			catch(Exception e) {
				System.out.println(e);
				return -4;
			}
		}
		
		/*	
		//}
		//Beer
		//else if(Type==2) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("beer");
			}
		//}
		//Drinker
		//else if(Type==3) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("drinker");
			}	
		//}
		//Frequents
		//else if(Type==4) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("frequents");
			}
		//}
		//Likes
		//else if(Type==5) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("likes");
			}
		//}
		//Food Sells
		//else if(Type==6) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("foodsells");
			}
		//}
		//Beer Sells
		//else if(Type==7) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("beersells");
			}
		//}
		//Soft Drink Sells
		//else if(Type==8) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("softdrinksells");
			}
		//}
		//Food Transactions
		//else if(Type==9) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("foodtransactions");
			}
		//}
		//Beer Transactions
		//else if(Type==10) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("beertransactions");
			}	
		//}
		//Soft Drink Transactions
		//else if(Type==11) {
			if(AllTables[0].split(" ")[0].equals("UPDATE") || AllTables[0].split(" ")[0].equals("INSERT") || AllTables[0].split(" ")[0].equals("DELETE")) {
				return Table.toLowerCase().equals("softdrinktransactions");
			}
		//}
		*/
		
		//Not recognized query
		return Case;
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

	public static ResultSet ReadQueryResults(Statement stmt,String qry,int Type) throws SQLException {
		
		boolean hasMoreResultSets = stmt.execute(qry);
		
		LinkedList<ResultSet> RS = new LinkedList<ResultSet>();
		
		ResultSet rs = null;
		
		int Count = 0;
		
		 while ( hasMoreResultSets || stmt.getUpdateCount() != -1 ) {  
		        if ( hasMoreResultSets ) {  
		            rs = stmt.getResultSet();
		            RS.add(rs);
		            if(Type==0) {
		            	if(Count==1) {
		            		return rs;
		            	}
		            }
		            if(Type==1) {
		            	if(Count==3) {
		            		return rs;
		            	}
		            }
		            if(Type==2) {
		            	if(Count==14) {
		            		return rs;
		            	}
		            }
		            // handle your rs here
		        } // if has rs
		        else { // if ddl/dml/...
		            int queryResult = stmt.getUpdateCount();  
		            if ( queryResult == -1 ) { // no more queries processed  
		                break;  
		            } // no more queries processed  
		            // handle success, failure, generated keys, etc here
		        } // if ddl/dml/...

		        Count+=1;
		        
		        // check to continue in the loop  
		        hasMoreResultSets = stmt.getMoreResults();  
		    } // while results
		
		if(rs==null) {
			System.out.println("READ MULTI QUERY ERROR!!!!!!!!!!");
			//System.exit(-1);
			return null;
		}
		
		return rs;
	}
	
	public static Connection GetConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
		    String connectionURL = "jdbc:mysql://bar-beer-drinker-plus.cqyzjclyvard.us-east-2.rds.amazonaws.com:3306/BBDP?allowMultiQueries=true";
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
