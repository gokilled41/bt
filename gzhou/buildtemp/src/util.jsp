<%@ page language="java" %>
<%@ page contentType="text/html; charset=GB2312"%>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.lang.*" %>
<%@ page language="java" import="java.text.*" %>
<%@ page language="java" import="java.util.Map.Entry" %>

<html> 
	<head> 
		<title>util.jsp</title> 
	</head>

	<body> 
		1. Thread Dump: 
		<a href="threadDump.jsp" style="button">
			Get Thread Dump
		</a>
 
		<br/> 
		<br/> 
		<br/>
		
		2. Convert long to date
		<form action="util.jsp">
			long value:
			<input type="text" name="longValue" size="40"> 
			<input type="submit" value="convert">
		</form>

<%
        String longValueParam = request.getParameter("longValue");
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        out.println("<br>");
        out.println("Date: " + sdf.format(new Date(Long.valueOf(longValueParam).longValue())) + "<br>");
        out.println("<br>");
%>

	</body> 
</html> 