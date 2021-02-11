<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>temperatura</title>
</head>
<body>
	<h1>Temperaturas</h1>
	<hr>
	<table>
		<tr>
			<th>Celsius</th>
			<th>Fahrenheit</th>
		</tr>
		<%
		for (int i = -40; i <= 100; i += 20) {
		%>

		<tr>
			<td><%=i%></td>
			<td><%=((i * 9 / 5) + 32)%></td>
		</tr>
		<%
		}
		%>
	</table>

	<br>
	<br>
	<form action="temperatura.jsp" method="post">
		<input type="text" name="temperatura"> <input type="submit"
			value="Enviar">
	</form>
	
	<h2>A temperatura digitada é ${param.temperatura}.</h2>
</body>
</html>