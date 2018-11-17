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
//new QueryState("SELECT * FROM frequents");
%>
	<!--  
	<form action="sample.jsp" method="POST">
      <input type="text" id="firstname" name="firstname" />
    </form>
    -->
    
    <form action="MySQL Query.jsp" method="GET">
    <textarea class="myTextarea" name = "T">
    </textarea>
    <input type="hidden" name ="query"/>	
    
    <h1>
	<!--  <a href="MySQL Query.jsp">Start Query</a> -->
	<!--  <input type="submit" name ="submit" value="Send"> -->
	</h1>
    
    
    </form>
    

</body>
</html>