<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Login</title>
</head>
<body>
<h3>LOGIN</h3>

<form action="Validation.jsp" method="post">
            <table align="center" width="50%">
            <tr>
            <td>USERNAME</td>
            <td><input type="text" name="username"></td>
            </tr>
     		<tr>
     		<td>PASSWORD</td>
     		<td><input type="password" name="password"></td>
     		</tr>
     		<tr>
     		<td colspan="2" align="center"><input type="submit" value="Submit"></td>
     		</tr>
            </table>
            </form>
</body>
</html>