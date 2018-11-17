<!-- Creates Session with user and saves user state given qry -->
<%@ tag body-content="empty"%>
<%@ tag import="QHandle.Graph"%>
<%@ tag import="java.sql.*"%>
<%@ tag import="java.lang.String.*"%>
<%@ tag import="java.util.*"%>
<%@ attribute name="setQry" required="true"%>
<%@ attribute name="setTitle" required="true" type="java.lang.String"%>
<%@ attribute name="setCategory" required="true"%>
<%@ attribute name="setXLabel" required="true"%>
<%@ attribute name="setYLabel" required="true"%>
<%@ attribute name="setRsmd" required="true" type="java.sql.ResultSetMetaData"%>
<%@ attribute name="setType" required="true" type="java.lang.String"%>
<%@ attribute name="setRs" required="true" type="java.sql.ResultSet"%>


<%
	//Set bar graph given these parameters
	
	ArrayList<double[]> d = null;
	LinkedList<String> XCategory = new LinkedList<String>();
	LinkedList<String> XGroupsLabels = new LinkedList<String>();
	
	double[][] D = Graph.ConvertData(setRs,setRsmd,XCategory,XGroupsLabels,Integer.parseInt(setType));
	
	//Must Parse double[][], String[] XCategory, String[]XGroupsLabels FROM TABLE
	Graph.BG(D, setTitle, setCategory, setXLabel, setYLabel, XCategory, XGroupsLabels,Integer.parseInt(setType));

%>