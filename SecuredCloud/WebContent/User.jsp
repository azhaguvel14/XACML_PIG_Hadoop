<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <%
            String user=session.getAttribute("username").toString();
            
 %>
  <h4 align="right"><a href="Login.jsp">LOGOUT</a></h4>
 <h3>Welcome <%= user %></h3>
 <form action="Output.jsp" method="post">
 <table align="center">
 <tr>
 <td>PIG Script: </td>
 <td><textarea rows="5" cols="60" name="pigScript" onfocus="if(this.value=='Enter Pig Script here.')this.value=''" onblur="if(this.value=='')this.value='Enter Pig Script here.'">Enter Pig Script here.</textarea></td>
 </tr>
 <tr>
     		<td colspan="2" align="center"><input type="submit" value="Submit"></td>
     		</tr>
 </table>
 </form>
</body>
</html>