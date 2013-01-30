<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h2>Your past states are:</h2><br>
	<c:forEach items="${StateDetailsList}" var="stateDetails">
		<tr>
			<td>${stateDetails.state.description}</td>
			<td>${stateDetails.getTime_Date()}</td>
						<br>
		</tr>
	</c:forEach>
	
	<br><br>
	
	<a href="/server/SendStateForm">Back</a> <br><br>
	<a href="/server/logout">Logout</a>
</body>
</html>