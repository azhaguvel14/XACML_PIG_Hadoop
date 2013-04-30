<%@ page language="java" import="password.PasswordEncryption" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Validation</title>
</head>
<body>
<%
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        
        
        String msg=PasswordEncryption.ValidateUser(username, password);
        if(msg.equals("Success"))
            {
            session.setAttribute("username",username);
            response.sendRedirect("User.jsp");
            }
        else
        {
        	session.setAttribute("msg",msg);
            response.sendRedirect("ErrorPage.jsp");           
        }
%>

</body>
</html>