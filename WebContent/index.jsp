<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %>
<%@ page import= "QHandle.*" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>

<head>
<link rel="stylesheet" type="text/css" href="BaseStyle.css">
	<!--  <link rel="stylesheet" href="reset.css">
	<link rel="stylesheet" href="960_24_col.css">
	<link rel="stylesheet" href="text.css">
	<link rel ="stylesheet" href="STYLE10.css">
	-->
	
	<title>My Website</title>

	
</head>


<body>
<div class="container_24">
	<header>
		<nav>
			
				<a href="index.jsp" class="selected">Home</a>
				<a href="Drinker.jsp">Drinker</a>
				<a href="Bar.jsp">Bar</a>
				<a href="Beer.jsp">Beer</a>
				<a href="Modification.jsp">Modification</a>
				<a href="Give Query.jsp">MySQL Query</a>
			
		</nav>
		<h1 class="clearfix"><a href="#">Welcome to Group 155 Database</a></h1>
	</header>
</div>
	<% 
	Connection connection = QS.GetConnection();
	
	Graph.Files=null;
	Graph.Files=new LinkedList<String>();
	
	if (connection != null) {

		try{
			
			String qry = QS.SetQuery("AHAHA", 13);
			System.out.printf("\nQUERY:%s",qry);
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(qry);
			ResultSetMetaData rsmd = rs.getMetaData();
			
			%>
			<h2></h2>
			<table>
				<my:PrintTable rsmd="<%=rsmd%>" rs="<%=rs%>" />
			</table>
			<%
		}
		catch(Exception e){
			System.out.println(e);
			out.println(e);
		}
	}
	else{
		out.println("CONNECTION FAILED");
	}

	%>
</body>
</html>