<%@ tag body-content="empty"%>
<%@ attribute name="rsmd" required="true"
	type="java.sql.ResultSetMetaData"%>
<%@ attribute name="rs" required="true" type="java.sql.ResultSet"%>

<%
	try {

		int k = 1;
%>
<tr>
	<%
		while (k <= rsmd.getColumnCount()) {
				//out.print(rsmd.getColumnName(k)+" ");
	%>
	<td><%=rsmd.getColumnName(k) + " "%></td>
	<%
		k += 1;
			}
	%>
</tr>
<%
	out.println();
	} catch (Exception e) {

	}
	String ALLOUT = "";
	while (rs.next()) {

		int i = 1;
		int len = rsmd.getColumnCount();
		//System.out.printf("Rows: %d",len);
%>
<tr>
	<%
		while (i <= len) {
				//out.print(rs.getString(i) + " ");
				//ALLOUT+=rs.getString(i)+" ";
	%>
	<td><%=rs.getString(i) + " "%></td>
	<%
		i += 1;
			}
	%>
</tr>
<%
	//ALLOUT+="\n";
		//out.print("\n");
	}
	//out.println(ALLOUT);
%>