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
				<a href="Bar.jsp">Bar</a> <a href="Beer.jsp">Beer</a>
				<a href="Modification.jsp">Modification</a> 
				<a
href="Give Query.jsp">MySQL Query</a>

			</nav>


			<%
				Connection connection = QS.GetConnection();
			
				//RESET ALL FILE
				Graph.Files=null;
				Graph.Files=new LinkedList<String>();
			
					if (connection != null) {
						String ip = request.getHeader("X-Forwarded-For");
						ip = QS.GetIp(ip, request);

						Statement st = connection.createStatement();
						ResultSet rs = null;

						String Drinker = (String) request.getParameter("Drinker1");
						//Add drinker name to query

		
    					request.setAttribute(ip+"Drinker",Drinker);
						
						String qry = QS.SetQuery(Drinker, 0);

						if (qry == "") {
							return;
						}
			%>

			<my:IpSession qry="<%=Drinker%>" />

			<%
				rs = st.executeQuery(qry);

						if (rs == null) {
							out.println("Unable to get result from query");
						} else {

							ResultSetMetaData rsmd = rs.getMetaData();

							out.println("\n");
			%>
			<h2><%=Drinker%></h2>
			<table>
				<my:PrintTable rsmd="<%=rsmd%>" rs="<%=rs%>" />
			</table>

			<%
				try{
					qry = QS.SetQuery(Drinker, 1);
					rs = st.executeQuery(qry);
					rsmd = rs.getMetaData();
					
					
				//First Image
				
				
				String Title = "Most Beer Transactions for "+Drinker;
					
			%>
			<my:SetGraph setQry="<%=qry%>" setTitle="<%=Title%>" setCategory="Most Beer Transactions" setXLabel="Beers" setYLabel="Frequency" setRsmd="<%=rsmd%>" setRs="<%=rs%>" setType="0" />
			<my:PrintGraph setType="0"/>
			<%
			//Success Drinker1
			//System.out.printf("IN DGRAPHS JSP! GET FILE 0: %s",Graph.Files.get(0));
			
			//First Image
			
			qry = QS.SetQuery(Drinker, 2);
			rs = st.executeQuery(qry);
			rsmd = rs.getMetaData();
			
			
			Title = "Transactions for Days of Year for "+Drinker;
			
			//Second Image
			%>
			<my:SetGraph setQry="<%=qry%>" setTitle="<%=Title%>" setCategory="Transactions for Days of Year" setXLabel="Consumables" setYLabel="Price" setRsmd="<%=rsmd%>" setRs="<%=rs%>" setType="1"/>
			<my:PrintGraph setType="1"/>
			<%
			//Third Image
			qry = QS.SetQuery(Drinker, 3);
			rs = st.executeQuery(qry);
			rsmd = rs.getMetaData();
			
			Title = "Transactions for Days of Week for "+Drinker;
			
			%>
			<my:SetGraph setQry="<%=qry%>" setTitle="<%=Title%>" setCategory="Transactions for Days of Week" setXLabel="Consumables" setYLabel="Price" setRsmd="<%=rsmd%>" setRs="<%=rs%>" setType="1"/>
			<my:PrintGraph setType="2"/>
			<%
			//Fourth Image
			qry = QS.SetQuery(Drinker, 4);
			rs = st.executeQuery(qry);
			rsmd = rs.getMetaData();
			
			Title = "Transactions for Months of Year for " +Drinker;
			
			%>
			<my:SetGraph setQry="<%=qry%>" setTitle="<%=Title%>" setCategory="Transactions for Months of Year" setXLabel="Consumables" setYLabel="Price" setRsmd="<%=rsmd%>" setRs="<%=rs%>" setType="1"/>
			<my:PrintGraph setType="3"/>
			<%
			}
			catch(Exception r){
				System.out.println(r);
				out.print(r);
			}
				}

					connection.close();
				}
				else{
					out.println("CONNECTION FAILED");
				}
			%>


			<h1 class="clearfix">
				<a href="#"></a>
			</h1>
		</header>
	</div>

</body>
</html>