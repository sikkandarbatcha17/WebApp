<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Top values for field</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
         >
</head>
<body>

<%
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	
		if(session.getAttribute("username")==null)
		{
			response.sendRedirect("Login.jsp");
		}
			 
	%>
	
<main class="m-3">
	<div class="row col-md-6">
		<table class="table table-striped table-bordered table-sm">
	            <tr>
	                <th>Top Values</th>
	                <th>Count</th>
	            </tr>
	
	            <c:forEach items="${topKey}" var="topKey">
	                <tr>
	                    <td>${topKey.getTopValue()}</td>
	                    <td>${topKey.getCount()}</td>
	                   
	                </tr>
	            </c:forEach>
	        </table>
     </div>
</main>
</body>
</html>