<%@ page import = "QHandle.*" %>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="javax.*" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
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
				<a href="Beer.jsp">Beer</a>
				<a href="Drinker.jsp">Drinker</a>
				<a href="Modification.jsp">Modification</a>
				<a href="Give Query.jsp">MySQL Query</a>
		</nav>
		
	</header>
	 <h2><a>Enter Bar Name</a></h2>
</div>

<%
	String ip = request.getHeader("X-Forwarded-For");  
		 ip = QS.GetIp(ip,request);
		 
		 HttpSession sess = request.getSession();
         //Open drinker graphs page
%>
		
         <form action="BarGraphs.jsp" method="GET"> 
    	 <h1>
         <textarea style="width:300px; height:20px;" class="myTextarea" name = "Bar1"><%try{if(sess.getAttribute(ip+"Bar")!=null){out.print(sess.getAttribute(ip+"Bar"));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
         <input type="hidden" name ="query"/>	
	     <input type="submit" name ="submit" value="Send"> 
	     </h1>
         </form> 
         
         <h1><a>Enter New Transaction for Bar</a></h1>
         
         <p>
    		
    		
    	 Use INSERT INTO bar_name in order to insert a new transaction. EX:
    	 INSERT INTO beer_transactions
    	 VALUE ("MyBar","MyLicense","MyCity","MyState","MyPhoneNumber","MyAddress","")	
    		    		
         </p>
         
         <form action="UpdateResults.jsp" method="GET">
    	 <h1>
    	 <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    	 <input type="hidden" name ="query" value="bars2"/>
    	 <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
		 </h1>
    	 </form>
        
         
         <%
         //Save state...
         String qry =(String)request.getParameter("Bar1");
         if(qry!=null){
         %>
         <my:IpSession qry = "<%= qry %>" />
         <%
         }
         //After name entered, use this for query...
  
         %>
         
        
         
         <%
         
         
         

%>
</body>
</html>