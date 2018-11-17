<%@ page import= "QHandle.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<head>
<link rel="stylesheet" type="text/css" href="BaseStyle.css">
</head>
<body>

<div class="container_24">
	<header>
		<nav>
				<a href="index.jsp">Home</a>
				<a href="Bar.jsp">Bar</a>
				<a href="Beer.jsp">Beer</a>
				<a href="Drinker.jsp">Drinker</a>
		</nav>
		<h1><a>Enter A Query</a></h1>
	</header>
</div>
<%
	//Do some java stuff here
		String ip = request.getHeader("X-Forwarded-For");  
		
		ip = QS.GetIp(ip,request);
		
        HttpSession sess = request.getSession();
//new QueryState("SELECT * FROM frequents");
%>
	<!--  
	<form action="sample.jsp" method="POST">
      <input type="text" id="firstname" name="firstname" />
    </form>
    -->
    
    <form action="MySQL Query.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "T"><%try{if(sess.getAttribute(ip)!=null){out.print(sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query"/>	
    
	 <input type="submit" name ="submit" value="Send"> 
	</h1>
    </form>
 

</body>
</html>