<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:joda="http://www.joda.org/joda/time/tags" version="2.0">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<c:choose>
		<c:when test="${Admin == true}">
			<c:import url="AddNewState.jsp"></c:import>
		</c:when>
	</c:choose>

	<c:choose>
		<c:when test="${Admin == true}">
			<c:forEach items="${StateList}" var="state">
				<tr>
					<form action="/server/DeleteState" method="POST">
						<td>${state.description }<br></td>
						<td><input type="submit" value="Delete" /></td> <input
							type="hidden" name="id" value=${state.id}> <input
							type="hidden" name="description" value=${state.description}>
					</form>
				</tr>
			</c:forEach>
		</c:when>
	</c:choose>
	
	<br><br>
	
	<c:choose>
		<c:when test="${empty Admin}">
			<c:forEach items="${StateList}" var="state">
				<tr>
					<form action="/server/SendState" method="POST">
						<td>${state.description }<br></td>
						<td><input type="submit" value="Send State" /></td> 
						<input type="hidden" name="id" value=${state.id}> 
						<input type="hidden" name="description" value=${state.description}>
					</form>
				</tr>
			</c:forEach>
		</c:when>
	</c:choose>

	<a href="/server/testSession">test</a>
	
	<br><br>
	<c:choose>
		<c:when test="${empty Admin}">
			<a href="/server/ShowStates">Show your States</a>
		</c:when>
	</c:choose>
	
	<br><br>
	<a href="/server/logout">Logout</a>
</body>
	</html>