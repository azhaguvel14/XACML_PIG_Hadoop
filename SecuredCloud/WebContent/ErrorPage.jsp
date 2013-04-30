<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
</head>
<body>
<h3> <% String msg=session.getAttribute("msg").toString();
		
%>Sorry, could not login. <%=msg %></h3>
<h4><a href="Login.jsp">Try Again</a></h4>
</body>
</html>