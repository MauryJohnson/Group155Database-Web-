<%@ tag body-content="empty"%>
<%@ tag import="QHandle.Graph"%>
<%@ tag import="java.sql.*"%>
<%@ tag import="java.lang.String.*"%>
<%@ tag import="java.util.*"%>
<%@ attribute name="setType" required="true"%>
			
			<% 
			String F = Graph.Files.get(Integer.parseInt(setType));
			
			System.out.printf("\nFILE:%s\n",F);
			if(!F.contains(".png")){
				out.println("File path failure1");
				System.exit(-1);
			}
			
		
			%>
			<img src=<%=F%> BORDER="0" USEMAP="#chart">
			<%
			%>