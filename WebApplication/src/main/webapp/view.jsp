<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Event Logs</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
         >
</head>
<body>	
<main class="m-3">    
			    <c:forEach items="${eventLogs}" var="eventLogs">
	                <p>
	                <a href="TopValue?eventCat=severity&criteria=${criteria}&value=${value}"><b>Severity:</b></a> ${eventLogs.getSeverity()}
	             	<b>Id:</b> ${eventLogs.getId()}
	                <a href="TopValue?eventCat=type.keyword&criteria=${criteria}&value=${value}"><b>Type:</b></a> ${eventLogs.getType()}
	                <b>TimeGenerated:</b> ${eventLogs.getTimeGenerated()}
	                <b>Message:</b> ${eventLogs.getMessage()}
	                <a href="TopValue?eventCat=eventId&criteria=${criteria}&value=${value}"><b>EventId:</b></a> ${eventLogs.getEventId()}
	                <a href="TopValue?eventCat=source.keyword&criteria=${criteria}&value=${value}"> <b>Source:</b></a> ${eventLogs.getSource()}
	                </p>
	                <hr>
	            </c:forEach>
    
        
        <nav aria-label="Navigation for eventLogs">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                    href="ReadData?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&value=${value}&criteria=${criteria}">Previous</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">
                                ${i} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                            href="ReadData?recordsPerPage=${recordsPerPage}&currentPage=${i}&value=${value}&criteria=${criteria}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link"
                    href="ReadData?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&value=${value}&criteria=${criteria}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
<a href="welcome.jsp">Go to Home Page</a>
<form action="Logout">
		<input type="submit" value="logout">
</form>
</main> 
</body>
</html>