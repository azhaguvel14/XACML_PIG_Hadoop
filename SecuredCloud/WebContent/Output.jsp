<%@ page language="java" import="password.PasswordEncryption" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="policyreader.XACMLPolicyValidator"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% String pig=request.getParameter("pigScript");
System.out.println(pig);
List<String> resources = new ArrayList<String>();
String[] queries = pig.split(";");
for(String s: queries){
	if(s.toUpperCase().contains("LOAD"))
	{
		String[] loadQueries = s.split(" ");
		for(int i=0;i<loadQueries.length;i++)
		{
			if(loadQueries[i].toUpperCase().contains("LOAD") && i<loadQueries.length-1)
			{
				resources.add(loadQueries[i+1].replace("'", ""));
				break;
			}
		}
	}
}
//resources[0] = "users";
//resources[1] = "pages";
String output ="";
System.out.println("User name"+(String)session.getAttribute("username"));
String[] array = resources.toArray(new String[resources.size()]);
boolean accessPermit = XACMLPolicyValidator.validateLegitimateAccess(array,(String)session.getAttribute("username"));
if(accessPermit){
	PasswordEncryption pw = new PasswordEncryption();
	output=pw.callPigScript(pig).toString();
}else{
	 output="Access Denied.Insufficient privileges";
}

 //PasswordEncryption pw = new PasswordEncryption();
 //StringBuffer output=pw.callPigScript(pig);

//PasswordEncryption.callPigScript(pig);

   
%>
<h3>Pig script run</h3>
<br/>
<h2>OUTPUT:</h2>
<br/>
<h5><%= output %></h5>
</body>
</html>