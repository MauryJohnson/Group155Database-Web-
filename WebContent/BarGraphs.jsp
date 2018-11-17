<%@ page import="QHandle.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
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
				<a href="Beer.jsp">Beer</a> 
				<a href="Give Query.jsp">MySQL Query</a>

			</nav>


			<%
				Connection connection = QS.GetConnection();

					if (connection != null) {
						String ip = request.getHeader("X-Forwarded-For");
						ip = QS.GetIp(ip, request);

						Statement st = connection.createStatement();
						if(st==null){
							return;
						}
						ResultSet rs = null;

						System.out.println("BAR GRAPHS 0");
						
						String Bar = (String) request.getParameter("Bar1");
						//Add drinker name to query

						String qry = QS.SetQuery(Bar, 6);

						if (qry == "") {
							return;
						}
						String D = "Bar";
			%>

			<my:IpSession qry="<%=Bar%>" setSess="<%=D%>" />

			<%
				rs = st.executeQuery(qry);

						if (rs == null) {
							out.println("Unable to get result from query");
							return;
						} else {
							if(rs==null){
								return;
							}
							ResultSetMetaData rsmd = null;

							out.println("\n");
			%>
			<h2><%=Bar%></h2>
			<table>
				<my:PrintTable rsmd="<%=rsmd%>" rs="<%=rs%>" />
			</table>

			<%
				try{
					
					String qry2 = QS.SetQuery(Bar, 6);
					ResultSet rs2 = st.executeQuery(qry2);
					ResultSetMetaData rsmd2 = rs2.getMetaData();
					
				//First Image
			%>
			<h2>
			Query 2
			</h2>
			<table>
			<my:PrintTable rsmd="<%=rsmd2%>" rs="<%=rs2%>"/>
			</table>
			
			<%
							//First Image
							//Second Image
								
								String qry3 = QS.SetQuery(Bar, 7);
								ResultSet rs3 = st.executeQuery(qry3);
								ResultSetMetaData rsmd3 = rs3.getMetaData();
								
								System.out.printf("RS: %s",rs);
						%>
			<h2>
			Query 3
			</h2>
			<table>
			<my:PrintTable rsmd="<%=rsmd3%>" rs="<%=rs3%>"/>
			</table>
			
			<%
							//Second Image
							}
							catch(Exception r){
								out.print(r);
							}
								}
									//Graph.Files = null;
									connection.close();
									if(st!=null){
										st.close();
									}
								}
						%>


			<h1 class="clearfix">
				<a href="#"></a>
			</h1>
		</header>
	</div>

</body>
</html>