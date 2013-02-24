<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login into the System</title>
</head>
<body>

<h3>Login Page</h3>
${Result}
<form method="POST" action="/server/WebLogin">
<table> <tr>
        <td>Username:</td>
        <td><input type="text" name="username"></td>
    </tr>
    <tr>
        <td>Password:</td>
        <td><input type="password" name="password"></td>
	</tr> 
    <tr>
        <td colspan="2">
            <input type="submit" value="Send"/>
        </td>
    </tr>
</table>
</form>
</body>
</html>