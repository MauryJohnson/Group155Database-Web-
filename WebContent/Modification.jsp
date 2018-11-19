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
				<a href="Give Query.jsp">MySQL Query</a>
		</nav>
		<h1><a>Modifications</a></h1>
	</header>
	<h2><a>Enter Update(s), Delete(s), or Insertion(s) for Bar</a></h2>
</div>
<%
	//Do some java stuff here
		String ip = request.getHeader("X-Forwarded-For");  
		
		ip = QS.GetIp(ip,request);
		
        HttpSession sess = request.getSession();
        
        ip+="MOD";
        
        System.out.printf("\nIP IN-%s\n",ip);
        
//new QueryState("SELECT * FROM frequents");
%>
	<!--  
	<form action="sample.jsp" method="POST">
      <input type="text" id="firstname" name="firstname" />
    </form>
    -->
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="bars"/>
    <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
 
 	<h2><a>Enter Update(s), Delete(s), or Insertion(s) for Beer</a></h2>
 
 	<form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="beers"/><input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>

	<h2><a>Enter Update(s), Delete(s), or Insertion(s) for Drinker</a></h2>

	<form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="drinkers"/><input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
    <h2><a>Enter Update(s), Delete(s), or Insertion(s) for Frequents</a></h2>
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value = "frequents"/>
    <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
    <h2><a>Enter Update(s), Delete(s), or Insertion(s) for Likes</a></h2>
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="likes"/>	
    
	 <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
    <h2><a>Enter Update(s), Delete(s), or Insertion(s) for Food Sells</a></h2>
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="food_sells"/>	
    
	 <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
    <h2><a>Enter Update(s), Delete(s), or Insertion(s) for Beer Sells</a></h2>
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="beer_sells"/>	
    
	 <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
    <h2><a>Enter Update(s), Delete(s), or Insertion(s) for Soft Drink Sells</a></h2>
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="soft_drink_sells"/>	
    
	 <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
    <h2><a>Enter Update(s), Delete(s), or Insertion(s) for Food Transactions</a></h2>
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="food_transactions"/>	
    
	 <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
    <h2><a>Enter Update(s), Delete(s), or Insertion(s) for Beer Transactions</a></h2>
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="beer_transactions"/>	
    
	 <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
    <h2><a>Enter Update(s), Delete(s), or Insertion(s) for Soft Drink Transactions</a></h2>
    
    <form action="UpdateResults.jsp" method="GET">
    <h1>
    <textarea class="myTextarea" name = "Mod"><%try{if((String)sess.getAttribute(ip)!=null){out.print((String)sess.getAttribute(ip));}else{/*out.print("");*/}}catch(Exception e){}%></textarea>
    <input type="hidden" name ="query" value="soft_drink_transactions"/>	
    
	 <input type="submit" name ="submit" value="Send" onclick = "{document.frm.hdnbt.value=this.value;document.frm.submit();}"> 
	</h1>
    </form>
    
</body>
</html>