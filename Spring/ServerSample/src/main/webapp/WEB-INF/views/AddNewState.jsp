<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<form action="/server/addNewState" method="POST">
<table>
	<tr>
	<h3>Add New State</h3>
	</tr>
	<tr>
		<td>
		Description
		</td>
		<td>
		<input type="text" name="description">
		</td>
	</tr>
	<tr>
		<td>
			<input type="submit" value="Add State"/>
		</td>
	</tr>
</table>

</form>
