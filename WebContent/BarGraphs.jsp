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

				<a href="index.jsp">Home</a>
				<a href="Beer.jsp">Beer</a>
				<a href="Drinker.jsp">Drinker</a>
				<a href="Bar.jsp">Bar</a>
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

						ResultSetMetaData rsmd = null;
						
						System.out.println("BAR GRAPHS 0");
						
						Graph.Files = null;
						Graph.Files=new LinkedList<String>();
						
						
						String Bar = (String) request.getParameter("Bar1");
						//Add drinker name to query

						String qry = QS.SetQuery(Bar, 5);
						rs = st.executeQuery(qry);
						rsmd = rs.getMetaData();	
						
						if (qry == "") {
							return;
						}
						
						try{
							
						%>
						<my:SetGraph setQry="<%=qry%>" setTitle="Bar1" setCategory="Transactions for Months of Year" setXLabel="Consumables" setYLabel="Price" setRsmd="<%=rsmd%>" setRs="<%=rs%>" setType="2"/>
						<my:PrintGraph setType="0"/>
						<%
				
						}catch(Exception r){
							System.err.println(r);
							out.println(r);
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