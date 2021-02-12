<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,
			br.edu.ifsudestemg.barbacena.dao.*,
			br.edu.ifsudestemg.barbacena.model.*"%>

<html>
<body>

	<table border=1>
		<tr>
			<th>Nome</th>
			<th>E-mail</th>
			<th>Grau de forma��o</th>
		</tr>
		<%
		ProfessorDAO dao = new ProfessorDAO();
		List<Professor> teachers = dao.fetchAll();
		for (Professor professor : teachers) {
		%>
		<tr>
			<td><%=professor.getName()%></td>
			<td><%=professor.getEmail()%></td>
			<td><%=professor.getDegree()%></td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</html>