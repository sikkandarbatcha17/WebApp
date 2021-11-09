<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<main class="m-3">
	<h2>Welcome ${username}</h2>
	
	<form action="ReadData">
	<br>
		<input type="hidden" name="currentPage" value=1>
		<div class="form-group col-md-4">
			<label for="items">Criteria:</label>
		        <select class="form-control" id="items" name="criteria">
		                <option value="severity">Severity</option>
		                <option value="eventId" selected>EventId</option>
		                <option value="type">Type</option>
		                <option value="source">Source</option>
		        </select>
        <br>
		<input type="text" placeholder="Enter the Value" name="value"><br><br>
       
			<label for="records">Select records per page:</label>
		        <select class="form-control" id="records" name="recordsPerPage">
		                <option value="5">5</option>
		                <option value="10" selected>10</option>
		                <option value="15">15</option>
		        </select>
        </div>

        <button type="submit" class="btn btn-primary">Search</button><br><br>

    </form>
	
	<form action="Logout">
		<input type="submit" value="logout">
	</form>
</main>
</body>
</html>