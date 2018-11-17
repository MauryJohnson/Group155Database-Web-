<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<!DOCTYPE HTML>
<html> 
<head>
<link rel="stylesheet" type="text/css" href="BaseStyle.css">
<title>Connection with mysql database</title>
</head> 
<body>
<h1>Connection status</h1>
<% 
try {
	Class.forName("com.mysql.jdbc.Driver"); 
    String connectionURL = "jdbc:mysql://bar-beer-drinker-plus.cqyzjclyvard.us-east-2.rds.amazonaws.com:3306/BBDP";
    Connection connection = null; 
    connection = DriverManager.getConnection(connectionURL, "TestUser", "TestUser");
    if(!connection.isClosed())
         out.println("Successfully connected to " + "MySQL server using TCP/IP...");
    connection.close();
}catch(Exception ex){
    out.println("Unable to connect to database "+ex );
}
%>
</body> 
</html>